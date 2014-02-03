import java.util.ArrayList;

import projectiles.Projectile;
import utility.ObjectTrackerUtility;

public class WorldProjectiles {
	ArrayList<Projectile> wProjectiles = null;
	
	public WorldProjectiles() {
		wProjectiles = new ArrayList<Projectile>();
		
	}
	
	// @TODO we can refactor this to be a generic world container with WorldObjects -> and just call each element
	// of the container to update themselves.
	public void updateProjectiles() {
		ArrayList<Projectile> removalSet = new ArrayList<Projectile>();
		
		for(Projectile projectile : wProjectiles ) {
			projectile.updateLocation();
			
			if(ObjectTrackerUtility.isOutsideOfWindow(projectile)) {
				removalSet.add(projectile);
			}
		}
		
		
		wProjectiles.removeAll(removalSet);
	}
}
