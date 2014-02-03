import java.util.Hashtable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import PlayerShip.ShipVector;


public class GraphicsMarshal {
	public Image playerShipIdle;
	public Image playerShipLeanLeft;
	public Image playerShipLeanRight;
	
	private Hashtable<ShipVector, Image> playerShip;
	
	public GraphicsMarshal () {
		playerShip = new  Hashtable<ShipVector, Image>();
		
		try {
			playerShipIdle = new Image("resources/Jet_A.png");
			playerShipLeanLeft = new Image("resources/JetLeanLeft.png");
			playerShipLeanRight = new Image("resources/JetLeanRight.png");
			
			playerShip.put(ShipVector.LEFT, playerShipLeanLeft);
			playerShip.put(ShipVector.RIGHT, playerShipLeanRight);
			playerShip.put(ShipVector.CENTER, playerShipIdle);
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Image getPlayerShipGraphic(ShipVector vector) {
		return playerShip.get(vector);
	}
}
