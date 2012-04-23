package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Player extends Planetoid {
	
	private float atmosphere;
	private float heat;

	public Player(Sprite s, BodyDef bodyDef, World world) {
		super(s, bodyDef, world, 1);

		setAtmosphere(0);
		heat = 0;
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
	}
	
	public float getMass(){
		return this.getBody().m_mass;
	}

	public float getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(float atmosphere) {
		this.atmosphere = atmosphere;
	}
	
	public float getHeat() {
		return heat;
	}

	public void updateHeat() {
		this.heat = StarFactory.getHeat(this.getBody().getWorldCenter());
		this.atmosphere = this.atmosphere - StarFactory.getAtmosphereDrain(this.getBody().getWorldCenter());
	}
}
