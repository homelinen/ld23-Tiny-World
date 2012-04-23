package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Player extends Planetoid {
	
	private int atmosphere;
	private int heat;

	public Player(Sprite s, BodyDef bodyDef, World world) {
		super(s, bodyDef, world, 1);

		setAtmosphere(0);
		setHeat(0);
	}

	// TODO: Score or counter for mass (Increase in size would suffice
	/**
	 * Add mass to the object
	 * 
	 * @param mass
	 */
	public void addMass(float mass) {
		float oldMass = this.getBody().m_mass;

		float newMass = oldMass + mass;

		this.getBody().m_mass = newMass;

		System.out.println("Mass: " + newMass);
	}
	
	public float getMass(){
		return this.getBody().m_mass;
	}

	public int getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(int atmosphere) {
		this.atmosphere = atmosphere;
	}
	
	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}
}
