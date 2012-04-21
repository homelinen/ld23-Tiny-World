package com.calumgilchrist.ld23.tinyworld.core;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Planetoid {
	private Sprite sprite;
	private Body pBody;
	
	private int x;
	private int y;
	
	public Planetoid(int posX, int posY, Sprite s, BodyDef bodyDef, World world){
		x = posX;
		y = posY;
		
		sprite = s;
		pBody = world.createBody(bodyDef);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
