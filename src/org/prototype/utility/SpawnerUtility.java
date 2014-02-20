package org.prototype.utility;

import java.util.Random;

import org.newdawn.slick.geom.Point;
import org.prototype.globals.GlobalConfig;
import org.prototype.world.entities.CollidableObject;
import org.prototype.world.entities.EnemyObject;

public class SpawnerUtility {

	public static Point generateConstrainedPoint(int minVertical, int maxVertical, int minHorizontal, int maxHorizontal) {
	    Random randomGen = new Random();
		float newVerticalPoint = randomGen.nextInt(maxVertical) + minVertical;
		float newHorizontalPoint = randomGen.nextInt(maxHorizontal) + minHorizontal;
		
		Point returnPoint = new Point(newHorizontalPoint, newVerticalPoint);
		return returnPoint; 
	}
	
	public static EnemyObject generateHorizontalEnemy() {
		EnemyObject eObject = new EnemyObject(SpawnerUtility.generateConstrainedPoint(0, 200, 0, GlobalConfig.GAME_WIDTH),
				50,
				50,
				1,
				0,
				1,
				0);
		return eObject;
	}
	
	public static CollidableObject generateVerticalCollidable() {
    	CollidableObject cObject = new CollidableObject(SpawnerUtility.generateConstrainedPoint(0, 1, 0, GlobalConfig.GAME_WIDTH),
			     50,
			     50, 
			     0, 
			     1,
			     0, 
			     1);
		return cObject;
	}

}
