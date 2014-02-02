import org.newdawn.slick.SlickException;


public class MockPlayer {
	
	public int x;
	public int y;
	public String uiPath;
	public int height;
	public int width;
	
	public MockPlayer() throws SlickException {
		x = 0;
		y = 0;
		height = 50;
		width = 50;
		uiPath = "resources/Test.png";
	}
}
