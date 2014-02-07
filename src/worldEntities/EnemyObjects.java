package worldEntities;

import java.util.ArrayList;

import utility.ObjectTrackerUtility;

public class EnemyObjects {
	public ArrayList<EnemyObject> eObjects = null;
	
	public EnemyObjects() {
		eObjects = new ArrayList<EnemyObject>();
	}

	public void updateObects() {
		ArrayList<CollidableObject> removalSet = new ArrayList<CollidableObject>();
		
		for(EnemyObject enemyObject : eObjects) {
			enemyObject.updateLocation();
			
			if(ObjectTrackerUtility.isOutsideOfWindow(enemyObject)) {
				removalSet.add(enemyObject);
			}
		}
		
		eObjects.removeAll(removalSet);
	}
}
