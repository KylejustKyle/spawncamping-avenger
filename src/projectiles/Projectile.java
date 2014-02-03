package projectiles;


public abstract class Projectile {
	protected int velocity;
	protected int team;
	public int x;
	public int y;
	
	public Projectile (int newVelocity, int newTeam, int startingX, int startingY) {
		velocity = newVelocity;
		team = newTeam;
		x = startingX;
		y = startingY;
	}
	
	public abstract void updateLocation();
}
