import org.lwjgl.input.Keyboard;
import org.newdawn.slick.geom.Point;
import org.prototype.player.MockPlayer;
import org.prototype.player.ShipVector;
import org.prototype.projectiles.VerticalProjectile;
import org.prototype.projectiles.WorldProjectiles;
import org.prototype.utility.IntervalTimer;



public class PlayerInputController {
	
	public int screenWidth;
	public int screenHeight;
	private static IntervalTimer inputTimer;
	
	public PlayerInputController(int screenWidth, int screenHeight) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		inputTimer = new IntervalTimer(100);
	}
	
	public GameState consumeInput(MockPlayer player, GameState currentState, int delta, WorldProjectiles wProjectiles) {
		// Check for general keystrokes first
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			currentState = GameState.END;
			return currentState;
		}
		
		switch(currentState) {
			case IN_FLIGHT: 
				consumeInFlightInput(player, delta, wProjectiles);
			case MENU:
				consumeInMenuInput();
		}
		
		return currentState;
	}
	
	public int boostControls(int burnFactor) {
		if(Keyboard.isKeyDown(Keyboard.KEY_E) && burnFactor < 3) {
			burnFactor += 1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_Q) && burnFactor > 1) {
			burnFactor -= 1;
		}
		
		return burnFactor;
	}
	
	private void consumeInFlightInput(MockPlayer player, int delta, WorldProjectiles wProjectiles) {
		//We don't want to run this consuming flight control logic if the player is dead.
		if(!player.isAlive) {
			return;
		}
		
		// @TODO this is used in the burner trail, remove this if we refactor this code to no longer contain that logic.
		int currentX = player.x;
		int currentY = player.y;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			//Projectile factory should be used here instead.
			if(inputTimer.isInterval()) {
				wProjectiles.wProjectiles.add(new VerticalProjectile(1, 1, player.x+player.middle, player.y, 10, 10));
				inputTimer.rootTime = System.currentTimeMillis();
			}
		}
		
        if(Keyboard.isKeyDown(Keyboard.KEY_S) && player.y+player.height < screenHeight) {
        	player.y += delta;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && player.y >= 0 ) {
        	player.y -= delta;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_A) && player.x >= 0) {
        	player.x -= delta;
        	player.vector = ShipVector.LEFT;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D) && player.x+player.width < screenWidth) {
        	player.x += delta;
        	player.vector = ShipVector.RIGHT;
        }
        
        if(!Keyboard.isKeyDown(Keyboard.KEY_D) && !Keyboard.isKeyDown(Keyboard.KEY_A)) {
        	player.vector = ShipVector.CENTER;
        }
        
        player.boundingBox.setX(player.x);
        player.boundingBox.setY(player.y);
        
        //@TODO This should not go in here as it's not controller related. This should be a call on the player object
        if(currentX != player.x || currentY != player.y) {
        	// @TODO Introduce some variability in the points so it looks like nice dynamic noise,
        	// not just a straight line
        	player.addBurnerTrail(new Point(player.x+15 , player.y + player.height-20));
        	player.addBurnerTrail(new Point(player.x + (player.width-18), player.y + player.height-20));
        } else {
        	if(!player.burnerTrail.isEmpty()) {
        		player.burnerTrail.removeLast();
        		
        	}
        }
	}
	
	private void consumeInMenuInput() {
		
	}
}