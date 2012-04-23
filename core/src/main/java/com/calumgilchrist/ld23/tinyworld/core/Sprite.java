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
	
	private float scale;

	public Sprite(int posX, int posY, Image img) {
		x = posX;
		y = posY;
		
		currentFrame = 0;
		scale = 1.0f;
		
		layer = PlayN.graphics().createImageLayer();
		frames = new ArrayList<Image>();
		addFrame(img);
		
		//Need to do this to initialise the imageLayer
		update();
	}

	/**
	 * Empty Constructor
	 * 
	 * Really, don't use it
	 */
	public Sprite() {
	}
	
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
		layer.setScale(scale);
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
	
	public float getHeight() {
		return currentImage.height();
	}
	
	public float getWidth() {
		return currentImage.width();
	}
	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setFrame(int frame) {
		if(frame < 0 || frame > frames.size()){
			System.out.println("Error - frame: "+frame+" is out of bounds");
			System.exit(-1);
		}
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
			layer.setTranslation(x, y);
			layer.setImage(currentImage);
			layer.setWidth(currentImage.width());
			layer.setHeight(currentImage.height());
			//layer.setSourceRect(x, y, currentImage.width(), currentImage.height());
		}
	}
}