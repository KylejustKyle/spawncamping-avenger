import java.util.Hashtable;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import playerShip.AfterburnerType;
import playerShip.ShipVector;


public class GraphicsMarshal {
	public Image playerShipIdle;
	public Image playerShipLeanLeft;
	public Image playerShipLeanRight;
	
	public Image trackingWall;
	public Image testCollidable;
	public Image playerBasicProjectile;
	
	public Animation jetAfterburnerTier1;
	public Animation jetAfterburnerTier2;
	public Animation jetAfterburnerTier3;
	
	private Hashtable<ShipVector, Image> playerShip;
	private Hashtable<AfterburnerType, Animation> afterburner;
	
	public GraphicsMarshal () {
		playerShip = new  Hashtable<ShipVector, Image>();
		afterburner = new Hashtable<AfterburnerType, Animation>();
		
		try {
			playerShipIdle = new Image("resources/Jet_A.png");
			playerShipLeanLeft = new Image("resources/Jet_Lean_Left.png");
			playerShipLeanRight = new Image("resources/Jet_Lean_Right.png");
			
			trackingWall = new Image("resources/VerticalLine.png");
			testCollidable = new Image("resources/Test.png");
			playerBasicProjectile = new Image("resources/Projectile_Small.png");

	    	Image[] aferburnerAnimT1 = {new Image("resources/Afterburner_1.png"), new Image("resources/Afterburner_2.png")};
	    	jetAfterburnerTier1 = new Animation(aferburnerAnimT1, 200);
	    	Image[] aferburnerAnimT2 = {new Image("resources/Afterburner_1_T2.png"), new Image("resources/Afterburner_2_T2.png")};
	    	jetAfterburnerTier2 = new Animation(aferburnerAnimT2, 200);
	    	Image[] aferburnerAnimT3 = {new Image("resources/Afterburner_1_T3.png"), new Image("resources/Afterburner_2_T3.png")};
	    	jetAfterburnerTier3 = new Animation(aferburnerAnimT3, 200);
			
			playerShip.put(ShipVector.LEFT, playerShipLeanLeft);
			playerShip.put(ShipVector.RIGHT, playerShipLeanRight);
			playerShip.put(ShipVector.CENTER, playerShipIdle);
			
			afterburner.put(AfterburnerType.TIER_1, jetAfterburnerTier1);
			afterburner.put(AfterburnerType.TIER_2, jetAfterburnerTier2);
			afterburner.put(AfterburnerType.TIER_3, jetAfterburnerTier3);
			
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
	
	public Animation getPlayerAfterburner(int burnFactor) {
		return afterburner.get(AfterburnerType.values()[burnFactor]);
	}
	
	public Image getTestCollidableGraphic() {
		return testCollidable;
	}
}
