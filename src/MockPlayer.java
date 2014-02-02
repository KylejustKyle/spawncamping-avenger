import org.newdawn.slick.SlickException;


public class MockPlayer {
	
	public int x;
	public int y;
	public String uiPath;
	public int height;
	public int width;
	
	public MockPlayer(int startX, int startY) throws SlickException {
		x = startX;
		y = startY;
		height = 50;
		width = 50;
		uiPath = "resources/Test.png";
	}
}
