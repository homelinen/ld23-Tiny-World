package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Sound;

public class TinyWorld implements Game {

	ArrayList<Asteroid> planetoids;
	ArrayList<Body> destroyList;

	Player player;
	World world;

	DynamicFactory factory;
	StarFactory starFactory;

	private KeyboardInput keyboard;
	private MouseInput mouse;

	private static GroupLayer planetoidLayer;
	Image planetoidImage;
	
	boolean createAstr;
	
	Menus menus;
	
	Sound clickSound;

	@Override
	public void init() {	
		menus = new Menus();
		
		keyboard = new KeyboardInput();
		mouse = new MouseInput(this);
		
		createAstr = false;
		
		Globals.globalScale = 1.0f;
				
		planetoidLayer = graphics().createGroupLayer();
		
		planetoidImage = assets().getImage("images/planetoid.png");

		Globals.state = Globals.STATE_MENU;
				
		// create and add background image layer
		Image bgImage = assets().getImage("images/starfield.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
		
		menus.menuInit();
	}

	public void gameInit() {
		Globals.state = Globals.STATE_GAME;
		graphics().rootLayer().remove(menus.menuLayer);

		planetoids = new ArrayList<Asteroid>();
		destroyList = new ArrayList<Body>();
		planetoidLayer = graphics().createGroupLayer();

		Image sunImage = assets().getImage("images/sun.png");

		// Set up the world
		world = new World(new Vec2(), false);
		
		//Set up the factory and Asteroids
		factory = new DynamicFactory(world, planetoidLayer);
		starFactory = new StarFactory(world,sunImage, planetoidLayer);
		for (int i = 0; i < 10; i++) {
			factory.getAsteroid(10);
		}
		
		for (int i = 0; i < 10; i++) {
			factory.getComet(10);
		}
		
		for (int i = 0; i < 3; i++){
			starFactory.getStar(new Vec2(0,0));
		}
		
		//Set up player
		Vec2 playerStart = new Vec2(graphics().width() / 2,
				graphics().height() / 2);

		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DYNAMIC;
		
		playerBodyDef.position.set(playerStart.mul(1/Globals.PHYS_RATIO));
		
		player = new Player(new Sprite((int) playerStart.x, (int) playerStart.y, planetoidImage), playerBodyDef, world);
		planetoidLayer.add(player.getSprite().getImageLayer());
		graphics().rootLayer().add(planetoidLayer);
		
		setScale(2.0f);
	}
	
	public void setScale(float scale){
		Globals.globalScale = 1/scale;
		player.getSprite().setScale(scale);
		planetoidLayer.setScale(Globals.globalScale);
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
		// here!

	}

	@Override
	public void update(float delta) {

		if(Globals.state == Globals.STATE_GAME){
			// Values need playing with, and to be stored
			world.step(30, 6, 3);
			world.clearForces();
	
			if (!keyboard.isSpaceDown()) {
				player.applyThrust(keyboard.getMovement());
			} else {
				//When space is pressed, set Velocity to 0
				player.getBody().setLinearVelocity(new Vec2());
			}
			
			//Creates an asteroid if one was previosuly destroyed
			//What happens if two were destroyed?
			if (createAstr) {
				factory.getAsteroid(1);
				createAstr = false;
			}
			
			cameraFollowPlayer();
			player.update();
	
			factory.update();
		}
	}

	@Override
	public int updateRate() {
		return 25;
	}
	
	// Translates the planetoid layer so that the player planet is always centre
	public void cameraFollowPlayer() {
		int tx;
		tx = (int) (player.getBody().getWorldCenter().x * Globals.PHYS_RATIO);
		tx = (int) (tx - graphics().width() + (player.getSprite().getWidth()));

		int ty;
		ty = (int) (player.getBody().getWorldCenter().y * Globals.PHYS_RATIO);
		ty = (int) (ty - graphics().height() + (player.getSprite().getHeight()));
		
		planetoidLayer.setOrigin(tx, ty);
	}

	public void movePlayer() {
		//What is this?
	}
}