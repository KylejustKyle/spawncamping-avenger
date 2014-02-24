package org.prototype.world.entities;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.geom.Point;
import org.prototype.globals.GlobalConfig;
import org.prototype.math.Function;
import org.prototype.math.FunctionFactory;
import org.prototype.math.FunctionType;
import org.prototype.projectiles.VerticalProjectile;
import org.prototype.projectiles.WorldProjectiles;
import org.prototype.utility.IntervalTimer;

public class EnemyObject extends CollidableObject {
	//@TODO refactor the burnerTrail to be extracted into a super class
	public boolean isAlive;
	public LinkedList<Point> burnerTrail;
	public static int TRAIL_QUEUE_SIZE = 80;
	public Point previousPoint;
	IntervalTimer fireTimer;
	Function pathFunction;
	
	public EnemyObject(Point point, float width, float height, float xAxisVector, float yAxisVector, float newVelocity, FunctionType pathType) {
		super(point, width, height, xAxisVector, yAxisVector, newVelocity);
		isAlive = true;
		burnerTrail = new LinkedList<Point>();
		fireTimer = new IntervalTimer(900);
		pathFunction = FunctionFactory.createFunction(pathType);
	}
	
	public EnemyObject clone() {
		return new EnemyObject(new Point(this.x, this.y), width, height, xAxisVector, yAxisVector, velocity, pathFunction.getType());
	}
	
	@Override
	public void updateLocation() {
		float locationX = velocity * xAxisVector;
		if((this.x+locationX) > GlobalConfig.GAME_WIDTH || (this.x+locationX) < 0 ) {
			xAxisVector*= -1;
		} else {
			// @TODO we want to make this automatic - interface to update x or y would make the change in the bounding box.
			this.x += locationX;
			this.y =  pathFunction.compute(this.x);
			this.boundingBox.setX(this.x);
			this.boundingBox.setY(this.y);
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
        	
        	burnerX += direction;
        	burnerY += direction;
        	
        	addBurnerTrail(new Point(burnerX-18, burnerY+18));
        	addBurnerTrail(new Point(burnerX+14, burnerY+18));
        } else {
        	if(!burnerTrail.isEmpty()) {
        		burnerTrail.removeLast();
        	}
        }
	}
	
	/**
	 * This is the fire method. Here we should define different fire types and patterns.
	 */
	public void shoot(WorldProjectiles wProjectiles) {
		if(fireTimer.isInterval()) {
			wProjectiles.wProjectiles.add(new VerticalProjectile(2, 0, this.boundingBox.getCenterX(), this.boundingBox.getY(), 10, 10));
			fireTimer.rootTime = System.currentTimeMillis();
		}
	}
}
