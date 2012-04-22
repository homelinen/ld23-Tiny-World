package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactListener implements org.jbox2d.callbacks.ContactListener {

	private Player player;
	
	public ContactListener(Player player) {
		this.player = player;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Body hitter;
		
		//Fixture A is never the contact, I think
		if (player.getBody().equals(contact.getFixtureB().m_body)) {
			
			hitter = contact.getFixtureA().m_body;
			player.addMass(hitter.m_mass);
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
