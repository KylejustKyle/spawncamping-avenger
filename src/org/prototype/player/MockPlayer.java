package org.prototype.player;
import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class MockPlayer {
	public static int TRAIL_QUEUE_SIZE = 140;
	public int x;
	public int y;
	public int middle;
	public int height;
	public int width;
	public Shape boundingBox;
	public Animation idle;
	public ShipVector vector;
	public boolean isAlive;
	public boolean shouldExplode;
	public LinkedList<Point> burnerTrail;
	public Point previousPoint;
	
	
	public MockPlayer(int startX, int startY) throws SlickException {
		x = startX;
		y = startY;
		height = 100;
		width = 100;
		boundingBox = new Rectangle(x, y, width, height);
		middle = width/2;
		vector = ShipVector.CENTER;
		isAlive = true;
		
		// @TODO this is definitely a hack; need a better solution to message pass events for queue.
		shouldExplode = false;
		burnerTrail = new LinkedList<Point>();
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
        	// @TODO Introduce some variability in the points so it looks like nice dynamic noise,
        	// not just a straight line
        	addBurnerTrail(new Point(this.x+15 , this.y + this.height-20));
        	addBurnerTrail(new Point(this.x + (this.width-18), this.y + this.height-20));
        } else {
        	if(!burnerTrail.isEmpty()) {
        		burnerTrail.removeLast();
        		
        	}
        }
	}
}
