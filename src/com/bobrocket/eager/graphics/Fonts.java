package com.bobrocket.eager.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Fonts {

	private static Map<String, Font> fonts = new HashMap<String, Font>();
	
	public static void drawText(Graphics g, String fontName, String text, float size, int x, int y) {
		if (!fonts.containsKey(fontName)) loadFont(fontName);
		g.setFont(fonts.get(fontName).deriveFont(size));
		g.drawString(text, x, y);
	}
	
	public static void loadFont(String name) {
		try {
			fonts.put(name, Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/" + name + ".ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
