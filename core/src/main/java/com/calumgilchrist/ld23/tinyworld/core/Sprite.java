package com.calumgilchrist.ld23.tinyworld.core;

import java.util.ArrayList;
import playn.core.*;

public class Sprite {

	private ImageLayer layer;
	private int currentFrame;
	private ArrayList<Image> frames;
	private Image currentImage;
	
	private int x;
	private int y;

	public Sprite(int posX, int posY) {
		x = posX;
		y = posY;
		
		currentFrame = 0;
		
		layer = PlayN.graphics().createImageLayer();
		frames = new ArrayList<Image>();
	}
	
	public ImageLayer getImageLayer(){
		return layer;
	}
	
	public void addFrame(Image i){
		frames.add(i);
	}
	
	public void setX(int posX){
		x = posX;
	}
	
	public void setY(int posY){
		y = posY;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setFrame(int frame) {
		currentFrame = frame;
	}

	public void incCurrentFrame() {
		currentFrame = currentFrame + 1;

		if (currentFrame > frames.size()) {
			currentFrame = 0;
		}
	}

	public void update() {
		currentImage = frames.get(currentFrame);

		if(currentImage != null){			
			layer.setImage(currentImage);
			layer.setWidth(currentImage.width());
			layer.setHeight(currentImage.height());
			layer.setSourceRect(x, y, currentImage.width(), currentImage.height());
		}
	}
}