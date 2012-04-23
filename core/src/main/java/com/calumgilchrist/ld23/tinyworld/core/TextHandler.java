package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.graphics;

import org.jbox2d.common.Vec2;

import playn.core.CanvasImage;
import playn.core.Color;
import playn.core.Font;
import playn.core.ImageLayer;
import playn.core.TextFormat;
import playn.core.TextFormat.Alignment;
import playn.core.TextLayout;

/**
 * Handle Text. Allow for creating layers and drawing new text 
 * per frame.
 * 
 * @author homelinen
 *
 */
public class TextHandler {

	private TextFormat textFormat;
	private CanvasImage canv;
	private ImageLayer iLayer;
	private TextLayout textLayer;
	private String message;
	private Font textFont;
	
	private Vec2 pos;
	
	public TextHandler(String message, Vec2 pos) {
		setText(message);
		
		textFont = graphics().createFont("Courier", Font.Style.BOLD, 12);
		textFormat = new TextFormat(textFont, 100, Alignment.LEFT, Color.rgb(255, 255,255), new TextFormat().effect);
		textLayer = graphics().layoutText("" + message, textFormat);
	}
	
	/**
	 * Render text to show an FPS Counter
	 */
	public void initText() {
		
		//TODO: Tweak size
		canv = graphics().createImage((int) textLayer.width() + 50, (int) textLayer.width() + 50);
		canv.canvas().drawText(textLayer, 20, 20);
		iLayer = graphics().createImageLayer(canv);
		
		graphics().rootLayer().add(iLayer);
	}
	
	public void drawFpsCounter() {
		canv.canvas().clear();
		textLayer = graphics().layoutText("" + message, textFormat);
		
		canv.canvas().drawText(textLayer, 20, 20);
	}
	
	public void setText(String text) {
		this.message = text;
	}
	
	public Vec2 getPos() {
		return pos;
	}
	
	public void setPos(Vec2 pos) {
		this.pos = pos;
	}
}
