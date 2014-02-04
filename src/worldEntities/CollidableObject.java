package worldEntities;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class CollidableObject extends WorldObject {
	public int width;
	public int height;
	public Shape boundingBox;
	
	public CollidableObject(int xPosition, int yPosition, int width, int height) {
		super(xPosition, yPosition);
		boundingBox = new Rectangle(xPosition, yPosition, width, height);
	}
	
	public void updateLocation() {
		
	}
	
}
