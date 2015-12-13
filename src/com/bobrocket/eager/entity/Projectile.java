package com.bobrocket.eager.entity;

import java.awt.Graphics;

import com.bobrocket.eager.Audio;
import com.bobrocket.eager.Game;
import com.bobrocket.eager.data.Vector;
import com.bobrocket.eager.graphics.Sprite;

public class Projectile extends AbstractEntity {

	private final Vector dest;
	
	public Projectile(Game game, int startX, int startY, Vector mouse, int size) {
		super(game, new Sprite("player"), startX, startY);
		this.size = size;
		this.dest = new Vector(mouse.getX(), mouse.getY());
		
		sprite.setScale(min + getSizeScale());// = min + getSizeScale();
	}

	@Override
	public void tick() {
		if (!alive()) return;
		if (getCentrePosition().distance(dest) <= (5 + getBounds().getWidth())) return;
		System.out.println(dest.toString());
		Vector diff = dest.minus(getCentrePosition());

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
		
		x += 3 * (newX);
		y += 3 * (newY);
		
		if (x > 512) x = 512;
		if (x < 0) x = 0;
		
		if (y > 512) y = 512;
		if (y < 0) y = 0;
	}

	@Override
	public void onCollide(AbstractEntity other) {
		if (!alive() || !other.alive()) return;
		
		if (other instanceof Projectile) {
			size += other.size;
			other.size = 0;
			reverseGrow = false;
			Audio.play("enemyHit");
		}
		
		/*if (other instanceof Enemy) {
			if (other.size > size) {
				other.size += size;
				size = 0;
			}
			else {
				size += other.size;
				other.size = 0;
			}
		}*/
		
		if ((other instanceof Player) && getCentrePosition().distance(dest) <= (5 + getBounds().getWidth())) {
			other.size += size;
			other.reverseGrow = false;
			size = 0;
			Audio.play("playerHit");
		}
	}
	
	@Override
	public float getSizeScale() {
		return (size - 30) / 11f;
	}

	@Override
	public void render(Graphics g) {
		if (alive()) g.drawImage(sprite.getImage(), getX(), getY(), null);
	}

}
