package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Comet extends Planetoid{

	public Comet(Sprite s, BodyDef bodyDef, World world, float m) {
		super(s, bodyDef, world, m);
		// TODO Auto-generated constructor stub
	}
	
	public Comet() {
		super();
	}
}
