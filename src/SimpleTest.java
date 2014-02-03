import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Projectiles.Projectile;

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
	private static WorldProjectiles worldProjectiles = null;
	private static long time = System.currentTimeMillis();
	private static long lastAdd = 0;
	private static boolean runAnimTest = false;
	
	private static Animation jetIdle;
    public SimpleTest() {
        super("SimpleTest");
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {
    	Image[] idleAnim = {new Image("resources/AnimTest1.png"), new Image("resources/AnimTest2.png")};
    	jetIdle = new Animation(idleAnim, 200);
    }

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called first in the game loop.
    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    	currentState = controller.consumeInput(player, currentState, worldProjectiles);
    	worldObjects.pullObjectsDown(burnFactor, app.getHeight());
    	worldProjectiles.updateProjectiles();
    	
    	if((System.currentTimeMillis() - time) > 1000 ) {
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
        renderProjectiles(g);
        renderDebugMenu(g);
        
        if(runAnimTest) {
        	jetIdle.draw(100,100);
        }

    }
    
    /**
     * @TODO
     * For ease at the moment we will place listener logic for keys that have a atomic 1 press logic attached.
     * We care about fidelity for these controls & currently the playerInputController doesn't handle that well.
     */
    @Override
    public void keyPressed(int x, char b) {
    	burnFactor = controller.boostControls(burnFactor);
    	controller.fireControls(player, worldProjectiles);
    	
    	if(b == 'y') {
    		runAnimTest= true;
    	}
    }
    
    public static void main(String[] args) {
        try {
        	
        	// Initialize game state
            app = new AppGameContainer(new SimpleTest());
            app.setTitle("Divergent Pancakes v0.0.1");
        	player = new MockPlayer(app.getWidth()/2, app.getHeight()/2);
        	controller = new PlayerInputController( app.getWidth(), app.getHeight());
        	currentState =  GameState.IN_FLIGHT;
        	worldObjects = new WorldObjects();
        	worldProjectiles = new WorldProjectiles();
        	
        	// Start game l oop
            app.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    private void renderPlayer(Graphics g) throws SlickException {
    	g.drawImage(new Image(player.uiPath, false, 0), player.x, player.y);

    }
    
    private void renderObjects(Graphics g) throws SlickException {
    	for(WorldObject worldObject : worldObjects.wObjects) {
    		g.drawImage(new Image(worldObject.uiPath, false, 0), worldObject.x, worldObject.y);
    	}
    }
    
    private void renderProjectiles(Graphics g) throws SlickException {
    	for(Projectile projectile : worldProjectiles.wProjectiles) {
    		g.drawImage(new Image(projectile.uiPath, false, 0), projectile.x, projectile.y);
    	}
    }
    
    private void renderDebugMenu(Graphics g) {
        g.drawString("BurnFactor: "+burnFactor, 0, 40);
        g.drawString("WorldObjectCount: "+worldObjects.wObjects.size(), 0, 55);
        g.drawString("WorldProjectileCount: "+worldProjectiles.wProjectiles.size(), 0, 70);
    }
}