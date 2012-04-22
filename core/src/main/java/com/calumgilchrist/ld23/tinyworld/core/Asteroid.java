package com.calumgilchrist.ld23.tinyworld.core;


import static playn.core.PlayN.graphics;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Asteroid extends Planetoid {

	public Asteroid(Vec2 startPos, Sprite s, BodyDef bodyDef, World world) {
		super(s, bodyDef, world);
		
	}

	/**
	 * Get a direction vector from start point, within screens
	 * height and width
	 * 
	 * @return
	 */
	public Vec2 getStartDirVec(float screenWidth, float screenHeight) {
		Vec2 start = this.getBody().getWorldCenter().mul(Globals.PHYS_RATIO);
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
	
	/**
	 * Get the force vector to fly in
	 * @param forceFactor
	 */
	public Vec2 getThrustForce(float forceFactor) {

		//Apply a force to the asteroid
		Vec2 forceDir = this.getStartDirVec(graphics().width(), graphics().height());
		
		/*
		 * Generate a nice random vector by multiplying direction by random numbers 
		 */
		
		Random rand = new Random();
		
		float xComp = forceDir.x * rand.nextInt((int) forceFactor) / forceFactor;
		float yComp = forceDir.y * rand.nextInt((int) forceFactor) / forceFactor;
		
		//Initial Force, need to randomise
		Vec2 thrust = new Vec2(xComp, yComp);
		
		return thrust;
	}
}