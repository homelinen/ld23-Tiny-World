package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.pointer;
import playn.core.Pointer;
import playn.core.Pointer.Event;

public class MouseInput implements Pointer.Listener{
	TinyWorld root;
	
	public MouseInput(TinyWorld root){
		pointer().setListener(this);
		this.root = root;
	}
	
	@Override
	public void onPointerStart(playn.core.Pointer.Event event) {
		int mousex = (int) event.x();
		int mousey = (int) event.y();
		
		if(Globals.state == Globals.STATE_MENU){			
			if(root.menus.handleMainMenu(mousex,mousey)){
				root.gameInit();
			}
		}
		else if(Globals.state == Globals.STATE_CREDITS){
			root.menus.handleCreditsMenu(mousex,mousey);
		}
	}

	@Override
	public void onPointerEnd(playn.core.Pointer.Event event) {
		
	}

	@Override
	public void onPointerDrag(Event event) {
		// TODO Auto-generated method stub
		
	}
}
