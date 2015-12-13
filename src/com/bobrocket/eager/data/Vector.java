package com.bobrocket.eager.data;

import com.bobrocket.eager.Util;

public class Vector {
	private float x, y;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float val) {
		x = val;
	}
	
	public void setY(float val) {
		y = val;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Vector)) return false;
		Vector v = (Vector) other;
		return (v.getX() == x) && (v.getY() == y);
	}
	
	public Vector minus(Vector other) {
		return new Vector((x - other.getX()), (y - other.getY()));
	}
	
	public Vector minusClamped(Vector other) {
		return new Vector(Util.clamp((int)(x - other.getX()), -1, 1), Util.clamp((int)(y - other.getY()), -1, 1));
	}
	
	public float distance(Vector other) {
		Vector v = minus(other);
		return (Math.abs(v.getX()) + Math.abs(v.getY()));
	}
	
	public String toString() {
		return "[x " + x + " y " + y + "]";
	}
}
