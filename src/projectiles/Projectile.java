package projectiles;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public abstract class Projectile {
	protected int velocity;
	protected int team;
	public int x;
	public int y;
	public Shape boundingBox;
	
	public Projectile (int newVelocity, int newTeam, int startingX, int startingY, int width, int height) {
		velocity = newVelocity;
		team = newTeam;
		x = startingX;
		y = startingY;
		boundingBox = new Rectangle(x, y, width, height);
	}
	
	public abstract void updateLocation();
}
