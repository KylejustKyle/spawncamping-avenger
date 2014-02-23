package org.prototype.events;

import java.util.ArrayList;

import org.newdawn.slick.geom.Point;
import org.prototype.world.entities.CollidableObject;
import org.prototype.world.entities.EnemyObject;

public class Event {
	public float eventTiming;
	public Point location;
	public ArrayList<CollidableObject> collidableObjects;
	public ArrayList<EnemyObject> enemyObjects;
	
	public Event(float f) {
		eventTiming = f;
		collidableObjects = new ArrayList<CollidableObject>();
		enemyObjects = new ArrayList<EnemyObject>();
	}
	
	public void consumeEvent() {
	}
	
	public void addEnemy(EnemyObject enemy) {
		enemyObjects.add(enemy);
	}
	
	public void addCollidable(CollidableObject collidable) {
		collidableObjects.add(collidable);
	}
}
