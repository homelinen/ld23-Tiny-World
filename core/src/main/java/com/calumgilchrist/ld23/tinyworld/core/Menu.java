package com.calumgilchrist.ld23.tinyworld.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;

import playn.core.CanvasLayer;
import playn.core.Font;
import playn.core.Layer;
import playn.core.TextFormat;
import playn.core.TextLayout;

public class Menu {

	Font titleFont;
	Font textFont;

	int startY;

	TextLayout layout;
	Layer layer;

	String titleText;
	ArrayList<String> menuItems;

	public Menu(String titleText, int startY) {
		titleFont = graphics().createFont("Courier", Font.Style.BOLD, 32);
		textFont = graphics().createFont("Courier", Font.Style.BOLD, 24);

		this.titleText = titleText;
		this.startY = startY;

		menuItems = new ArrayList<String>();
	}

	public void addMenuItem(String itemText) {
		menuItems.add(itemText);
	}

	public void setTitleFont(Font font) {
		titleFont = font;
	}

	public void setTextFont(Font font) {
		textFont = font;
	}

	public Layer getTitle() {
		String text = "Tiny World";
		layout = graphics().layoutText(
				text,
				new TextFormat().withFont(titleFont)
						.withWrapping(200, TextFormat.Alignment.CENTER)
						.withEffect(TextFormat.Effect.shadow(0x33000000, 2, 2))
						.withTextColor(0xFFFFFFFF));
		layer = createTextLayer(layout);
		layer.setTranslation((graphics().width() / 2) - (layout.width() / 2),
				startY);

		return layer;
	}

	public ArrayList<Layer> getMenuItemLayers() {
		ArrayList<Layer> layers = new ArrayList<Layer>();

		for (int i = 0; i < menuItems.size(); i++) {
			layout = graphics().layoutText(
					menuItems.get(i),
					new TextFormat()
							.withFont(textFont)
							.withWrapping(200, TextFormat.Alignment.CENTER)
							.withEffect(
									TextFormat.Effect.shadow(0x33000000, 2, 2))
							.withTextColor(0xFFFFFFFF));
			layer = createTextLayer(layout);
			layer.setTranslation((graphics().width() / 2) - (layout.width() / 2),
					startY + (100 * (i+1)));
			layers.add(layer);
		}

		return layers;
	}

	protected Layer createTextLayer(TextLayout layout) {
		@SuppressWarnings("deprecation")
		CanvasLayer layer = graphics().createCanvasLayer(
				(int) Math.ceil(layout.width()),
				(int) Math.ceil(layout.height()));
		layer.canvas().drawText(layout, 0, 0);
		return layer;
	}
}