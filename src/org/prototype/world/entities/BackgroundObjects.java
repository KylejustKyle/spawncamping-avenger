package org.prototype.world.entities;

import java.util.ArrayList;
import java.util.List;

import org.prototype.utility.ObjectTrackerUtility;

public class BackgroundObjects {
	public List<Background> bObjects;
	
	public BackgroundObjects() {
		bObjects = new ArrayList<Background>();
	}
	
	public void updateBackgrounds(int burnFactor) {
		ArrayList<Background> removalSet = new ArrayList<Background>();
		ArrayList<Background> insertionSet = new ArrayList<Background>();
		
		for(Background bg : bObjects) {
			bg.y += 0.2*burnFactor;
			
			if(ObjectTrackerUtility.isOutsideOfWindow(bg)) {
				removalSet.add(bg);
			}
			
			if(!bg.hasPassedOrigin) {
				if(bg.y >= 0) {
					bg.hasPassedOrigin = true;
				}
			}
			
			if(bg.hasPassedOrigin && !bg.hasFollowingBackground) {
				insertionSet.add(new Background(bg.x, -544 + bg.y));
				bg.hasFollowingBackground = true;
			}
		}
		
		bObjects.addAll(insertionSet);
		bObjects.removeAll(removalSet);
	}
}
