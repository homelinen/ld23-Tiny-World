package com.calumgilchrist.ld23.tinyworld.core;

public class Planetoid {
	private Sprite sprite;
	
	private int x;
	private int y;
	
	public Planetoid(int posX, int posY, Sprite s){
		x = posX;
		y = posY;
		s = sprite;
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
