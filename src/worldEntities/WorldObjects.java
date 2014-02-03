package worldEntities;
import java.util.ArrayList;

import utility.ObjectTrackerUtility;


public class WorldObjects {
	public ArrayList<WorldObject> wObjects = null;
	
	public WorldObjects() {
		wObjects = new ArrayList<WorldObject>();
	}
	
	//This needs to be refactored to let the world object determine it's own path.
	public void pullObjectsDown(int burnFactor, int maxHeight) {
		ArrayList<WorldObject> removalSet = new ArrayList<WorldObject>();
		
		for(WorldObject worldObject : wObjects ) {
			worldObject.y += burnFactor;
			
			if(ObjectTrackerUtility.isOutsideOfWindow(worldObject)) {
				removalSet.add(worldObject);
			}
		}
		
		wObjects.removeAll(removalSet);
	}
}
