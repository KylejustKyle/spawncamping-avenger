import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.prototype.globals.GlobalConfig;
import org.prototype.marshal.CollisionMarshal;
import org.prototype.marshal.GraphicsMarshal;
import org.prototype.player.MockPlayer;
import org.prototype.projectiles.Projectile;
import org.prototype.projectiles.WorldProjectiles;
import org.prototype.utility.IntervalTimer;
import org.prototype.utility.SpawnerUtility;
import org.prototype.world.entities.Background;
import org.prototype.world.entities.CollidableObject;
import org.prototype.world.entities.CollidableObjects;
import org.prototype.world.entities.EnemyObject;
import org.prototype.world.entities.EnemyObjects;
import org.prototype.world.entities.WorldObject;
import org.prototype.world.entities.WorldObjects;
import org.prototype.world.streamer.WorldStreamer;

public class SimpleTest extends BasicGame {

	/**
	 * @author Stanley Plisskin
	 */
	private static AppGameContainer app;
	private static MockPlayer player;
	private static PlayerInputController controller;
	private static GameState currentState;
	private static int burnFactor = 1;

	private static boolean isDebugMode = false;
	private static double distanceTravelled = 0;
	
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
	private static EnemyObjects enemyObjects = null;
	private static WorldStreamer ws = null;
	
	private static IntervalTimer spawnTimer;
	private static IntervalTimer distanceTimer;
	private static IntervalTimer enemyTimer;
	private static IntervalTimer collidableTimer;
	
    public SimpleTest() {
        super("SimpleTest");
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {
    	
    	spawnTimer = new IntervalTimer(1000);
    	distanceTimer = new IntervalTimer(1000);
    	enemyTimer = new IntervalTimer(5000);
    	collidableTimer = new IntervalTimer(1400);
    	
    	app.getInput().disableKeyRepeat();
    	gMarshal = new GraphicsMarshal();
    	cMarshal = new CollisionMarshal(collidableObjects, enemyObjects, worldProjectiles, player);
		worldObjects.wObjects.add(new WorldObject(0, -GlobalConfig.GAME_HEIGHT));
		worldObjects.wObjects.add(new WorldObject(GlobalConfig.GAME_WIDTH-20, -GlobalConfig.GAME_HEIGHT));
		
		//Music openingMenuMusic = new Music("resources/23_-_bloody_battle.ogg");
		//openingMenuMusic.loop();
    }

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called first in the game loop.
    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    	
    	currentState = controller.consumeInput(player, currentState, delta, worldProjectiles);
    	worldObjects.updateObjects(burnFactor, app.getHeight());
    	collidableObjects.updateObjects();
    	enemyObjects.updateObects();
    	worldProjectiles.updateProjectiles();
    	ws.updateBackgrounds(burnFactor);
    	
    	cMarshal.runCollision(gMarshal);
    	
    	distanceTravelled += (((System.currentTimeMillis()-distanceTimer.rootTime)/1000.0)*burnFactor);
    	distanceTimer.rootTime = System.currentTimeMillis();
    	
    	if(enemyTimer.isInterval()) {
    		enemyObjects.eObjects.add(
    				new EnemyObject(SpawnerUtility.generateConstrainedPoint(0, 200, 0, GlobalConfig.GAME_WIDTH),
    								50,
    								50,
    								1,
    								0,
    								1,
    								0));
    		enemyTimer.rootTime = System.currentTimeMillis();
    	}
    	
    	//@TODO hack fix, address this
    	int speed = (burnFactor > 0 ? burnFactor : 1);
    	
    	if(collidableTimer.isInterval()) {
        	collidableObjects.cObjects.add(new CollidableObject(SpawnerUtility.generateConstrainedPoint(0, 1, 0, GlobalConfig.GAME_WIDTH),
				     50,
				     50, 
				     0, 
				     1,
				     0, 
				     1*speed));
        	collidableTimer.rootTime = System.currentTimeMillis();
    	}
    	
    	// Player ship died, run death routine
    	if(player.shouldExplode) {
    		gMarshal.queueAnimation(gMarshal.createMediumExplosionAssets(), new Point(player.x, player.y));
    		burnFactor = 0;
    		player.shouldExplode = false;
    		spawnTimer.stop();
    		distanceTimer.stop();
    		enemyTimer.stop();
    	}
    	
    	// Check for exit status 
    	// @TODO we may not want to do this check each update loop, and just pass the app context into the
    	// Input Controller, so we can kill the app. Increase of coupling though.
    	if(currentState.equals(GameState.END)) {
    		app.exit();
    	}
    }

