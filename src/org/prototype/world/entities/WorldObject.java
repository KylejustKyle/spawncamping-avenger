package org.prototype.world.entities;

public class WorldObject {
	public float x;
	public float y;
	public boolean hasPassedOrigin;
	public boolean hasFollowingWall = false;
	
	public WorldObject(float xPosition, float yPosition) {
		x = xPosition;
		y = yPosition;
		hasPassedOrigin = y >=0 ? true : false;
	}
}
