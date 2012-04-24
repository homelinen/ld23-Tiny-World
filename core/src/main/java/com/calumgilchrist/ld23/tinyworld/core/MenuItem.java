package com.calumgilchrist.ld23.tinyworld.core;

import playn.core.Layer;
import playn.core.TextLayout;

public class MenuItem {
	TextLayout layout;
	Layer layer;
	
	String text;
	
	int posX;
	int posY;
	
	public MenuItem(int posX, int posY, TextLayout tl, Layer l, String text){
		layout = tl;
		layer = l;
		this.posX = posX;
		this.posY = posY;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public TextLayout getLayout() {
		return layout;
	}

	public void setLayout(TextLayout layout) {
		this.layout = layout;
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}
	
	public boolean withinBounds(int x, int y){
		if(x > this.getPosX() && x < (this.getPosX() + this.getLayout().width())){
			if(y > this.getPosY() && y < (this.getPosY() + this.getLayout().height())){
				return true;
			}	
		}
		return false;
	}
}
