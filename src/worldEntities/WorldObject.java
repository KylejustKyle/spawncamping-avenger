package worldEntities;

public class WorldObject {
	public String uiPath;
	public int x;
	public int y;
	
	public WorldObject(int xPosition, int yPosition) {
		x = xPosition;
		y = yPosition;
		uiPath = "resources/VerticalLine.png";
	}
}
