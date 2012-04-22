package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import playn.core.Image;

public class Star extends Planetoid{

	private float temp;
	public Star(Sprite s, BodyDef bodyDef, World world, float m) {
		super(s, bodyDef, world, m);
		
		//Modified with Mass?
		temp = 1000;
	}
	
	/**
	 * Empty constructor for emptiness
	 */
	public Star() {
		super();
	}
	
	public float getTemp() {
		return temp;
	}	
}
