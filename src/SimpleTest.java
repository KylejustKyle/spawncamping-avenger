import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimpleTest extends BasicGame {

	/**
	 * @author Stanley Pliskin
	 * 	In order for this test to run, we need to pass a VM argument to the native 
	 *  lib directory:
	 * -Djava.library.path=/Users/xxxxx/git/spawncamping-avenger/native/macosx
	 */
	private static AppGameContainer app;
	private char keystroke;
	private static MockPlayer player;
	
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
        // Monitor sustains
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
        	player.y += 1;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
        	player.y -= 1;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
        	player.x -= 1;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
        	player.x += 1;
        }
    }

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called after update.
    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        renderPlayer(g);

    }
    
    private void renderPlayer(Graphics g) throws SlickException {
    	g.drawImage(new Image(player.uiPath, false, 0), player.x, player.y);
    }
    
    public static void main(String[] args) {
        try {
            app = new AppGameContainer(new SimpleTest());
        	player = new MockPlayer();
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}