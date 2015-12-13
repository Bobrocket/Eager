package com.bobrocket.eager.entity;

import java.awt.Graphics;

import com.bobrocket.eager.Audio;
import com.bobrocket.eager.Game;
import com.bobrocket.eager.graphics.Sprite;

public class Coin extends AbstractEntity {
	public Coin(Game game, int startX, int startY) {
		super(game, new Sprite("coin"), startX, startY);
	}

	@Override
	public void tick() {
		if (!reverseGrow && sprite.getScale() < 1.25f + getSizeScale()) {
			sprite.setScale(sprite.getScale() + 0.01f);
			if (sprite.getScale() >= max + getSizeScale()) reverseGrow = true;
		}
		
		if (reverseGrow && sprite.getScale() > min + getSizeScale()) {
			sprite.setScale(sprite.getScale() - 0.01f);
			if (sprite.getScale() <= min + getSizeScale()) reverseGrow = false;
		}
	}

	@Override
	public void onCollide(AbstractEntity other) {
		if (!alive() || !other.alive()) return;
		other.size += size;
		size = 0;
		Audio.play("coin");
	}

	@Override
	public void render(Graphics g) {
		if (alive()) g.drawImage(sprite.getImage(), getX(), getY(), null);
	}

}
