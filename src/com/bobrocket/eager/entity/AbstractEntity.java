package com.bobrocket.eager.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.bobrocket.eager.Game;
import com.bobrocket.eager.data.Vector;
import com.bobrocket.eager.graphics.Sprite;

public abstract class AbstractEntity implements IEntity {

	protected float x, y;
	protected Game game;
	private Vector pos, centrePos; //Keep this to reduce any mem usage
	
	protected Sprite sprite;
	protected int size;
	protected Rectangle rect;
	
	protected boolean reverseGrow;
	protected boolean resetGrowth;
	
	protected float min = 1f;
	protected float max = 1.25f;
	
	public AbstractEntity(Game game, Sprite sprite, int startX, int startY) {
		this.x = startX;
		this.y = startY;
		this.game = game;
		this.sprite = sprite;
		
		pos = new Vector(-1, -1);
		centrePos = new Vector(-1, -1);
		size = 1;
		
		rect = new Rectangle(getX(), getY(), sprite.getWidth(), sprite.getHeight());
	}
	
	public void setSize(int val) {
		size = val;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public int getX() {
		return (int)x;
	}

	@Override
	public int getY() {
		return (int)y;
	}
	
	public int getCentreX() {
		return (int)(x + (sprite.getWidth() / 2));
	}
	
	public int getCentreY() {
		return (int)(y + (sprite.getHeight() / 2));
	}

	@Override
	public Vector getPosition() {
		pos.setX(x);
		pos.setY(y);
		return pos;
	}
	
	public Vector getCentrePosition() {
		centrePos.setX(getCentreX());
		centrePos.setY(getCentreY());
		return centrePos;
	}

	@Override
	public Vector getVelocity() {
		return null;
	}


	@Override
	public Sprite getSprite() {
		return sprite;
	}
	
	public float getSizeScale() {
		return (size / 11);
	}
	
	public abstract void tick();
	public abstract void onCollide(AbstractEntity other);
	public abstract void render(Graphics g);

	public boolean alive() {
		return (size > 0);
	}
	
	public Rectangle getBounds() {
		rect.setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
		return rect;
	}
}
