package com.calumgilchrist.ld23.tinyworld.core;

import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Asteroid extends Planetoid {

	public Asteroid(Vec2 startPos, Sprite s, BodyDef bodyDef, World world) {
		super(startPos, s, bodyDef, world);
		
	}

	/**
	 * Get a direction vector from start point, within screens
	 * height and width
	 * 
	 * @return
	 */
	public Vec2 getStartDirVec(float screenWidth, float screenHeight) {
		Vec2 start = this.getPos();
		Vec2 dir = new Vec2();
		
		//Find where the screen is, so Vec can go through it
		if (start.x < 0) {
			dir.x = 1;
		} else if (start.x >= screenWidth) {
			dir.x = -1;
		}
		
		if (start.y < 0) {
			dir.y = 1;
		} else if (start.y >= screenHeight) {
			dir.y = -1;
		}
		
		return dir;
	}
	
	public Vec2 genStartPos(float screenWidth, float screenHeight) {
		float x = getSpawnBound((int) screenWidth);
		float y = getSpawnBound((int) screenHeight);
		
		Vec2 pos = new Vec2(x, y);

		return pos;
	}
	
	/**
	 * Randomly choose a number NOT between 0 and limit
	 * @param limit
	 * @return
	 */
	private int getSpawnBound(int limit) {
		
		Random rand = new Random();
		
		int spawnBound = 100;
		//Set x
		boolean belowBound = rand.nextBoolean();
		
		int pos = rand.nextInt(spawnBound);
		
		if (belowBound) {
			pos = -pos;
		} 
		
		return pos;
		
	}
}
