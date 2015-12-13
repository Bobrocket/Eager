package com.bobrocket.eager.data.input;

public class Key {

	private int keyCode;
	private boolean toggled;
	
	public Key(int keyCode) {
		this.keyCode = keyCode;
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public void setToggled(boolean b) {
		toggled = b;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
}
