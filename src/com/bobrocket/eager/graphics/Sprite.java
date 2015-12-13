package com.bobrocket.eager.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private BufferedImage fullImage;
	
	private int width, height;
	
	private float scale;
	
	public Sprite(String name) {
		try {
			fullImage = ImageIO.read(getClass().getResourceAsStream("/sprites/" + name + ".png"));
			height = fullImage.getHeight();
			width = fullImage.getWidth();
			scale = 1f;
		}
		catch (IOException ex) {
			width = -1;
			height = -1;
			scale = 1f;
		}
	}
	
	public int getWidth() {
		return (int)(width * scale);
	}
	
	public int getHeight() {
		return (int)(height * scale);
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float val) {
		if (val <= 0.1f) return;
		scale = val;
	}
	
	public BufferedImage getImage() {
		if (scale == 1f || (getWidth() == width || getHeight() == height)) return fullImage;
		return scale();
	}
	
	private BufferedImage scale() {
		if (fullImage == null) return null;
		BufferedImage scaled = new BufferedImage((int)(width * scale), (int)(height * scale), fullImage.getType());
		Graphics2D g = scaled.createGraphics();
		AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
		g.drawRenderedImage(fullImage, at);
		return scaled;
	}
	
}
