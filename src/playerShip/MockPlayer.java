package playerShip;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;


public class MockPlayer {
	
	public int x;
	public int y;
	public int middle;
	public int height;
	public int width;
	public Animation idle;
	public ShipVector vector;
	
	public MockPlayer(int startX, int startY) throws SlickException {
		x = startX;
		y = startY;
		height = 100;
		width = 100;
		middle = width/2;
		vector = ShipVector.CENTER;
	}
}
