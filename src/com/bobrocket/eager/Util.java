package com.bobrocket.eager;

public class Util {

	public static int clamp(int val, int min, int max) {
		if (val > max) return max;
		if (val < min) return min;
		return val;
	}
}
