package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.math.*;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.MouseJoint;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.Pointer;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;

public class TinyWorld implements Game, Keyboard.Listener, Pointer.Listener {
	ArrayList<Planetoid> planetoids;
	
	Player player;
	
	World world;
	MouseJoint mouseJoint;
	
	private boolean upKeyDown;
	private boolean downKeyDown;
	private boolean leftKeyDown;
	private boolean rightKeyDown;
	
	private GroupLayer planetoidLayer;
	// TODO remove everything from root layer
	
	@Override
	public void init() {		
		initKeyboard();
		
		planetoids = new ArrayList<Planetoid>();
		
		pointer().setListener(this);
		keyboard().setListener(this);
	
		// create and add background image layer
		Image bgImage = assets().getImage("images/starfield.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
		
		// Load grey asteroid image asset
		Image asteroid = assets().getImage("images/grey-asteroid.png");

		Vec2 pStart = new Vec2(20,20);
		
		//Set up the world
		world = new World(new Vec2(), false);
		
		//TODO asteroidInit
		
		//Start Vector off screen (This should be random)
		Vec2 astrStart = new Vec2(100,100);
		
		//Set up an asteroid
		BodyDef astrBodyDef = new BodyDef();
		astrBodyDef.type= BodyType.DYNAMIC;
		
		// Create the player body definition
		//Initial Position
		astrBodyDef.position.set(astrStart.mul(1/Constants.PHYS_RATIO));
		
		Asteroid astr = new Asteroid(astrStart, new Sprite((int) astrStart.x, (int) astrStart.y), astrBodyDef, world);
		astr.getSprite().addFrame(asteroid);
		
		//Apply a force to the asteroid
		//Vec2 forceDir = astr.getStartDirVec(graphics().width(), graphics().height());
		// astr.applyThrust(forceDir.mul(0.1f));
		
		planetoids.add(astr);
		graphics().rootLayer().add(astr.getSprite().getImageLayer());
	
		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DYNAMIC;
		playerBodyDef.position.set(new Vec2(60,60).mul(1/Constants.PHYS_RATIO));
		
		// Create a player planetoid and add it to the root layer
		player = new Player(new Vec2(60,60),new Sprite(100,100), playerBodyDef, world);
		graphics().rootLayer().add(player.getSprite().getImageLayer());
		player.getSprite().addFrame(asteroid);
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
		// here!
	}

	@Override
	public void update(float delta) {
		//Values need playing with, and to be stored
		world.step(60, 30, 30);
		world.clearForces();
		
		handleKeyboard();
		player.update();
		cameraFollowPlayer();
		
		// For every planetoid update it's sprite
		for (Planetoid p : planetoids) {
			p.update();
		}
	}

	@Override
	public int updateRate() {
		return 25;
	}
	
	public void cameraFollowPlayer(){	
		graphics().rootLayer().setOrigin( player.getPos().x,player.getPos().y);
	}
	
	public void initKeyboard(){
		upKeyDown = false;
		downKeyDown = false;
		leftKeyDown = false;
		rightKeyDown = false;
	}
	
	public void handleKeyboard(){
		int px, py;
		px = py = 0;
		
		if(upKeyDown){
			py = py - 1;
		}
		if(downKeyDown){
			py = py + 1;
		}
		if(leftKeyDown){
			px = px - 1;
		}
		if(rightKeyDown){
			px = px + 1;
		}
		player.applyThrust(new Vec2(px,py));
	}

	@Override
	public void onKeyDown(Event event) {
		switch (event.key()) {
		case W:
			upKeyDown = true;
			break;
		case A:
			leftKeyDown = true;
			break;
		case S:
			downKeyDown = true;
			break;
		case D:
			rightKeyDown = true;
			break;
		case ESCAPE:
			System.exit(0);
		}
		
	}

	@Override
	public void onKeyTyped(TypedEvent event) {
	}

	@Override
	public void onKeyUp(Event event) {
		// TODO Auto-generated method stub
		switch (event.key()) {
		case W:
			upKeyDown = false;
			break;
		case A:
			leftKeyDown = false;
			break;
		case S:
			downKeyDown = false;
			break;
		case D:
			rightKeyDown = false;
			break;
		}
	}

	@Override
	public void onPointerStart(playn.core.Pointer.Event event) {
	}

	@Override
	public void onPointerEnd(playn.core.Pointer.Event event) {
	}

	@Override
	public void onPointerDrag(playn.core.Pointer.Event event) {
	}
	
	public void movePlayer(){
	}
}
