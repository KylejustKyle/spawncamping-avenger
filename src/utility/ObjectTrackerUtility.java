package utility;
import projectiles.Projectile;
import worldEntities.CollidableObject;
import worldEntities.WorldObject;

public class ObjectTrackerUtility {
	private static int WIDTH = 680;
	private static int HEIGHT = 480;
	
	// @TODO a lot of class heirarchy can go here to help make this more autowired.
	public static boolean isOutsideOfWindow(WorldObject object) {
		return isOutsideOfWindow(object.x, object.y);
	}
	
	public static boolean isOutsideOfWindow(CollidableObject object) {
		return isOutsideOfWindow(object.x, object.y);
	}
	
	public static boolean isOutsideOfWindow(Projectile projectile) {
		return isOutsideOfWindow(projectile.x, projectile.y);
	}
	
	private static boolean isOutsideOfWindow(int x, int y) {
		if(x < 0 || x >WIDTH) {
			return true;
		} else if( y < 0 || y > HEIGHT) {
			return true;
		} else {
			return false;
		}
	}
}
