package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.graphics;

import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class Planetoid {
	private Sprite sprite;
	private Body pBody;
	
	public Planetoid(Sprite s, BodyDef bodyDef, World world, float mass){
		//TODO: Need an initial mass
		
		System.out.println("Planetoid created");
		
		this.sprite = s;
		this.pBody = world.createBody(bodyDef);
		
		CircleShape circle = new CircleShape();
		
		circle.m_radius = this.sprite.getWidth() / Globals.PHYS_RATIO / Globals.magicBoundRatio;
		
		FixtureDef fixDef = new FixtureDef();
	
		fixDef.shape = circle;
		
		pBody.createFixture(fixDef);
		pBody.m_mass = mass;
	}
	
	/**
	 * Another Empty Constructor to Not Use
	 */
	public Planetoid() {
		
	}
	
	/**
	 * Apply force to the center of the planetoid
	 * @param force
	 */
	public void applyThrust(Vec2 force) {

		force = force.mul(1/Globals.PHYS_RATIO);
		this.getBody().applyForce(force, this.getBody().getWorldCenter());
	}
	
	public Sprite getSprite(){
		return sprite;
	}

	public Body getBody() {
		return this.pBody;
	}
	
	public void newBody(BodyDef bodyDef, World world) {
		this.pBody = world.createBody(bodyDef);
	}
	
	public void update(){
		Vec2 center = this.getBody().getPosition().mul(Globals.PHYS_RATIO);
		
		// CircleShape circle = new CircleShape();
		// circle.m_radius = this.sprite.getWidth() * Globals.globalScale / Globals.PHYS_RATIO / Globals.magicBoundRatio;
		
		this.getSprite().setX((int) center.x);
		this.getSprite().setY((int) center.y);
		this.getSprite().update();
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
