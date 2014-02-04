package worldEntities;

import java.util.ArrayList;

import utility.ObjectTrackerUtility;

public class CollidableObjects {
	public ArrayList<CollidableObject> cObjects = null;
	
	public CollidableObjects() {
		cObjects = new ArrayList<CollidableObject>();
	}
	
	public void updateObjects() {
		ArrayList<CollidableObject> removalSet = new ArrayList<CollidableObject>();
		
		for(CollidableObject collidableObject : cObjects) {
			collidableObject.updateLocation();
			
			if(ObjectTrackerUtility.isOutsideOfWindow(collidableObject)) {
				removalSet.add(collidableObject);
			}
		}
		
		cObjects.removeAll(removalSet);
	}
}
