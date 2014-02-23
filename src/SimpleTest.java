import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.prototype.events.Event;
import org.prototype.globals.GlobalConfig;
import org.prototype.marshal.CollisionMarshal;
import org.prototype.marshal.EventMarshal;
import org.prototype.marshal.GraphicsMarshal;
import org.prototype.player.MockPlayer;
import org.prototype.projectiles.Projectile;
import org.prototype.projectiles.WorldProjectiles;
import org.prototype.utility.IntervalTimer;
import org.prototype.utility.SpawnerUtility;
import org.prototype.world.entities.Background;
import org.prototype.world.entities.BackgroundObjects;
import org.prototype.world.entities.CollidableObject;
import org.prototype.world.entities.CollidableObjects;
import org.prototype.world.entities.EnemyObject;
import org.prototype.world.entities.EnemyObjects;
import org.prototype.world.entities.WorldObject;
import org.prototype.world.entities.WorldObjects;
import org.xml.sax.SAXException;

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
	private static EventMarshal	eMarshal;
	
	/*
	 * World Containers
	 */
	private static WorldObjects worldObjects = null;
	private static CollidableObjects collidableObjects = null;
	private static WorldProjectiles playerProjectiles = null;
	private static WorldProjectiles enemyProjectiles = null;
	private static EnemyObjects enemyObjects = null;
	private static BackgroundObjects backgroundObjects = null;
	
	private static IntervalTimer spawnTimer;
	private static IntervalTimer distanceTimer;
	private static IntervalTimer enemyTimer;
	
    public SimpleTest() {
        super("SimpleTest");
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {
    	
    	spawnTimer = new IntervalTimer(1000);
    	distanceTimer = new IntervalTimer(1000);
    	enemyTimer = new IntervalTimer(5000);
    	
    	app.getInput().disableKeyRepeat();
    	gMarshal = new GraphicsMarshal();
    	eMarshal = new EventMarshal(collidableObjects, enemyObjects);
    	cMarshal = new CollisionMarshal(collidableObjects, enemyProjectiles, enemyObjects, playerProjectiles, player);
		worldObjects.wObjects.add(new WorldObject(0, -GlobalConfig.GAME_HEIGHT));
		worldObjects.wObjects.add(new WorldObject(GlobalConfig.GAME_WIDTH-20, -GlobalConfig.GAME_HEIGHT));
		initializeTestEvents();
		
		try {
			LevelParser nParser = new LevelParser("resources/levels/test_level.xml");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Music openingMenuMusic = new Music("resources/23_-_bloody_battle.ogg");
//		openingMenuMusic.loop();
    }

    // This is called on tick, in GameContainer.java updateAndRender
    // This is called first in the game loop.
    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
    	currentState = controller.consumeInput(player, currentState, delta, playerProjectiles);
    	player.updateBlurTrail();
    	worldObjects.updateObjects(burnFactor, app.getHeight());
    	collidableObjects.updateObjects();
    	enemyObjects.updateObects(enemyProjectiles);
    	playerProjectiles.updateProjectiles();
    	enemyProjectiles.updateProjectiles();
    	backgroundObjects.updateBackgrounds(burnFactor);
    	eMarshal.updateEvents(distanceTravelled);
    	cMarshal.runCollision(gMarshal);
    	
    	distanceTravelled += (((System.currentTimeMillis()-distanceTimer.rootTime)/1000.0)*burnFactor);
    	distanceTimer.rootTime = System.currentTimeMillis();
    	
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
        	app.setTargetFrameRate(400);
        	
        	player 				= new MockPlayer(app.getWidth()/2, app.getHeight()/2);
        	controller 			= new PlayerInputController( app.getWidth(), app.getHeight());
        	currentState 		=  GameState.IN_FLIGHT;
        	worldObjects 		= new WorldObjects();
        	playerProjectiles 	= new WorldProjectiles();
        	enemyProjectiles 	= new WorldProjectiles();
        	enemyObjects 		= new EnemyObjects();
        	collidableObjects 	= new CollidableObjects();
        	backgroundObjects 	= new BackgroundObjects();
        	backgroundObjects.bObjects.add(new Background(0, -1));
        	
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

    	//renderBackground(g);
        renderPlayer(g);
        renderGraphicsMarshalQueues();
        renderObjects(g);
        renderProjectiles(g);
        renderDebugMenu(g);
        
        if(isDebugMode) {
            renderEnemyDebug(g);
        	renderBoundingBoxes(g);
        }
    }
    
    // @TODO huge refactor here to remove all thius rendering code into a rendering marhsal
    
    private void renderBackground(Graphics g) throws SlickException {
    	for(Background bg : backgroundObjects.bObjects) {
    		gMarshal.background.draw(bg.x, bg.y);
    	}
    }
    
    private void renderPlayer(Graphics g) throws SlickException {
    	if(player.isAlive) {
    		float alpha = 1f;
    		for(Point trailNode : player.burnerTrail) {
    			gMarshal.getBurnerTrail().setAlpha(alpha);
    			gMarshal.getBurnerTrail().draw(trailNode.getX(), trailNode.getY());
    			alpha = alpha - 0.01f;
    		}
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
    		float alpha = 1.0f;
    		
    		for(Point trailNode : enemyObject.burnerTrail) {
    			gMarshal.getBurnerTrail().setAlpha(alpha);
    			gMarshal.getBurnerTrail().draw(trailNode.getX(), trailNode.getY());
    			alpha = alpha - 0.01f;
    		}
    	}
    }
    
    private void renderProjectiles(Graphics g) throws SlickException {
    	for(Projectile projectile : playerProjectiles.wProjectiles) {
    		gMarshal.getPlayerProjectile().draw(projectile.x, projectile.y);
    	}
    	
    	for(Projectile projectile : enemyProjectiles.wProjectiles) {
    		gMarshal.getPlayerProjectile().draw(projectile.x, projectile.y);
    	}
    }
    
    private void renderEnemyDebug(Graphics g) {
    	for(EnemyObject enemy : enemyObjects.eObjects) {
    		g.drawString("TrailCollectionSize: "+enemy.burnerTrail.size(), enemy.x, enemy.y);
    	}
    }
    
    private void renderDebugMenu(Graphics g) {
        g.drawString("BurnFactor: "+burnFactor, 25, 40);
        g.drawString("WorldObjectCount: "+worldObjects.wObjects.size(), 25, 55);
        g.drawString("PlayerProjectileCount: "+playerProjectiles.wProjectiles.size(), 25, 70);
        g.drawString("EnemyProjectileCount: "+enemyProjectiles.wProjectiles.size(), 25, 85);
        g.drawString("CollidableObjectCount: "+collidableObjects.cObjects.size(), 25, 100);
        g.drawString("EnemyObjectCount: "+enemyObjects.eObjects.size(), 25, 115);
        g.drawString("DistanceTravelled: "+distanceTravelled, 25, 130);
        g.drawString("DebugMode (h)", 25, 145);
        g.drawString("Reset Player (r)", 25, 160);
        g.drawString("BackgroundCount: "+backgroundObjects.bObjects.size(), 25, 175);
        g.drawString("BurnerTrailCount: "+player.burnerTrail.size(), 25, 190);
    }
    
    private void renderBoundingBoxes(Graphics g) {
    	g.draw(player.boundingBox);
    	
    	for(CollidableObject collidableObject : collidableObjects.cObjects) {
    		g.draw(collidableObject.boundingBox);
    	}
    	
    	for(EnemyObject enemyObject : enemyObjects.eObjects) {
    		g.draw(enemyObject.boundingBox);
    	}
    	
    	for(Projectile projectile : playerProjectiles.wProjectiles) {
    		g.draw(projectile.boundingBox);
    	}
    	
    	for(Projectile projectile : enemyProjectiles.wProjectiles) {
    		g.draw(projectile.boundingBox);
    	}
    }
    
    private void initializeTestEvents() {
    	Event newEvent = new Event(11);
    	newEvent.addCollidable(SpawnerUtility.generateVerticalCollidable());
    	newEvent.addCollidable(SpawnerUtility.generateVerticalCollidable());
    	newEvent.addCollidable(SpawnerUtility.generateVerticalCollidable());
    	newEvent.addEnemy(SpawnerUtility.generateHorizontalEnemy());
    	newEvent.addEnemy(SpawnerUtility.generateHorizontalEnemy());
    	newEvent.addEnemy(SpawnerUtility.generateHorizontalEnemy());
    	
    	Event newEvent2 = new Event(20);
    	newEvent2.addCollidable(SpawnerUtility.generateVerticalCollidable());
    	newEvent2.addCollidable(SpawnerUtility.generateVerticalCollidable());
    	newEvent2.addCollidable(SpawnerUtility.generateVerticalCollidable());
    	newEvent2.addEnemy(SpawnerUtility.generateHorizontalEnemy());
    	newEvent2.addEnemy(SpawnerUtility.generateHorizontalEnemy());
    	newEvent2.addEnemy(SpawnerUtility.generateHorizontalEnemy());
    	
    	eMarshal.pushEvent(newEvent);
    	eMarshal.pushEvent(newEvent2);
    }
}