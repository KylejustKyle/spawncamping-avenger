package org.prototype.marshal;
import java.util.ArrayList;

import org.newdawn.slick.geom.Point;
import org.prototype.player.MockPlayer;
import org.prototype.projectiles.Projectile;
import org.prototype.projectiles.WorldProjectiles;
import org.prototype.world.entities.CollidableObject;
import org.prototype.world.entities.CollidableObjects;
import org.prototype.world.entities.EnemyObject;
import org.prototype.world.entities.EnemyObjects;


public class CollisionMarshal {
	public MockPlayer player;
	public CollidableObjects collidableObjects;
	public WorldProjectiles worldProjectiles;
	public EnemyObjects enemyObjects;
	
	public CollisionMarshal(CollidableObjects newCollidableObjects, EnemyObjects newEnemyObjects, WorldProjectiles newWorldProjectiles, MockPlayer newPlayer) {
		player 				= newPlayer;
		collidableObjects 	= newCollidableObjects;
		enemyObjects 		= newEnemyObjects;
		worldProjectiles 	= newWorldProjectiles;
	}
	
	public void runCollision(GraphicsMarshal gMarshal) {
		if(player.isAlive) {
			// Check to see if the player has collided with world hazards
			for(CollidableObject collidableObject : collidableObjects.cObjects) {
				if(collidableObject.boundingBox.intersects(player.boundingBox)) {
					player.isAlive = false;
					player.shouldExplode = true;
					break;
				}
			}
			
			// Check to see if the player has collided with enemy objects
			for(EnemyObject enemyObject : enemyObjects.eObjects) {
				if(enemyObject.boundingBox.intersects(player.boundingBox)) {
					player.isAlive = false;
					player.shouldExplode = true;
					break;
				}
			}
		}
		
		// @TODO this will definitely need an algorithm check
		ArrayList<EnemyObject> removalSet = new ArrayList<EnemyObject>();
		for(Projectile projectile : worldProjectiles.wProjectiles) {
			for(EnemyObject enemyObject : enemyObjects.eObjects) {
				if(projectile.boundingBox.intersects(enemyObject.boundingBox)) {
					removalSet.add(enemyObject);
					enemyObject.isAlive = false;
					gMarshal.queueAnimation(gMarshal.createExplosionAssets(), new Point(enemyObject.x, enemyObject.y));
				}
			}
		}
		
		enemyObjects.eObjects.removeAll(removalSet);
	}
}