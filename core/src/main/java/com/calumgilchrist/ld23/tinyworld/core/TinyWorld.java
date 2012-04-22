package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Sound;
import playn.core.Pointer.Event;

public class TinyWorld implements Game, Pointer.Listener, ContactListener {

	ArrayList<Asteroid> planetoids;
	ArrayList<Body> destroyList;

	Player player;
	World world;

	private int state;

	private KeyboardInput keyboard;

	private GroupLayer menuLayer;
	ArrayList<MenuItem> menuItemLayers;
	
	private GroupLayer planetoidLayer;
	
	Sound clickSound;
	
	float globalScale;

	@Override
	public void init() {	
		keyboard = new KeyboardInput();
		globalScale = 1.0f;
				
		planetoidLayer = graphics().createGroupLayer();

		state = Constants.STATE_MENU;
		
		pointer().setListener(this);
		keyboard = new KeyboardInput();
		
		// create and add background image layer
		Image bgImage = assets().getImage("images/starfield.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
		
		menuInit();
	}

	public void menuInit() {
		//clickSound = assets().getSound("sounds/select");
		Menu mainMenu = new Menu("Tiny World",60);
		menuLayer = graphics().createGroupLayer();
		menuLayer.add(mainMenu.getTitle());
		
		mainMenu.addMenuItem("New Game");
		mainMenu.addMenuItem("Credits");
		mainMenu.addMenuItem("Exit");
	    
	    menuItemLayers = mainMenu.getMenuItems();
	    for(MenuItem l : menuItemLayers){
	    	menuLayer.add(l.getLayer());
	    }
	    
	    graphics().rootLayer().add(menuLayer);
	}
	
	public void creditsMenuInit(){
		graphics().rootLayer().remove(menuLayer);
		Menu creditsMenu = new Menu("Credits",60);
		menuLayer = graphics().createGroupLayer();
		menuLayer.add(creditsMenu.getTitle());
		
		creditsMenu.addMenuItem("Calum Gilchrist");
		creditsMenu.addMenuItem("Daniel Bell");
		creditsMenu.addMenuItem("Back");
		
		menuItemLayers = creditsMenu.getMenuItems();
	    for(MenuItem l : menuItemLayers){
	    	menuLayer.add(l.getLayer());
	    }
	    
	    graphics().rootLayer().add(menuLayer);
	}

	public void gameInit() {
		state = Constants.STATE_GAME;
		graphics().rootLayer().remove(menuLayer);

		planetoids = new ArrayList<Asteroid>();
		destroyList = new ArrayList<Body>();
		planetoidLayer = graphics().createGroupLayer();

		// Load grey asteroid image asset
		Image asteroidImage = assets().getImage("images/bubbly-asteroid.png");
		Image planetoidImage = assets().getImage("images/planetoid.png");

		// Set up the world
		world = new World(new Vec2(), false);
		world.setContactListener(this);
		
		for (int i = 0; i < 30; i++) {
			createAsteroid(asteroidImage);
		}

		Vec2 playerStart = new Vec2(graphics().width() / 2,
				graphics().height() / 2);

		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DYNAMIC;
		
		playerBodyDef.position.set(playerStart.mul(1/Constants.PHYS_RATIO));
		
		player = new Player(new Sprite((int) playerStart.x, (int) playerStart.y, planetoidImage), playerBodyDef, world);
		planetoidLayer.add(player.getSprite().getImageLayer());
		graphics().rootLayer().add(planetoidLayer);
		//planetoidLayer.setScale(globalScale/2);
		
		setScale(2.0f);
	}
	
	public void setScale(float scale){
		globalScale = 1/scale;;
		player.getSprite().setScale(scale);
		planetoidLayer.setScale(globalScale);
	}

	public void createAsteroid(Image asteroid) {
		
		//TODO: Randomise it!
		float forceFactor = 100;
		
		//Start Vector off screen (This should be random)
		Vec2 astrStart = genStartPos(graphics().width(), graphics().height());

		// Set up an asteroid
		BodyDef astrBodyDef = new BodyDef();
		astrBodyDef.type= BodyType.DYNAMIC;
		
		//Initial Position
		astrBodyDef.position.set(astrStart.mul(1/Constants.PHYS_RATIO));
		
		Asteroid astr = new Asteroid(astrStart, new Sprite((int) astrStart.x, (int) astrStart.y, asteroid), astrBodyDef, world);
		
		astr.applyThrust(astr.getThrustForce(forceFactor));

		planetoids.add(astr);
		planetoidLayer.add(astr.getSprite().getImageLayer());
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
		// here!

	}

	@Override
	public void update(float delta) {

		if(state == Constants.STATE_GAME){
			// Values need playing with, and to be stored
			world.step(60, 6, 3);
			world.clearForces();
	
			player.applyThrust(keyboard.getMovement());
			cameraFollowPlayer();
			player.update();
	
			// For every planetoid update it's sprite
			for (Planetoid p : planetoids) {
				p.update();		
				//Destroy bodies to be destroyed
				for (Body body: destroyList) {
					replaceAsteroid(body);
				}
			}
		}
		

	}

	@Override
	public int updateRate() {
		return 25;
	}

	public void cameraFollowPlayer() {
		int tx;
		tx = (int) (player.getBody().getWorldCenter().x * Constants.PHYS_RATIO);
		tx = tx - (graphics().width());

		int ty;
		ty = (int) (player.getBody().getWorldCenter().y * Constants.PHYS_RATIO);
		ty = ty - (graphics().height());

		planetoidLayer.setOrigin(tx, ty);
	}

	@Override
	public void onPointerStart(playn.core.Pointer.Event event) {
		int mousex = (int) event.x();
		int mousey = (int) event.y();
		
		if(state == Constants.STATE_MENU){			
			for(MenuItem mi : menuItemLayers){
				if(mousex > mi.getPosX() && mousex < (mi.getPosX() + mi.getLayout().width())){
					if(mousey > mi.getPosY() && mousey < (mi.getPosY() + mi.getLayout().height())){
						if(mi.getText() == "New Game"){
							gameInit();
						}
						else if(mi.getText() == "Credits"){
							creditsMenuInit();
						}
						else if(mi.getText() == "Exit"){
							System.exit(0);
						}
					}
				}
			}
			
			System.out.println(mousex+ ","+mousey);
		}
		else if(state == Constants.STATE_CREDITS){
			for(MenuItem mi : menuItemLayers){
				if(mousex > mi.getPosX() && mousex < (mi.getPosX() + mi.getLayout().width())){
					if(mousey > mi.getPosY() && mousey < (mi.getPosY() + mi.getLayout().height())){
						if(mi.getText() == "Back"){
							graphics().rootLayer().remove(menuLayer);
							menuInit();
						}
					}
				}
			}
		}
	}

	@Override
	public void onPointerEnd(playn.core.Pointer.Event event) {
		
	}

	/**
	 * Get the starting position of an object
	 * 
	 * @param screenWidth
	 * @param screenHeight
	 * @return
	 */
	public Vec2 genStartPos(int width, int height) {

		float x = getSpawnBound(-width, width);
		float y = getSpawnBound(-height, height);

		Vec2 pos = new Vec2(x, y);

		return pos;
	}

	/**
	 * Randomly choose a number NOT between 0 and limit
	 * 
	 * @param max
	 * @return
	 */
	private int getSpawnBound(int min, int max) {

		Random rand = new Random();

		int spawnBound = 100;
		// Set x
		boolean belowBound = rand.nextBoolean();

		int pos = rand.nextInt(spawnBound) + 50;

		if (belowBound) {
			pos = -pos + min;
		} else {
			pos += max;
		}

		return pos;
	}

	@Override
	public void beginContact(Contact contact) {
		Body hitter;
		
		//Fixture A is never the contact, I think
		if (player.getBody().equals(contact.getFixtureB().m_body)) {
			
			hitter = contact.getFixtureA().m_body;
			player.addMass(contact.getFixtureA().m_body.m_mass);
			destroyList.add(hitter);
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Take a body, destroy it and replace it.
	 * @param body
	 */
	public void replaceAsteroid(Body body){
		boolean found = false;
		Asteroid planet;
		
		Iterator<Asteroid> it = planetoids.iterator();
		
		/*
		 * Slow and awful
		 * TODO: Use something better than O(n)
		 */
		while (it.hasNext() && !found) {
			planet = it.next();
			if (planet.getBody().equals(body)) {
				Vec2 astrStart = genStartPos(graphics().width(), graphics().height());
				
				found = true;
				world.destroyBody(body);
				
				//Set up an asteroid
				BodyDef astrBodyDef = new BodyDef();
				astrBodyDef.type= BodyType.DYNAMIC;
				
				//Initial Position
				astrBodyDef.position.set(astrStart.mul(1/Constants.PHYS_RATIO));
				
				planet.newBody(astrBodyDef, world);
			}
		}
	}

	public void onPointerDrag(Event event) {
		// TODO Auto-generated method stub

	}
}