    /**
     * @TODO
     * For ease at the moment we will place listener logic for keys that have a atomic 1 press logic attached.
     * We care about fidelity for these controls & currently the playerInputController doesn't handle that well.
     */
    @Override
    public void keyPressed(int x, char b) {
    	if(player.isAlive) {
        	burnFactor = controller.boostControls(burnFactor);
        	controller.fireControls(player, worldProjectiles);
    	}
    	
    	if(b == 'h') {
    		isDebugMode = (isDebugMode ? false : true );
    	}
    	
    	if(b == 'r') {
    		player.isAlive = true;
    		player.x = 320;
    		player.y = 240;
    		player.boundingBox.setX(320);
    		player.boundingBox.setY(240);
    		burnFactor = 1;
    		spawnTimer.start();
    		distanceTimer.start();
    		enemyTimer.start();
    	}
    }
    
    public static void main(String[] args) {
        try {
        	
        	// Initialize game state
            app 				= new AppGameContainer(new SimpleTest());
        	app.setDisplayMode(GlobalConfig.GAME_WIDTH, GlobalConfig.GAME_HEIGHT, false);
        	app.setTargetFrameRate(900);
        	
        	player 				= new MockPlayer(app.getWidth()/2, app.getHeight()/2);
        	controller 			= new PlayerInputController( app.getWidth(), app.getHeight());
        	currentState 		=  GameState.IN_FLIGHT;
        	worldObjects 		= new WorldObjects();
        	worldProjectiles 	= new WorldProjectiles();
        	enemyObjects 		= new EnemyObjects();
        	collidableObjects 	= new CollidableObjects();
        	ws 					= new WorldStreamer();
        	ws.backgrounds.add(new Background(0, -1));
        	
            app.setTitle("Divergent Pancakes v0.0.1");
        	
        	// Start game loop
            app.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    //**************************************************************************************************
    //**								Rendering Source
    //**************************************************************************************************
    
    
    // This is called on tick, in GameContainer.java updateAndRender
    // This is called after update.
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
    	renderBackground(g);
        renderPlayer(g);
        renderGraphicsMarshalQueues();
        renderObjects(g);
        renderProjectiles(g);
        renderDebugMenu(g);
        
        if(isDebugMode) {
        	renderBoundingBoxes(g);
        }
    }
    
    private void renderBackground(Graphics g) throws SlickException {
    	for(Background bg : ws.backgrounds) {
    		gMarshal.background.draw(bg.x, bg.y);
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
    	
    	for(EnemyObject enemyObject : enemyObjects.eObjects) {
    		gMarshal.getTestEnemyGraphic().draw(enemyObject.x, enemyObject.y);
    	}
    }
    
    private void renderProjectiles(Graphics g) throws SlickException {
    	for(Projectile projectile : worldProjectiles.wProjectiles) {
    		gMarshal.getPlayerProjectile().draw(projectile.x, projectile.y);
    	}
    }
    
    private void renderDebugMenu(Graphics g) {
        g.drawString("BurnFactor: "+burnFactor, 25, 40);
        g.drawString("WorldObjectCount: "+worldObjects.wObjects.size(), 25, 55);
        g.drawString("WorldProjectileCount: "+worldProjectiles.wProjectiles.size(), 25, 70);
        g.drawString("CollidableObjectCount: "+collidableObjects.cObjects.size(), 25, 85);
        g.drawString("EnemyObjectCount: "+enemyObjects.eObjects.size(), 25, 100);
        g.drawString("DistanceTravelled: "+distanceTravelled, 25, 115);
        g.drawString("DebugMode (h)", 25, 130);
        g.drawString("Reset Player (r)", 25, 145);
        g.drawString("BackgroundCount: "+ws.backgrounds.size(), 25, 160);
    }
    
    private void renderBoundingBoxes(Graphics g) {
    	g.draw(player.boundingBox);
    	
    	for(CollidableObject collidableObject : collidableObjects.cObjects) {
    		g.draw(collidableObject.boundingBox);
    	}
    	
    	for(EnemyObject enemyObject : enemyObjects.eObjects) {
    		g.draw(enemyObject.boundingBox);
    	}
    	
    	for(Projectile projectile : worldProjectiles.wProjectiles) {
    		g.draw(projectile.boundingBox);
    	}
    }
}