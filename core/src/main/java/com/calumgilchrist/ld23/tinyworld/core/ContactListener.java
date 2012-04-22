package com.calumgilchrist.ld23.tinyworld.core;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactListener implements org.jbox2d.callbacks.ContactListener {

	private Player player;
	private int createCount;
	
	public ContactListener(Player player) {
		this.player = player;
		createCount = 0;
	}
	
	@Override
	public void beginContact(Contact contact) {

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Body hitter;
		
		//Fixture A is never the contact, I think
		if (player.getBody().equals(contact.getFixtureB().m_body)) {
			
			System.out.println("Hit player, begin");
			
			hitter = contact.getFixtureA().m_body;
			player.addMass(hitter.m_mass);
			DynamicFactory.removeByBody(hitter);
			
			createCount++;
		}
	}

	public int getCreateCount() {
		return createCount;
	}
	
	/**
	 * Clear the list of Planets to be created
	 */
	public void resetCreateCount() {
		createCount = 0;
	}
	
}