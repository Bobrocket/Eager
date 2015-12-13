package com.bobrocket.eager.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bobrocket.eager.Game;
import com.bobrocket.eager.data.input.Key;

public class KeyHandler implements KeyListener {

	private Game game;
	private Map<Integer, Boolean> keys = new HashMap<Integer, Boolean>();
	
	public KeyHandler(Game game) {
		this.game = game;
		game.addKeyListener(this);
		
		//keys.add(new Key(KeyEvent.VK_W));
		//keys.add(new Key(KeyEvent.VK_S));
		keys.put(KeyEvent.VK_SPACE, false);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public void toggleKey(int keyCode, boolean isPressed) {
		keys.put(keyCode, isPressed);
		//key.setToggled(isPressed);
	}
	
	public boolean isKeyPressed(int keyCode) {
		if (!keys.containsKey(keyCode)) return false;
		return keys.get(keyCode);
	}

}
