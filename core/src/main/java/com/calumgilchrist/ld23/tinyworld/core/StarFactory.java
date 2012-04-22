package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.graphics;

import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import playn.core.GroupLayer;
import playn.core.Image;

public class StarFactory extends Factory{

	public StarFactory(World world, Image img, GroupLayer layer) {
		super(world, img, layer);
		// TODO Auto-generated constructor stub
	}
	
	public Star getStar(Vec2 startPosition) {
		//TODO: Randomise Force
		
		//Start Vector off screen (This should be random)
		startPosition = genStartPos(graphics().width(), graphics().height());
		
		// Set up an asteroid
		BodyDef astrBodyDef = new BodyDef();
		astrBodyDef.type= BodyType.STATIC;
		
		//Initial Position
		astrBodyDef.position.set(startPosition.mul(1/Globals.PHYS_RATIO));
		
		startPosition.mulLocal(Globals.globalScale);
		
		Star star = new Star(new Sprite((int) startPosition.x, (int) startPosition.y, img), astrBodyDef, world);
		
		instances.add(star);
		layer.add(star.getSprite().getImageLayer());
		
		return star;
	}
	
	public Vec2 genStartPos(int width, int height) {
		
		Random rand = new Random();
		float x = 0;
		float y = 0;
		
		int maxRand = 0;
		
		boolean xOffScreen = rand.nextBoolean();
		
		//Select a position off screen, and any other position
		//Between min and max (-height, height for y)
		if (xOffScreen) {
			x = getSpawnBound(-width, width);
			
			maxRand = height * 2; 
			
			//Get position between height and -height
			y = rand.nextInt(maxRand) - height;
		} else {
			y = getSpawnBound(-height, height);
			
			maxRand = height * 2; 
			
			//Get position between width and -width
			x = rand.nextInt(maxRand) - width;
		}

		Vec2 pos = new Vec2(x, y);

		return pos;
	}
	
	private static int getSpawnBound(int min, int max) {

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
}