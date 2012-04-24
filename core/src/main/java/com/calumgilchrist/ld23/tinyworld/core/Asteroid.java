package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Asteroid extends Planetoid {

	public Asteroid(Vec2 startPos, Sprite s, BodyDef bodyDef, World world, float m) {
		super(s, bodyDef, world, m);
	}
	
	public Asteroid(){
		
	}
}