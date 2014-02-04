import org.lwjgl.input.Keyboard;

import playerShip.MockPlayer;
import playerShip.ShipVector;
import projectiles.VerticalProjectile;



public class PlayerInputController {
	
	public int screenWidth;
	public int screenHeight;
	
	public PlayerInputController(int screenWidth, int screenHeight) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}
	
	public GameState consumeInput(MockPlayer player, GameState currentState, int delta) {
		// Check for general keystrokes first
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			currentState = GameState.END;
			return currentState;
		}
		
		switch(currentState) {
			case IN_FLIGHT: 
				consumeInFlightInput(player, delta);
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
	
	public void fireControls(MockPlayer player, WorldProjectiles wProjectiles) {
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			//Projectile factory should be used here instead.
			wProjectiles.wProjectiles.add(new VerticalProjectile(1, 1, player.x+player.middle, player.y));
		}
	}
	
	private void consumeInFlightInput(MockPlayer player, int delta) {
		//We don't want to run this consuming flight control logic if the player is dead.
		if(!player.isAlive) {
			return;
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
	}
	
	private void consumeInMenuInput() {
		
	}
}