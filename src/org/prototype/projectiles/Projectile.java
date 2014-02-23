package org.prototype.projectiles;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public abstract class Projectile {
	protected int velocity;
	protected int team;
	public float x;
	public float y;
	public Shape boundingBox;
	
	public Projectile (int newVelocity, int newTeam, float startingX, float startingY, int width, int height) {
		velocity = newVelocity;
		team = newTeam;
		x = startingX;
		y = startingY;
		boundingBox = new Rectangle(x, y, width, height);
	}
	
	public abstract void updateLocation();
}
