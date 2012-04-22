package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.keyboard;

import org.jbox2d.common.Vec2;

import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;

public class KeyboardInput implements Keyboard.Listener {
	// Boolean values that represent whether a key is currently pressed or not
	private boolean upKeyDown;
	private boolean downKeyDown;
	private boolean leftKeyDown;
	private boolean rightKeyDown;
	private boolean spaceDown;
	
	
	public KeyboardInput(){
		keyboard().setListener(this);
		
		upKeyDown = false;
		downKeyDown = false;
		leftKeyDown = false;
		rightKeyDown = false;
		spaceDown  = false;
	}
	
	public Vec2 getMovement(){
		int px, py;
		px = py = 0;
		
		if(upKeyDown){
			py = py - (int) Globals.PHYS_RATIO;
		}
		if(downKeyDown){
			py = py + (int) Globals.PHYS_RATIO;
		}
		if(leftKeyDown){
			px = px - (int) Globals.PHYS_RATIO;
		}
		if(rightKeyDown){
			px = px + (int) Globals.PHYS_RATIO;
		}
		if (spaceDown) {
			px = py = 0;
		}
		
		return new Vec2(px,py);
	}
	
	public boolean isSpaceDown() {
		return spaceDown;
	}
	
	@Override
	public void onKeyDown(Event event) {
		switch (event.key()) {
		case W:
			upKeyDown = true;
			break;
		case A:
			leftKeyDown = true;
			break;
		case S:
			downKeyDown = true;
			break;
		case D:
			rightKeyDown = true;
			break;
		case ESCAPE:
			System.exit(0);
			break;
		case SPACE:
			spaceDown = true;
			break;
		}
	}

	@Override
	public void onKeyTyped(TypedEvent event) {
	}

	@Override
	public void onKeyUp(Event event) {
		// TODO Auto-generated method stub
		switch (event.key()) {
		case W:
			upKeyDown = false;
			break;
		case A:
			leftKeyDown = false;
			break;
		case S:
			downKeyDown = false;
			break;
		case D:
			rightKeyDown = false;
			break;
		case SPACE:
			spaceDown = false;
		break;
		}
	}
}
