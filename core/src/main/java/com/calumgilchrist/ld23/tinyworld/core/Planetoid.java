package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Planetoid {
	private Sprite sprite;
	private Body pBody;
	
	public Planetoid(Sprite s, BodyDef bodyDef, World world){
		//TODO: Need an initial mass
		
		this.sprite = s;
		this.pBody = world.createBody(bodyDef);
		
		this.pBody.setAwake(false);
		
		CircleShape circle = new CircleShape();
		
		float magicBoundRatio = 2.2f;
		circle.m_radius = this.sprite.getWidth() / Constants.PHYS_RATIO / magicBoundRatio;
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = circle;
		
		pBody.createFixture(fixDef);
	}
	
	
	
	/**
	 * Apply force to the center of the planetoid
	 * @param force
	 */
	public void applyThrust(Vec2 force) {

		force = force.mul(1/Constants.PHYS_RATIO);
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
		Vec2 center = this.getBody().getWorldCenter().mul(Constants.PHYS_RATIO);
		
		this.getSprite().setX((int) center.x);
		this.getSprite().setY((int) center.y);
		this.getSprite().update();
	}
}