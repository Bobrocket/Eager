package com.bobrocket.eager.entity;


import java.awt.Graphics;

import com.bobrocket.eager.Game;

public class ZoomerEnemy extends Enemy {

	public ZoomerEnemy(Game game, int startX, int startY) {
		super(game, startX, startY);
		speed = 2f;
	}
	
	@Override
	public void render(Graphics g) {
		if (!alive()) return;
		g.drawImage(sprite.getImage(), getX(), getY(), null);
	}
}
