import playerShip.MockPlayer;
import worldEntities.CollidableObject;
import worldEntities.CollidableObjects;


public class CollisionMarshal {
	public MockPlayer player;
	public CollidableObjects collidableObjects;
	
	public CollisionMarshal(CollidableObjects newCollidableObjects, MockPlayer newPlayer) {
		player = newPlayer;
		collidableObjects = newCollidableObjects;
	}
	
	public void runCollision() {
		for(CollidableObject collidableObject : collidableObjects.cObjects) {
			if(collidableObject.boundingBox.intersects(player.boundingBox)) {
				player.isAlive = false;
				player.shouldExplode = true;
				break;
			}
		}
	}
}
