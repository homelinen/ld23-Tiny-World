package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import playn.core.CanvasImage;
import playn.core.Color;
import playn.core.DebugDrawBox2D;
import playn.core.Font;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Sound;
import playn.core.TextFormat;
import playn.core.TextFormat.Alignment;
import playn.core.TextLayout;

public class TinyWorld implements Game {

	ArrayList<Asteroid> planetoids;
	ArrayList<Body> destroyList;

	Player player;
	World world;

	DynamicFactory factory;
	StarFactory starFactory;
	
	private static final float nanoToSecs = 1E9f;

	private static final int spawnIntervalSecs = 4;
	private long lastSpawnTime;
	
	
	private static final boolean debugPhysics = false;
	
	//FPS
	int frameCount;
	int fps;
	long oldTime;
	
	private KeyboardInput keyboard;
	private MouseInput mouse;

	private ContactListener contactListner;

	private ImageLayer debugLayer;
	
	private DebugDrawBox2D debugDraw;
	private CanvasImage canv;

	private static GroupLayer planetoidLayer;
	Image planetoidImage;
	
	boolean createAstr;
	
	Menus menus;
	
	Sound clickSound;
	
	//FPS Text Stuff
	private CanvasImage fpsTextImage;
	private ImageLayer fpsTextLayer;
	private TextLayout fpsTextLayout;
	private TextFormat fpsTextformat;
	
	@Override
	public void init() {	
		menus = new Menus();
		
		keyboard = new KeyboardInput();
		mouse = new MouseInput(this);
		
		createAstr = false;
		
		Globals.globalScale = 1.0f;
				
		//Set up FPS
		frameCount = 0;
		fps = 0;
		oldTime = System.nanoTime();
		lastSpawnTime = System.nanoTime();
		
		planetoidLayer = graphics().createGroupLayer();
		
		planetoidImage = assets().getImage("images/planetoid.png");

		Globals.state = Globals.STATE_MENU;
				
		// create and add background image layer
		Image bgImage = assets().getImage("images/starfield.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
		
		graphics().setSize(1024, 768);
		
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
		
		factory.createDebris(5);
		
		for (int i = 0; i < 1; i++){
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
		
		contactListner = new ContactListener(player);
		world.setContactListener(contactListner);

		setScale(2.0f);
		
		debugInit();
		
		initFPSCounter();
	}
	
	public void debugInit() {
		//Debug stuff
		debugDraw = new DebugDrawBox2D();
		
		int scaleCanvasSize = 10;
		canv = graphics().createImage(graphics().width() * scaleCanvasSize,graphics().height() * scaleCanvasSize);
		debugDraw.setCanvas(canv);
		debugDraw.setFlipY(false);
		debugDraw.setStrokeAlpha(100);
		debugDraw.setFillAlpha(50);
		debugDraw.setStrokeWidth(1.0f);
		debugDraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit);
		debugDraw.setCamera(0, 0, 2); 
		
		world.setDebugDraw(this.debugDraw);
		
		debugLayer = graphics().createImageLayer(canv);
		
		graphics().rootLayer().add(debugLayer);
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
		if(Globals.state == Globals.STATE_GAME){
			
			if (debugPhysics) {
				canv.canvas().clear();
				world.drawDebugData();
			}
		}
		
		//Calculate FPS
		frameCount++;
		if (frameCount > 100) {
			float curTime = System.nanoTime();
			
			float dTime = (curTime - oldTime) / nanoToSecs;
			
			oldTime = (long) curTime;
			fps = (int) (frameCount / dTime);
			frameCount = 0;
			drawFpsCounter();
		}
	}

	@Override
	public void update(float delta) {
		if(Globals.state == Globals.STATE_GAME){
			

			if (debugPhysics) {
				world.drawDebugData();
			}
			
			// Values need playing with, and to be stored
			world.step(30, 6, 3);
			world.clearForces();
	
			if (!keyboard.isSpaceDown()) {
				player.applyThrust(keyboard.getMovement());
			} else {
				//When space is pressed, set Velocity to 0
				player.getBody().setLinearVelocity(new Vec2());
			}
			
			planetoidGenerator();

			spawnBodies();
			
			cameraFollowPlayer();
			cameraFollowDebug();
			
			player.update();
	
			factory.update();
		}
	}

	@Override
	public int updateRate() {
		return 25;
	}
	
	/**
	 * Spawn bodies after spawnIntervale frames 
	 */
	public void spawnBodies() {
		long curTime = System.nanoTime();
		long dTime = (long) ((curTime - lastSpawnTime) / nanoToSecs);
		
		if (dTime > spawnIntervalSecs) {
			factory.createDebris(1);
			lastSpawnTime = curTime;
		}
	}
	
	/**
	 * Generate deleted planetoids and replace them
	 */
	public void planetoidGenerator() {
		
		//Creates an asteroid if one was previously destroyed
		//What happens if two were destroyed?
		factory.createDebris(contactListner.getCreateCount());
		
		contactListner.clearCreateCount();
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
	
	public void cameraFollowDebug() {
		int tx;
		tx = (int) (player.getBody().getWorldCenter().x * Globals.PHYS_RATIO);
		tx = (int) (tx - graphics().width()/2);

		int ty;
		ty = (int) (player.getBody().getWorldCenter().y * Globals.PHYS_RATIO);
		ty = (int) (ty - graphics().height()/2);
		
		debugLayer.setOrigin(tx, ty);
	}

	public void movePlayer() {
		//What is this?
	}
	
	/**
	 * Render text to show an FPS Counter
	 */
	public void initFPSCounter() {
		Font textFont = graphics().createFont("Courier", Font.Style.BOLD, 12);
		fpsTextformat = new TextFormat(textFont, 20, Alignment.LEFT, Color.rgb(50, 255, 247), new TextFormat().effect);
		
		fpsTextLayout = graphics().layoutText("" + fps, fpsTextformat);
		
		fpsTextImage = graphics().createImage(100, 100);
		fpsTextImage.canvas().drawText(fpsTextLayout, 20, 20);
		fpsTextLayer = graphics().createImageLayer(fpsTextImage);
		
		graphics().rootLayer().add(fpsTextLayer);
	}
	
	public void drawFpsCounter() {
		fpsTextImage.canvas().clear();
		fpsTextLayout = graphics().layoutText("" + fps, fpsTextformat);
		
		fpsTextImage.canvas().drawText(fpsTextLayout, 20, 20);
	}
}