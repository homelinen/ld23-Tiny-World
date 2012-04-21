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
		Vec2 start = this.getBody().getWorldCenter().mul(Constants.PHYS_RATIO);
		Vec2 dir = new Vec2();
		
		//Find where the screen is, so Vec can go through it
		if (start.x <= -screenWidth) {
			dir.x = 1;
		} else if (start.x >= screenWidth) {
			dir.x = -1;
		} 
		
		if (start.y <= -screenHeight) {
			dir.y = 1;
		} else if (start.y >= screenHeight) {
			dir.y = -1;
		}
		
		return dir;
	}
}
