package org.prototype.world.entities;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.geom.Point;
import org.prototype.globals.GlobalConfig;

public class EnemyObject extends CollidableObject {
	//@TODO refactor the burnerTrail to be extracted into a super class
	public boolean isAlive;
	public LinkedList<Point> burnerTrail;
	public static int TRAIL_QUEUE_SIZE = 80;
	public Point previousPoint;
	
	public EnemyObject(Point point, float width, float height, float xAxisVector, float yAxisVector, float xVelocity, float yVelocity) {
		super(point, width, height, xAxisVector, yAxisVector, xVelocity, yVelocity);
		isAlive = true;
		burnerTrail = new LinkedList<Point>();
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
	
	public void addBurnerTrail(Point point) {
		if(burnerTrail.size() == TRAIL_QUEUE_SIZE) {
			burnerTrail.removeLast();
		}
		
		burnerTrail.push(point);
	}
	
	public void updateBlurTrail() {
        //@TODO This should not go in here as it's not controller related. This should be a call on the player object
        if(this.previousPoint.getX() != this.x || this.y != this.previousPoint.getY()) {
        	float burnerX = (this.x + this.width/2.0f);
        	float burnerY = (this.y);
        	
        	Random randomGen = new Random();
        	float expansionX = randomGen.nextInt(3);
        	float expansionY = randomGen.nextInt(3);
        	int direction = randomGen.nextInt(1) == 0 ? 1 : -1; 
        	
        	burnerX += direction*expansionX;
        	burnerY += direction*expansionY;
        	
        	addBurnerTrail(new Point(burnerX+10, burnerY));
        } else {
        	if(!burnerTrail.isEmpty()) {
        		burnerTrail.removeLast();
        	}
        }
	}
}
