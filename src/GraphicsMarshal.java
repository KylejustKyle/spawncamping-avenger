import java.util.Hashtable;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import playerShip.ShipVector;


public class GraphicsMarshal {
	public Image playerShipIdle;
	public Image playerShipLeanLeft;
	public Image playerShipLeanRight;
	
	public Image trackingWall;
	public Image playerBasicProjectile;
	
	public Animation jetAfterburner;
	
	private Hashtable<ShipVector, Image> playerShip;
	
	public GraphicsMarshal () {
		playerShip = new  Hashtable<ShipVector, Image>();
		
		try {
			playerShipIdle = new Image("resources/Jet_A.png");
			playerShipLeanLeft = new Image("resources/Jet_Lean_Left.png");
			playerShipLeanRight = new Image("resources/Jet_Lean_Right.png");
			
			trackingWall = new Image("resources/VerticalLine.png");
			playerBasicProjectile = new Image("resources/Projectile_Small.png");

	    	Image[] aferburnerAnim = {new Image("resources/Afterburner_1.png"), new Image("resources/Afterburner_2.png")};
	    	jetAfterburner = new Animation(aferburnerAnim, 200);
			
			playerShip.put(ShipVector.LEFT, playerShipLeanLeft);
			playerShip.put(ShipVector.RIGHT, playerShipLeanRight);
			playerShip.put(ShipVector.CENTER, playerShipIdle);
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initializePlayerAssets() {
		
	}
	
	public Image getPlayerShipGraphic(ShipVector vector) {
		return playerShip.get(vector);
	}
	
	public Image getTrackingWallGraphic() {
		return trackingWall;
	}
	
	public Image getPlayerProjectile() {
		return playerBasicProjectile;
	}
	
	public Animation getPlayerAfterburner() {
		return jetAfterburner;
	}
}
