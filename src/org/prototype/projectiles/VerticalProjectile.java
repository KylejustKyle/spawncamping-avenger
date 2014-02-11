package org.prototype.projectiles;


public class VerticalProjectile extends Projectile{

	public VerticalProjectile(int newVelocity, int newTeam, int startingX, int startingY, int width, int height) {
		super(newVelocity, newTeam, startingX, startingY, width, height);
	}

	@Override
	public void updateLocation() {
		int direction = (this.team == 0 ? 1 : -1);
		y += (direction * velocity);
		this.boundingBox.setY(y);
	}
}
