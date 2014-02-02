import org.lwjgl.input.Keyboard;



public class PlayerInputController {
	
	public int screenWidth;
	public int screenHeight;
	
	public PlayerInputController(int screenWidth, int screenHeight) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}
	
	public GameState consumeInput(MockPlayer player, GameState currentState) {
		// Check for general keystrokes first
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			currentState = GameState.END;
			return currentState;
		}
		
		switch(currentState) {
			case IN_FLIGHT: 
				consumeInFlightInput(player);
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
	
	private void consumeInFlightInput(MockPlayer player) {
		
        if(Keyboard.isKeyDown(Keyboard.KEY_S) && player.y+player.height < screenHeight) {
        	player.y += 1;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && player.y >= 0 ) {
        	player.y -= 1;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_A) && player.x >= 0) {
        	player.x -= 1;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D) && player.x+player.width < screenWidth) {
        	player.x += 1;
        }
	}
	
	private void consumeInMenuInput() {
		
	}
}