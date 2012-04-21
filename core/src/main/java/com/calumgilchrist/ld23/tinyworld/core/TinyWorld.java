package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Pointer.Event;

public class TinyWorld implements Game, Pointer.Listener {
	ArrayList<Planetoid> planetoids;
	Player player;
	World world;
	private int state;

	private KeyboardInput keyboard;

	private GroupLayer planetoidLayer;

	// TODO remove everything from root layer

	@Override
	public void init() {
		state = Constants.STATE_MENU;
		menuInit();
	}

	public void menuInit() {
		gameInit();
	}

	public void gameInit() {
		state = Constants.STATE_GAME;
		keyboard = new KeyboardInput();

		planetoids = new ArrayList<Planetoid>();
		planetoidLayer = graphics().createGroupLayer();

		pointer().setListener(this);

		// create and add background image layer
		Image bgImage = assets().getImage("images/starfield.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);

		// Load grey asteroid image asset
		Image asteroidImage = assets().getImage("images/grey-asteroid.png");
		Image planetoidImage = assets().getImage("images/planetoid.png");

		// Set up the world
		world = new World(new Vec2(), false);

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
		planetoidLayer.setScale(0.5f);
	}

	public void createAsteroid(Image asteroid) {

		// Start Vector off screen (This should be random)
		Vec2 astrStart = genStartPos(graphics().width(), graphics().height());

		// Set up an asteroid
		BodyDef astrBodyDef = new BodyDef();
		astrBodyDef.type= BodyType.DYNAMIC;
		
		//Initial Position
		astrBodyDef.position.set(astrStart.mul(1/Constants.PHYS_RATIO));
		
		Asteroid astr = new Asteroid(astrStart, new Sprite((int) astrStart.x, (int) astrStart.y, asteroid), astrBodyDef, world);
		
		//Apply a force to the asteroid
		Vec2 forceDir = astr.getStartDirVec(graphics().width(), graphics().height());
		
		/*
		 * Generate a nice random vector by multiplying direction by random
		 * numbers
		 */
		float forceFactor = 100f;

		Random rand = new Random();

		float xComp = forceDir.x * rand.nextInt((int) forceFactor)
				/ forceFactor;
		float yComp = forceDir.y * rand.nextInt((int) forceFactor)
				/ forceFactor;

		// Initial Force, need to randomise
		Vec2 thrust = new Vec2(xComp, yComp);
		astr.applyThrust(thrust);

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
		// Values need playing with, and to be stored
		world.step(60, 6, 3);
		world.clearForces();

		player.applyThrust(keyboard.getMovement());
		cameraFollowPlayer();
		player.update();

		// For every planetoid update it's sprite
		for (Planetoid p : planetoids) {
			p.update();
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
	}

	@Override
	public void onPointerEnd(playn.core.Pointer.Event event) {
		
	}

	public void movePlayer() {
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
	public void onPointerDrag(Event event) {
		// TODO Auto-generated method stub
		
	}
}