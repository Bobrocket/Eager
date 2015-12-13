package com.bobrocket.eager.entity;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.bobrocket.eager.Audio;
import com.bobrocket.eager.Game;
import com.bobrocket.eager.Util;
import com.bobrocket.eager.data.Vector;
import com.bobrocket.eager.graphics.Sprite;

public class Player extends AbstractEntity {

	private boolean sentProjectile = false;
	
	public Player(Game game, int startX, int startY) {
		super(game, new Sprite("player"), startX, startY);
		size = 30;
	}
	
	@Override
	public void tick() {
		if (!alive()) return;
		if (game.getMouseHandler().getPosition().equals(new Vector(-1, -1))) return;
		if (getCentrePosition().equals(game.getMouseHandler().getPosition())) return;
		
		if (game.getMouseHandler().isClicked() && !sentProjectile && size > 1) {
			sentProjectile = true;
			size /= 2;
			Vector v = game.getMouseHandler().getPosition();
			Projectile p = new Projectile(game, getX(), getY(), v, size);
			game.getEnemies().add(p);
			reverseGrow = true;
			
			Audio.play("playerHit");
		}
		
		if (!game.getMouseHandler().isClicked() && sentProjectile) sentProjectile = false;
		
		Vector diff = game.getMouseHandler().getPosition().minus(getCentrePosition());

		double theta = Math.atan(diff.getY() / diff.getX());
		double newX = Math.cos(theta);
		double newY = Math.sin(theta);
		
		if ((diff.getX() < 0 && diff.getY() < 0) || (diff.getX() < 0 && diff.getY() > 0)) {
			newX = -newX;
			newY = -newY;
		}
		
		if (diff.getX() < 0 && diff.getY() == 0) { //glitch where would move away from mouse where diffY = 0
			newX = -newX;
		}
		
		if (resetGrowth) {
			sprite.setScale(1f + getSizeScale());
			reverseGrow = false;
			resetGrowth = false;
		}
		
		//size++;
		
		if (!reverseGrow && sprite.getScale() < 1.25f + getSizeScale()) {
			sprite.setScale(sprite.getScale() + 0.01f);
			if (sprite.getScale() >= max + getSizeScale()) reverseGrow = true;
		}
		
		if (reverseGrow && sprite.getScale() > min + getSizeScale()) {
			sprite.setScale(sprite.getScale() - 0.01f);
			if (sprite.getScale() <= min + getSizeScale()) reverseGrow = false;
		}
		
		//if (1f <= sprite.getScale() && sprite.getScale() < 1.25f) sprite.setScale(sprite.getScale() + 0.05f);
		//if (1.25f >= sprite.getScale() && sprite.getScale() > 1.04f) sprite.setScale(sprite.getScale() - 0.05f);
		//if (sprite.getScale() < 1.25f) sprite.setScale(sprite.getScale() + 0.05f);
		//else sprite.setScale(sprite.getScale() - 0.05f);
		
		//newX = (game.getMouseHandler().getPosition().getX() < getCentrePosition().getX()) ? -newX : newX;
		//newY = (game.getMouseHandler().getPosition().getY() > getCentrePosition().getY()) ? -newY : newY;
		
		//if (newX == 1 || newY == 1) return;
		
		x += newX;//(diff.getX() / hypotenuse) / 4;//diff.getX();
		y += newY;//(diff.getY() / hypotenuse) / 4;//diff.getY();
		
		if (x > 512) x = 512;
		if (x < 0) x = 0;
		
		if (y > 512) y = 512;
		if (y < 0) y = 0;
	}
	
	@Override
	public float getSizeScale() {
		return Math.abs((size - 30) / 11);
	}
	
	@Override
	public void onCollide(AbstractEntity other) {
	}

	@Override
	public void render(Graphics g) {
		if (alive()) g.drawImage(sprite.getImage(), getX(), getY(), null);
	}

}
