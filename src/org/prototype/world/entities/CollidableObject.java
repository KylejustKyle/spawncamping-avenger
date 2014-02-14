package org.prototype.world.entities;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.prototype.globals.GlobalConfig;

public class CollidableObject extends WorldObject {
	public int width;
	public int height;
	public int xAxisVector;
	public int yAxisVector;
	public int xVelocity;
	public int yVelocity;
	public Shape boundingBox;
	
	// @TODO we need to change the way which we pass in the vector & velocity maps, should be a helper object that reduces the 
	// long parameter list smell
	public CollidableObject(Point position, int width, int height, int xAxisVector, int yAxisVector, int xVelocity, int yVelocity) {
		super(position.getX(), position.getY());
		boundingBox = new Rectangle(position.getX(), position.getY(), width, height);
		this.xAxisVector = xAxisVector;
		this.yAxisVector = yAxisVector;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;	
	}
	
	public void updateLocation() {
		int locationY = yVelocity * yAxisVector;
		this.y += locationY;
		//@TODO this needs to be autowired together with the x & y cooridnates
		this.boundingBox.setY(this.y);
	}
	
}