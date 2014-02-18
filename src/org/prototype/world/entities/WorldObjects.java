package org.prototype.world.entities;
import java.util.ArrayList;

import org.prototype.globals.GlobalConfig;
import org.prototype.utility.ObjectTrackerUtility;

//This is being extracted to walls class
public class WorldObjects {
	public ArrayList<WorldObject> wObjects = null;
	
	public WorldObjects() {
		wObjects = new ArrayList<WorldObject>();
	}
	
	//This needs to be refactored to let the world object determine it's own path.
	public void updateObjects(int burnFactor, int maxHeight) {
		ArrayList<WorldObject> removalSet = new ArrayList<WorldObject>();
		ArrayList<WorldObject> insertionSet = new ArrayList<WorldObject>();
		
		for(WorldObject worldObject : wObjects ) {
			worldObject.y += burnFactor;
			
			if(ObjectTrackerUtility.isOutsideOfWindow(worldObject)) {
				removalSet.add(worldObject);
			}
			
			if(!worldObject.hasPassedOrigin) {
				if(worldObject.y >= 0) {
					worldObject.hasPassedOrigin = true;
				}
			}
			
			if(worldObject.hasPassedOrigin && !worldObject.hasFollowingWall) {
				insertionSet.add(new WorldObject(worldObject.x, -544 + worldObject.y-3));
				worldObject.hasFollowingWall = true;
			}
		}
		

		wObjects.addAll(insertionSet);
		wObjects.removeAll(removalSet);
		//Check to see if the current object's origin is at the top of the screen, if so, add another one to the queue
	}
}
