package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Star extends Planetoid{

	private float temp;
	public Star(Sprite s, BodyDef bodyDef, World world) {
		super(s, bodyDef, world);
		
		//Modified with Mass?
		temp = 1000;
	}
	public float getTemp() {
		return temp;
	}	
}
