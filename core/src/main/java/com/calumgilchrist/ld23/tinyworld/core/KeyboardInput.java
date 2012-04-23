package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.graphics;
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
	
	private TinyWorld root;
	private boolean pKeyDown;
	
	
	public KeyboardInput(TinyWorld root){
		keyboard().setListener(this);
		
		upKeyDown = false;
		downKeyDown = false;
		leftKeyDown = false;
		rightKeyDown = false;
		spaceDown  = false;
		
		this.root = root;
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
	
	public boolean ispKeyDown() {
		return pKeyDown;
	}

	@Override
	public void onKeyDown(Event event) {
		if(Globals.state == Globals.STATE_GAME){
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
			case P:
				pKeyDown = true;
				break;
			}
		}
	}

	@Override
	public void onKeyTyped(TypedEvent event) {
	}

	@Override
	public void onKeyUp(Event event) {
		if(Globals.state == Globals.STATE_MENU){
			switch (event.key()) {
			case W:
				Globals.currentItem = Globals.currentItem - 1;
				
				if(Globals.currentItem < 0){
					Globals.currentItem = Globals.numOfMenuItems - 1;
				}
				
				break;
			case S:
				Globals.currentItem = Globals.currentItem + 1;
				
				if(Globals.currentItem > (Globals.numOfMenuItems - 1)){
					Globals.currentItem = 0;
				}
				
				break;
			case ENTER:
				if(root.menus.handleMainMenu(Globals.currentItem)){
					root.gameInit();
				}
				break;
			case ESCAPE:
				System.exit(0);
				break;
			}
		}
		else if(Globals.state == Globals.STATE_CREDITS){
			switch (event.key()) {
			case W:
				Globals.currentItem = Globals.currentItem - 1;
				
				if(Globals.currentItem < 0){
					Globals.currentItem = Globals.numOfMenuItems - 1;
				}
				
				break;
			case S:
				Globals.currentItem = Globals.currentItem + 1;
				
				if(Globals.currentItem > (Globals.numOfMenuItems - 1)){
					Globals.currentItem = 0;
				}
				
				break;
			case ENTER:
				if(root.menus.handleCreditsMenu(Globals.currentItem)){
					root.gameInit();
				}
				break;
			case ESCAPE:
				graphics().rootLayer().remove(root.menus.menuLayer);
				Globals.state = Globals.STATE_MENU;
				root.menus.menuInit();
				break;
			}
		}
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
		case P:
			pKeyDown = false;
			break;
		}
	}
}