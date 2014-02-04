package playerShip;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class MockPlayer {
	
	public int x;
	public int y;
	public int middle;
	public int height;
	public int width;
	public Shape boundingBox;
	public Animation idle;
	public ShipVector vector;
	public boolean isAlive;
	
	public MockPlayer(int startX, int startY) throws SlickException {
		x = startX;
		y = startY;
		height = 100;
		width = 100;
		boundingBox = new Rectangle(x, y, width, height);
		middle = width/2;
		vector = ShipVector.CENTER;
		isAlive = true;
	}
}
