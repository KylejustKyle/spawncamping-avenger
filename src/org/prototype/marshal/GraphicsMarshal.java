package org.prototype.marshal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.prototype.player.AfterburnerType;
import org.prototype.player.ShipVector;


public class GraphicsMarshal {
	public Image playerShipIdle;
	public Image playerShipLeanLeft;
	public Image playerShipLeanRight;
	
	public Image trackingWall;
	public Image testCollidable;
	public Image playerBasicProjectile;
	public Image testEnemy;
	
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
			
			trackingWall = new Image("resources/Edge.png");
			testCollidable = new Image("resources/Collidable.png");
			testEnemy = new Image("resources/Test2.png");
			playerBasicProjectile = new Image("resources/Projectile_Small.png");

	    	Image[] aferburnerAnimT1 = {new Image("resources/Afterburner_1.png"), new Image("resources/Afterburner_2.png")};
	    	jetAfterburnerTier1 = new Animation(aferburnerAnimT1, 150);
	    	Image[] aferburnerAnimT2 = {new Image("resources/Afterburner_1_T2.png"), new Image("resources/Afterburner_2_T2.png")};
	    	jetAfterburnerTier2 = new Animation(aferburnerAnimT2, 150);
	    	Image[] aferburnerAnimT3 = {new Image("resources/Afterburner_1_T3.png"), new Image("resources/Afterburner_2_T3.png")};
	    	jetAfterburnerTier3 = new Animation(aferburnerAnimT3, 150);
			
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
			Image small = new Image("resources/Explosion_S.png");
			Image smallR = new Image("resources/Explosion_S.png");
			smallR.rotate(45);
//			Image med = new Image("resources/Explosion_M.png");
//			Image medR = new Image("resources/Explosion_M.png");
//			Image large = new Image("resources/Explosion_L.png");
//			Image largeR = new Image("resources/Explosion_L.png");
//			Image xLarge = new Image("resources/Explosion_XL.png");
//			Image xLargeR = new Image("resources/Explosion_XL.png");
//			
//			smallR.rotate(45);
//			medR.rotate(45);
//			largeR.rotate(45);
//			xLargeR.rotate(45);
			
    	Image[] explosionAnim = {
    			small,
    			smallR,
    			small,
    			smallR};
    	
    		anim = new Animation(explosionAnim, 70);
		} catch(Exception e) {
			
		}
		anim.setLooping(false);
		return anim;
	}
	
	public Animation createMediumExplosionAssets() {
		Animation anim = null;
		try{
			Image med = new Image("resources/Explosion_M.png");
			Image medR = new Image("resources/Explosion_M.png");
			medR.rotate(45);
//			Image med = new Image("resources/Explosion_M.png");
//			Image medR = new Image("resources/Explosion_M.png");
//			Image large = new Image("resources/Explosion_L.png");
//			Image largeR = new Image("resources/Explosion_L.png");
//			Image xLarge = new Image("resources/Explosion_XL.png");
//			Image xLargeR = new Image("resources/Explosion_XL.png");
//			
//			smallR.rotate(45);
//			medR.rotate(45);
//			largeR.rotate(45);
//			xLargeR.rotate(45);
			
    	Image[] explosionAnim = {
    			med,
    			medR,
    			med,
    			medR};
    	
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
	 * @TODO this is ripe for being extracted into it's own class.
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

	public Image getTestEnemyGraphic() {
		return testEnemy;
	}
}
