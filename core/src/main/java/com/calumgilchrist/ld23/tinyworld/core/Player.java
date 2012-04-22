package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Player extends Planetoid {

	
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
}
