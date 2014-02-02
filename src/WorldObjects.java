import java.util.ArrayList;


public class WorldObjects {
	ArrayList<WorldObject> wObjects = null;
	
	public WorldObjects() {
		wObjects = new ArrayList<WorldObject>();
	}
	
	public void pullObjectsDown(int burnFactor, int maxHeight) {
		ArrayList<WorldObject> removalSet = new ArrayList<WorldObject>();
		
		for(WorldObject worldObject : wObjects ) {
			worldObject.y += burnFactor;
			
			if(worldObject.y > maxHeight) {
				removalSet.add(worldObject);
			}
		}
		
		wObjects.removeAll(removalSet);
	}
}
