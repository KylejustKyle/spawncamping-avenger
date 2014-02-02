import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimpleTest extends BasicGame {

	/**
	 * @author Stanley Plisskin
	 * 	In order for this test to run, we need to pass a VM argument to the native 
	 *  lib directory:
	 * -Djava.library.path=/Users/xxxxx/git/spawncamping-avenger/native/macosx
	 */
	private static AppGameContainer app;
	private static MockPlayer player;
	private static PlayerInputController controller;
	private static GameState currentState;
	private static int burnFactor = 1;
	private static WorldObjects worldObjects = null;
	private static long time = System.currentTimeMillis();
	private static long lastAdd = 0;
	
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
    	currentState = controller.consumeInput(player, currentState);
    	worldObjects.pullObjectsDown(burnFactor, app.getHeight());
    	
    	if((System.currentTimeMillis() - time) > 3000 ) {
    		time = System.currentTimeMillis();
    		worldObjects.wObjects.add(new WorldObject(20, 0));
    		worldObjects.wObjects.add(new WorldObject(620, 0));
    	}
    	
    	// Check for exit status 
    	// @TODO we may not want to do this check each update loop, and just pass the app context into the
    	// Input Controller, so we can kill the app. Increase of coupling though.
    	if(currentState.equals(GameState.END)) {
    		app.exit();
    	}
    	
    }

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called after update.
    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        renderPlayer(g);
        renderObjects(g);
        g.drawString("BurnFactor: "+burnFactor, 0, 40);
        g.drawString("WorldObjectCount: "+worldObjects.wObjects.size(), 0, 80);
    }
    
    private void renderPlayer(Graphics g) throws SlickException {
    	g.drawImage(new Image(player.uiPath, false, 0), player.x, player.y);
    }
    
    private void renderObjects(Graphics g) throws SlickException {
    	for(WorldObject worldObject : worldObjects.wObjects) {
    		g.drawImage(new Image(worldObject.uiPath, false, 0), worldObject.x, worldObject.y);
    	}
    }
    
    @Override
    public void keyPressed(int x, char b) {
    	burnFactor = controller.boostControls(burnFactor);
    }
    
    public static void main(String[] args) {
        try {
        	
        	// Initialize game state
            app = new AppGameContainer(new SimpleTest());
        	player = new MockPlayer();
        	controller = new PlayerInputController( app.getWidth(), app.getHeight());
        	currentState =  GameState.IN_FLIGHT;
        	worldObjects = new WorldObjects();
        	
        	// Start game loop
            app.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}