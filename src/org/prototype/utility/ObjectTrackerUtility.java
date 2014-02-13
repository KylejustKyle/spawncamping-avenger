package org.prototype.utility;
import org.prototype.projectiles.Projectile;
import org.prototype.world.entities.CollidableObject;
import org.prototype.world.entities.WorldObject;

public class ObjectTrackerUtility {
	private static int WIDTH = 960;
	private static int HEIGHT = 544;
	
	// @TODO a lot of class heirarchy can go here to help make this more autowired.
	public static boolean isOutsideOfWindow(WorldObject object) {
		return isLeavingBottom(object.x, object.y);
	}
	
	public static boolean isOutsideOfWindow(CollidableObject object) {
		return isOutsideOfWindow(object.x, object.y);
	}
	
	public static boolean isOutsideOfWindow(Projectile projectile) {
		return isOutsideOfWindow(projectile.x, projectile.y);
	}
	
	private static boolean isOutsideOfWindow(float x, float y) {
		if(x < 0 || x >WIDTH) {
			return true;
		} else if( y < 0 || y > HEIGHT) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isLeavingBottom(float x, float y) {
		if(y > HEIGHT) {
			return true;
		} else {
			return false;
		}
	}
}
