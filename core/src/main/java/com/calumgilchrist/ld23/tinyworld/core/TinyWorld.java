package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;

public class TinyWorld implements Game {
	ArrayList<Planetoid> planetoids;

	@Override
	public void init() {
		planetoids = new ArrayList<Planetoid>();

		// create and add background image layer
		Image bgImage = assets().getImage("images/bg.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
		
		// Load grey asteroid image asset
		Image asteroid = assets().getImage("images/grey-asteroid.png");

		// Create a testing planetoid and add it to the arraylist
		Planetoid p = new Planetoid(20, 20, new Sprite(20, 20));
		p.getSprite().addFrame(asteroid);
		graphics().rootLayer().add(p.getSprite().getImageLayer());
		planetoids.add(p);
	}

	@Override
	public void paint(float alpha) {
		// the background automatically paints itself, so no need to do anything
		// here!
		
	}

	@Override
	public void update(float delta) {
		// For every planetoid update it's sprite
		for (Planetoid p : planetoids) {
			p.getSprite().update();
		}
	}

	@Override
	public int updateRate() {
		return 25;
	}
}
