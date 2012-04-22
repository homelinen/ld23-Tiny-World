package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class Player extends Planetoid implements ContactListener{

	
	public Player(Sprite s, BodyDef bodyDef, World world) {
		super(s, bodyDef, world);
	
	}
	
	//TODO: Score or counter for mass (Increase in size would suffice
	/**
	 * Add mass to the object
	 * @param mass
	 */
	public void addMass(float mass) {
		float oldMass = this.getBody().m_mass;
		
		float newMass = oldMass + mass;
		
		this.getBody().m_mass = newMass;
		
		System.out.println("Mass: " + newMass);
	}
	
	@Override
	public void beginContact(Contact contact) {
		Body hitter;
		
		System.out.println("TOUCHED FOR THE VERY FIRST TIME");
		//Fixture A is never the contact, I think
		if (getBody().equals(contact.getFixtureB().m_body)) {
			
			hitter = contact.getFixtureA().m_body;
			addMass(hitter.m_mass);
			AsteroidFactory.removeAstrByBody(hitter);
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		contact.getFixtureB().m_body.setLinearVelocity(new Vec2 (0,0));
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
	}
}
