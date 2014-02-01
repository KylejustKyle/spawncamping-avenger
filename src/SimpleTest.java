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

    public SimpleTest() {
        super("SimpleTest");
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {}

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called first in the game loop.
    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    	System.out.println("Update");
    }

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called after update.
    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        g.drawString("Hello, Slick world!", 0, 100);
        System.out.println("Render");
    }

    @Override
    public void keyPressed(int key, char c) {
    	//Stub out a controller handler
    	switch (key) {
    	case 'w':

    	}
    }
    
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new SimpleTest());
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}