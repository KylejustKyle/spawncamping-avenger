package org.prototype.utility;
import org.prototype.globals.GlobalConfig;
import org.prototype.projectiles.Projectile;
import org.prototype.world.entities.Background;
import org.prototype.world.entities.CollidableObject;
import org.prototype.world.entities.WorldObject;

public class ObjectTrackerUtility {
	
	// @TODO a lot of class heirarchy can go here to help make this more autowired.
	public static boolean isOutsideOfWindow(WorldObject object) {
		return isLeavingBottom(object.x, object.y);
	}
	
	public static boolean isOutsideOfWindow(Background object) {
		return isLeavingBottom(object.x, object.y);
	}
	
	public static boolean isOutsideOfWindow(CollidableObject object) {
		return isOutsideOfWindow(object.x, object.y);
	}
	
	public static boolean isOutsideOfWindow(Projectile projectile) {
		return isOutsideOfWindow(projectile.x, projectile.y);
	}
	
	private static boolean isOutsideOfWindow(float x, float y) {
		if(x < 0 || x >GlobalConfig.GAME_WIDTH) {
			return true;
		} else if( y < 0 || y > GlobalConfig.GAME_HEIGHT) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isLeavingBottom(float x, float y) {
		if(y > GlobalConfig.GAME_HEIGHT) {
			return true;
		} else {
			return false;
		}
	}
}
