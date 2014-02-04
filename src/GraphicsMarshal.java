import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

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
	public Animation explosion;
	
	private Hashtable<ShipVector, Image> playerShip;
	private Hashtable<AfterburnerType, Animation> afterburner;
	
	private Stack<Animation> queuedAnimations;
	private Stack<Point> queuedAnimationCoordinates;
	private Stack<Image> queuedImages;
	private Stack<Point> queuedImageCoordinates;
	
	public GraphicsMarshal () {
		playerShip = new  Hashtable<ShipVector, Image>();
		afterburner = new Hashtable<AfterburnerType, Animation>();
		queuedAnimations = new Stack<Animation>();
		queuedImages = new Stack<Image>();
		queuedAnimationCoordinates = new Stack<Point>();
		queuedImageCoordinates = new Stack<Point>();
		
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
	
	public Animation createExplosionAssets() {
		Animation anim = null;
		try{
    	Image[] explosionAnim = {	new Image("resources/Explosion_S.png"), 
				new Image("resources/Explosion_M.png"),
				new Image("resources/Explosion_L.png"),
				new Image("resources/Explosion_XL.png")};
    		anim = new Animation(explosionAnim, 90);
		} catch(Exception e) {
			
		}
		anim.setLooping(false);
		return anim;
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
	
	/*
	 ********************************************************************************************************
	 * Animation & Image Queuer
	 ********************************************************************************************************
	*/
	public List<Animation> getQueuedAnimations() {
		return queuedAnimations;
	}
	
	public List<Point> getQueuedAnimationCoordinates() {
		return queuedAnimationCoordinates;
	}
	
	public List<Image> getQueuedImages() {
		return queuedImages;
	}
	
	public void drawAnimationQueue() {
		List<Animation> removalSetAnim = new ArrayList<Animation>();
		List<Point> removalSetPoint = new ArrayList<Point>();
		
		for(int i=0; i < queuedAnimations.size(); i++) {
			Animation anim = queuedAnimations.get(i);
			Point point = queuedAnimationCoordinates.get(i);
			anim.draw(point.getX(), point.getY());
			
			if(anim.isStopped()) {
				removalSetAnim.add(anim);
				removalSetPoint.add(point);
			}
		}
		
		queuedAnimations.removeAll(removalSetAnim);
		queuedAnimationCoordinates.removeAll(removalSetPoint);
		
	}
	
	public void queueAnimation(Animation newAnimation, Point newPoint) {
		queuedAnimations.push(newAnimation);
		queuedAnimationCoordinates.push(newPoint);
	}
	
	public void queueImage(Image newImage, Point newPoint) {
		queuedImages.push(newImage);
		queuedImageCoordinates.push(newPoint);
	}
	
	public void clearQueues() {
		queuedAnimations.clear();
		queuedImages.clear();
		queuedAnimationCoordinates.clear();
		queuedImageCoordinates.clear();
	}
}
