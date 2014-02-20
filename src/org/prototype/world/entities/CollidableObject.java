package org.prototype.world.entities;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.prototype.globals.GlobalConfig;

public class CollidableObject extends WorldObject {
	public float width;
	public float height;
	public float xAxisVector;
	public float yAxisVector;
	public float xVelocity;
	public float yVelocity;
	public Shape boundingBox;
	
	// @TODO we need to change the way which we pass in the vector & velocity maps, should be a helper object that reduces the 
	// long parameter list smell
	public CollidableObject(Point position, float newWidth, float newHeight, float xAxisVector, float yAxisVector, float xVelocity, float yVelocity) {
		super(position.getX(), position.getY());
		boundingBox = new Rectangle(position.getX(), position.getY(), newWidth, newHeight);
		this.width = newWidth;
		this.height = newHeight;
		this.xAxisVector = xAxisVector;
		this.yAxisVector = yAxisVector;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;	
	}
	
	public void updateLocation() {
		float locationY = yVelocity * yAxisVector;
		this.y += locationY;
		//@TODO this needs to be autowired together with the x & y cooridnates
		this.boundingBox.setY(this.y);
	}
	
}
