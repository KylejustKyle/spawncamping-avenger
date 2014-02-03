package Projectiles;


public class VerticalProjectile extends Projectile{

	public VerticalProjectile(int newVelocity, int newTeam, int startingX, int startingY) {
		super(newVelocity, newTeam, startingX, startingY);
	}

	@Override
	public void updateLocation() {
		int direction = (this.team == 0 ? 1 : -1);
		y += (direction * velocity);
	}
}
