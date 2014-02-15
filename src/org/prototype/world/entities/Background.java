package org.prototype.world.entities;

public class Background {
	public float x;
	public float y;
	public boolean hasPassedOrigin;
	public boolean hasFollowingBackground = false;
	
	public Background(float newX, float newY) {
		x = newX;
		y = newY;
	   hasPassedOrigin = y >0 ? true : false;
	}
}
