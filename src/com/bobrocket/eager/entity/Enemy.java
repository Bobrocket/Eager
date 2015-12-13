package com.bobrocket.eager.entity;

import java.awt.Graphics;

import com.bobrocket.eager.Audio;
import com.bobrocket.eager.Game;
import com.bobrocket.eager.data.Vector;
import com.bobrocket.eager.graphics.Sprite;

public class Enemy extends AbstractEntity {
	protected float speed = 0.5f;
	
	private int sizeBase = 30;
	
	public Enemy(Game game, int startX, int startY) {
		super(game, new Sprite("enemy"), startX, startY);
		size = 10;
		
		sprite.setScale(min + getSizeScale());// = min + getSizeScale();
	}

	@Override
	public void tick() {
		if (!alive()) return;
		if (getCentrePosition().equals(game.getPlayer().getCentrePosition())) return;
		
		Vector diff = game.getPlayer().getCentrePosition().minus(getCentrePosition());

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
		
		x += speed * (newX);
		y += speed * (newY);
		
		if (x > 512) x = 512;
		if (x < 0) x = 0;
		
		if (y > 512) y = 512;
		if (y < 0) y = 0;
	}

	@Override
	public void onCollide(AbstractEntity other) {
		if (!alive() || !other.alive()) return;
		
		if (other instanceof Player) {
			other.size -= size;
			size = 0;
			other.reverseGrow = true;
			Audio.play("playerHit");
			
		}
			if (size > other.size) {
				size += other.size;
				other.size = 0;
				reverseGrow = false;
				Audio.play("enemyHit");
			}
			else {
				other.size += size;
				size = 0;
				other.reverseGrow = false;
				Audio.play("enemyHit");
			}
		//}
	}

	@Override
	public void render(Graphics g) {
		if (alive()) g.drawImage(sprite.getImage(), getX(), getY(), null);
	}
	
	/*@Override
	public void setSize(int val) {
		sizeBase = val;
		size = val;
	}*/
	
	@Override
	public float getSizeScale() {
		float sc = (size - sizeBase) / 11;
		return (sc >= 0) ? sc : 0;
	}
	

}
