package com.bobrocket.eager.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.bobrocket.eager.Game;
import com.bobrocket.eager.data.Vector;

public class MouseHandler implements MouseMotionListener, MouseListener {

	private Vector mousePos = new Vector(-1, -1);
	
	private boolean clicked = false;
	
	public MouseHandler(Game game) {
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		clicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clicked = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos.setX(e.getX());
		mousePos.setY(e.getY());
	}
	
	public Vector getPosition() {
		return mousePos;
	}
	
	public boolean isClicked() {
		return clicked;
	}

}
