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
	public WorldProjectiles playerProjectiles;
	public WorldProjectiles enemyProjectiles;
	public EnemyObjects enemyObjects;
	
	public CollisionMarshal(CollidableObjects newCollidableObjects, WorldProjectiles newEnemyProjectiles, EnemyObjects newEnemyObjects, WorldProjectiles newWorldProjectiles, MockPlayer newPlayer) {
		player 				= newPlayer;
		collidableObjects 	= newCollidableObjects;
		enemyObjects 		= newEnemyObjects;
		playerProjectiles 	= newWorldProjectiles;
		enemyProjectiles 	= newEnemyProjectiles;
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
			
			for(Projectile projectile : enemyProjectiles.wProjectiles) {
				if(projectile.boundingBox.intersects(player.boundingBox)) {
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
		ArrayList<EnemyObject> enemyRemovalSet = new ArrayList<EnemyObject>();
		ArrayList<Projectile> projectileRemovalSet = new ArrayList<Projectile>();
		
		for(Projectile projectile : playerProjectiles.wProjectiles) {
			for(EnemyObject enemyObject : enemyObjects.eObjects) {
				if(projectile.boundingBox.intersects(enemyObject.boundingBox)) {
					enemyRemovalSet.add(enemyObject);
					projectileRemovalSet.add(projectile);
					enemyObject.isAlive = false;
					gMarshal.queueAnimation(gMarshal.createExplosionAssets(), new Point(enemyObject.x, enemyObject.y));
				}
			}
		}
		
		playerProjectiles.wProjectiles.removeAll(projectileRemovalSet);
		enemyObjects.eObjects.removeAll(enemyRemovalSet);
	}
}