package com.bobrocket.eager.entity;

import com.bobrocket.eager.data.Vector;
import com.bobrocket.eager.graphics.Sprite;

public interface IEntity {
	public int getX();
	public int getY();
	public Vector getPosition();
	public Vector getVelocity();
	public Sprite getSprite();
	
	public int getSize();
}
