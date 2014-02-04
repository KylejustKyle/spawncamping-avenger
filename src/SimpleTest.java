import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import playerShip.MockPlayer;
import projectiles.Projectile;
import utility.IntervalTimer;
import worldEntities.CollidableObject;
import worldEntities.CollidableObjects;
import worldEntities.WorldObject;
import worldEntities.WorldObjects;

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

	private static boolean isDebugMode = false;
	private static long distanceTravelled = 0;
	
	/*
	 * Marshal Classes
	 */
	private static GraphicsMarshal gMarshal;
	private static CollisionMarshal cMarshal;
	
	/*
	 * World Containers
	 */
	private static WorldObjects worldObjects = null;
	private static CollidableObjects collidableObjects = null;
	private static WorldProjectiles worldProjectiles = null;
	
	private static IntervalTimer spawnTimer;
	private static IntervalTimer distanceTimer;
	
    public SimpleTest() {
        super("SimpleTest");
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {
    	
    	spawnTimer = new IntervalTimer(System.currentTimeMillis(), 1000);
    	distanceTimer = new IntervalTimer(System.currentTimeMillis(), 1000);
    	
    	app.getInput().disableKeyRepeat();
    	gMarshal = new GraphicsMarshal();
    	cMarshal = new CollisionMarshal(collidableObjects, player);
    }

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called first in the game loop.
    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    	
    	currentState = controller.consumeInput(player, currentState, delta);
    	worldObjects.updateObjects(burnFactor, app.getHeight());
    	collidableObjects.updateObjects();
    	worldProjectiles.updateProjectiles();
    	
    	cMarshal.runCollision();
    	
    	// @TODO we want to move this logic out into a different class, or a delegate like structure
    	if(spawnTimer.isInterval()) {
    		spawnTimer.rootTime = System.currentTimeMillis();
    		worldObjects.wObjects.add(new WorldObject(20, 0));
    		worldObjects.wObjects.add(new WorldObject(620, 0));
    	}
    	
    	if(distanceTimer.isInterval()) {
    		distanceTravelled += ((System.currentTimeMillis() - distanceTimer.rootTime)/1000) * burnFactor;
    		distanceTimer.rootTime = System.currentTimeMillis();
    	}
    	
    	// Player ship died, run death routine
    	if(player.shouldExplode) {
    		gMarshal.queueAnimation(gMarshal.createExplosionAssets(), new Point(player.x, player.y));
    		player.shouldExplode = false;
    		player.boundingBox.setX(320);
    		player.boundingBox.setY(240);
    		player.x = 320;
    		player.y = 240;
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
    public void render(GameContainer container, Graphics g) throws SlickException {
        renderPlayer(g);
        renderGraphicsMarshalQueues();
        renderObjects(g);
        renderProjectiles(g);
        renderDebugMenu(g);
        
        if(isDebugMode) {
        	renderBoundingBoxes(g);
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
    	
    	if(b == 'h') {
    		isDebugMode= (isDebugMode ? false : true );
    	}
    	
    	if(b == 'r') {
    		player.isAlive = true;
    		player.x = 320;
    		player.y = 240;
    	}
    }
    
    public static void main(String[] args) {
        try {
        	
        	// Initialize game state
            app 				= new AppGameContainer(new SimpleTest());
        	player 				= new MockPlayer(app.getWidth()/2, app.getHeight()/2);
        	controller 			= new PlayerInputController( app.getWidth(), app.getHeight());
        	currentState 		=  GameState.IN_FLIGHT;
        	worldObjects 		= new WorldObjects();
        	worldProjectiles 	= new WorldProjectiles();
        	collidableObjects 	= new CollidableObjects();
        	collidableObjects.cObjects.add(new CollidableObject(300, 100, 50, 50));
        	
            app.setTitle("Divergent Pancakes v0.0.1");
        	
        	// Start game loop
            app.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    private void renderPlayer(Graphics g) throws SlickException {
    	if(player.isAlive) {
    		gMarshal.getPlayerShipGraphic(player.vector).draw(player.x, player.y);
    		gMarshal.getPlayerAfterburner(burnFactor).draw(player.x, player.y);
    	} else {
    		//Opts for death
    	}
    }
    
    private void renderGraphicsMarshalQueues() throws SlickException {
    	gMarshal.drawAnimationQueue();
    }
    
    private void renderObjects(Graphics g) throws SlickException {
    	for(WorldObject worldObject : worldObjects.wObjects) {
    		gMarshal.getTrackingWallGraphic().draw(worldObject.x, worldObject.y);
    	}
    	
    	for(CollidableObject collidableObject : collidableObjects.cObjects) {
    		gMarshal.getTestCollidableGraphic().draw(collidableObject.x, collidableObject.y);
    	}
    }
    
    private void renderProjectiles(Graphics g) throws SlickException {
    	for(Projectile projectile : worldProjectiles.wProjectiles) {
    		gMarshal.getPlayerProjectile().draw(projectile.x, projectile.y);
    	}
    }
    
    private void renderDebugMenu(Graphics g) {
        g.drawString("BurnFactor: "+burnFactor, 0, 40);
        g.drawString("WorldObjectCount: "+worldObjects.wObjects.size(), 0, 55);
        g.drawString("WorldProjectileCount: "+worldProjectiles.wProjectiles.size(), 0, 70);
        g.drawString("CollidableObjectCount: "+collidableObjects.cObjects.size(), 0, 85);
        g.drawString("DistanceTravelled: "+distanceTravelled, 0, 100);
        g.drawString("DebugMode (h)", 0, 115);
        g.drawString("Reset Player (r)", 0, 130);
    }
    
    private void renderBoundingBoxes(Graphics g) {
    	g.draw(player.boundingBox);
    	
    	for(CollidableObject collidableObject : collidableObjects.cObjects) {
    		g.draw(collidableObject.boundingBox);
    	}
    }
}