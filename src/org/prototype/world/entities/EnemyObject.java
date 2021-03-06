package org.prototype.world.entities;

import org.newdawn.slick.geom.Point;
import org.prototype.globals.GlobalConfig;

public class EnemyObject extends CollidableObject {
	public boolean isAlive;
	
	public EnemyObject(Point point, float width, float height, float xAxisVector, float yAxisVector, float xVelocity, float yVelocity) {
		super(point, width, height, xAxisVector, yAxisVector, xVelocity, yVelocity);
		isAlive = true;
	}
	
	@Override
	public void updateLocation() {
		float locationX = xVelocity * xAxisVector;
		if((this.x+locationX) > GlobalConfig.GAME_WIDTH || (this.x+locationX) < 0 ) {
			xAxisVector*= -1;
		} else {
			// @TODO we want to make this automatic - interface to update x or y would make the change in the bounding box.
			this.x += locationX;
			this.boundingBox.setX(this.x);
		}
	}
}
