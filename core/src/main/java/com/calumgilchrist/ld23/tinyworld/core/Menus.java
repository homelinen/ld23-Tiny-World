package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;

import playn.core.GroupLayer;

public class Menus {
	public ArrayList<Menu> menus;
	
	public ArrayList<MenuItem> menuItemLayers;
	public GroupLayer menuLayer;
	
	public void menuInit() {
		menus = new ArrayList<Menu>();
		
		//clickSound = assets().getSound("sounds/select");
		Globals.currentItem = 0;
		Globals.menuSin = 0;
		Globals.state = Globals.STATE_MENU;
		Menu mainMenu = new Menu("Tiny World",60 * (graphics().height() / 480));
		menuLayer = graphics().createGroupLayer();
		menuLayer.add(mainMenu.getTitle());
		
		mainMenu.addMenuItem("New Game");
		mainMenu.addMenuItem("Credits");
		mainMenu.addMenuItem("Exit");
	    
	    menuItemLayers = mainMenu.getMenuItems();
	    for(MenuItem l : menuItemLayers){
	    	menuLayer.add(l.getLayer());
	    }
	    
	    graphics().rootLayer().add(menuLayer);
	}
	
	public boolean handleMainMenu(int mousex, int mousey){			
		for(MenuItem mi : menuItemLayers){
			if(mousex > mi.getPosX() && mousex < (mi.getPosX() + mi.getLayout().width())){
				if(mousey > mi.getPosY() && mousey < (mi.getPosY() + mi.getLayout().height())){
					if(mi.getText() == "New Game"){
						return true;
					}
					else if(mi.getText() == "Credits"){
						creditsMenuInit();
					}
					else if(mi.getText() == "Exit"){
//						System.exit(0);
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean handleMainMenu(int index){
		if(index == 0){
			return true;
		}else if(index == 1){
			creditsMenuInit();
		}else if(index == 2){
//			System.exit(0);
		}
		
		return false;
	}
	
	public void creditsMenuInit(){
		Globals.state = Globals.STATE_CREDITS;
		Globals.currentItem = 0;
		graphics().rootLayer().remove(menuLayer);
		Menu creditsMenu = new Menu("Credits",60);
		menuLayer = graphics().createGroupLayer();
		menuLayer.add(creditsMenu.getTitle());
		
		creditsMenu.addMenuItem("Calum Gilchrist");
		creditsMenu.addMenuItem("Daniel Bell");
		creditsMenu.addMenuItem("Music - brandon75689");
		creditsMenu.addMenuItem("Back");
		
		menuItemLayers = creditsMenu.getMenuItems();
	    for(MenuItem l : menuItemLayers){
	    	menuLayer.add(l.getLayer());
	    }
	    
	    graphics().rootLayer().add(menuLayer);
	}
	
	public boolean handleCreditsMenu(int index){
		if(index == 3){
			graphics().rootLayer().remove(menuLayer);
			menuInit();
		}
		
		return false;
	}
	
	public void handleCreditsMenu(int mousex, int mousey){
		for(MenuItem mi : menuItemLayers){
			if(mousex > mi.getPosX() && mousex < (mi.getPosX() + mi.getLayout().width())){
				if(mousey > mi.getPosY() && mousey < (mi.getPosY() + mi.getLayout().height())){
					if(mi.getText() == "Back"){
						graphics().rootLayer().remove(menuLayer);
						menuInit();
					}
				}
			}
		}
	}
}