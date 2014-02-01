import org.lwjgl.input.Keyboard;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

public class SimpleTest extends BasicGame {

	/**
	 * @author Stanley Pliskin
	 * 	In order for this test to run, we need to pass a VM argument to the native 
	 *  lib directory:
	 * -Djava.library.path=/Users/xxxxx/git/spawncamping-avenger/native/macosx
	 */
	private static AppGameContainer app;
	private char keystroke;
	
    public SimpleTest() {
        super("SimpleTest");
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {}

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called first in the game loop.
    // Monitor for keystroke in update logic, modify the player object - use this as the
    // entity message passing object to allow the render loop to act upon the entity.
    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    	
    }

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called after update.
    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        g.drawString("You've recently pressed: "+keystroke, 0, 200);
        
        // Monitor sustains
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
        	g.drawString("Pressing: "+keystroke, 0, 300);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
    	//Stub out a controller handler
    	keystroke = c;
    }
    
    public static void main(String[] args) {
        try {
            app = new AppGameContainer(new SimpleTest());
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}