package org.prototype.utility;

import java.util.Random;

import org.newdawn.slick.geom.Point;

public class SpawnerUtility {

	public static Point generateConstrainedPoint(int minVertical, int maxVertical, int minHorizontal, int maxHorizontal) {
	     Random randomGen = new Random();
		float newVerticalPoint = randomGen.nextInt(maxVertical) + minVertical;
		float newHorizontalPoint = randomGen.nextInt(maxHorizontal) + minHorizontal;
		
		Point returnPoint = new Point(newHorizontalPoint, newVerticalPoint);
		return returnPoint; 
	}

}
