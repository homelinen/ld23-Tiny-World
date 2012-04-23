package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactListener implements org.jbox2d.callbacks.ContactListener {

	private Player player;
	int createCount;
	DynamicFactory dynamicFactory;
	
	public ContactListener(Player player, DynamicFactory dynamicFactory) {
		this.player = player;
		createCount = 0;
		
		this.dynamicFactory = dynamicFactory;
	}
	
	@Override
	public void beginContact(Contact contact) {
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Body hitter;
		
		//Fixture A is never the contact, I think
		
		if (player.getBody().equals(contact.getFixtureA().m_body)) {
			
			hitter = contact.getFixtureB().m_body;
			Planetoid planet = dynamicFactory.getFromBody(hitter);
			
			if (planet != null) {
				discernCollisionType(planet);
				player.addMass(hitter.m_mass);
	
				dynamicFactory.removeByBody(hitter);
				createCount++;
				System.out.println("Knock B");
			}
		} if (player.getBody().equals(contact.getFixtureB().m_body)) {
			
			hitter = contact.getFixtureA().m_body;
			Planetoid planet = dynamicFactory.getFromBody(hitter);
			
			if (planet != null) {
				discernCollisionType(planet);
				player.addMass(hitter.m_mass);
	
				dynamicFactory.removeByBody(hitter);
				createCount++;
				System.out.println("Knock A");
			}
		}
		
	}
	
	// Work out whether the collision is with a comet or asteroid and apply the correct rule
	public void discernCollisionType(Planetoid planet){
		if (planet.getClass().equals((new Asteroid().getClass()))){
			//System.out.println("Asteroid collision");
			player.setAtmosphere(player.getAtmosphere()-10);
		}
		else{
			//System.out.println("Comet collision");
			player.setAtmosphere((int) (player.getAtmosphere()+5));
		}
	}
		
	public int getCreateCount() {
		return createCount;
	}
	
	public void clearCreateCount() {
		createCount = 0;
	}
}