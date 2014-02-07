package worldEntities;

public class EnemyObject extends CollidableObject {
	int direction;
	public boolean isAlive;
	
	public EnemyObject(int xPosition, int yPosition, int width, int height) {
		super(xPosition, yPosition, width, height);
		direction = 1;
		isAlive = true;
	}
	
	@Override
	public void updateLocation() {
		int locationX = 1 * direction;
		
		if((this.x+locationX) > 680 || (this.x+locationX) < 0 ) {
			direction*= -1;
		} else {
			// @TODO we want to make this automatic - interface to update x or y would make the change in the bounding box.
			this.x += locationX;
			this.boundingBox.setX(this.x);
		}
	}
}
