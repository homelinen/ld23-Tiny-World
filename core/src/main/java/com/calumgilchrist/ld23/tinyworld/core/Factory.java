package com.calumgilchrist.ld23.tinyworld.core;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import playn.core.GroupLayer;
import playn.core.Image;

public abstract class Factory {
	protected World world;
	protected Image img;
	protected GroupLayer layer;
	
	private static int cometCount;
	
	protected static ArrayList<Planetoid> instances = new ArrayList<Planetoid>();
	protected static ArrayList<Body> destroyList;
	
	public Factory(World world, Image img, GroupLayer layer){
		this.world = world;
		this.img = img;
		this.layer = layer;
		
		cometCount = 0;
		destroyList = new ArrayList<Body>();
	}
	
	public Factory(World world, GroupLayer layer){
		this.world = world;
		this.layer = layer;
		
		destroyList = new ArrayList<Body>();
	}
	
	public void update() {		
		//Destroy bodies to be destroyed
		for (Body body: destroyList) {
			world.destroyBody(body);
		}
		
		// For every planetoid update it's sprite
		for (Planetoid p : instances) {
			p.update();		
		}
		
		destroyList.clear();
	}
	
	protected static int getCometCount() {
		return cometCount;
	}
	
	protected static void setCometCount(int newCount) {
		cometCount = newCount;
	}
	
	protected static int getAsteroidCount() {
		return (instances.size() - getCometCount());
	}
}
