package org.prototype.marshal;

import org.prototype.events.Event;
import org.prototype.events.EventQueue;
import org.prototype.world.entities.CollidableObjects;
import org.prototype.world.entities.EnemyObjects;

public class EventMarshal {
	public EventQueue eventQueue;
	public CollidableObjects collidables;
	public EnemyObjects enemies;
	
	public EventMarshal(CollidableObjects newCObjects, EnemyObjects newEObjects) {
		eventQueue = new EventQueue();
		collidables = newCObjects;
		enemies = newEObjects;
	}
	
	public void updateEvents(double currentTime) {
		Event e = eventQueue.getNextEvent(currentTime);
		
		if(e != null) {
			collidables.cObjects.addAll(e.collidableObjects);
			enemies.eObjects.addAll(e.enemyObjects);
		}
	}
	
	public void pushEvent(Event newEvent) {
		eventQueue.events.add(newEvent);
		
		// @ TODO we need to sort events by the time to get ascending.
	}
}
