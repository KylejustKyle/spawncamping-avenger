package org.prototype.world.entities;

import org.newdawn.slick.geom.Point;
import org.prototype.math.FunctionType;

public class EnemyFactory {
	public static EnemyObject createEnemy(Point startingPoint, float width, float height, float xAxisVector, float yAxisVector, float velocity, FunctionType funcType) {
		return new EnemyObject(startingPoint, width, height, xAxisVector, yAxisVector, velocity, funcType);
	}
}
