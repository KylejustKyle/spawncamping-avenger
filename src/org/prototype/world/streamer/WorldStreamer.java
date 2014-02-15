package org.prototype.world.streamer;

import java.util.ArrayList;
import java.util.List;

import org.prototype.globals.GlobalConfig;
import org.prototype.utility.ObjectTrackerUtility;
import org.prototype.world.entities.Background;

public class WorldStreamer {
	public List<Background> backgrounds;
	
	public WorldStreamer() {
		backgrounds = new ArrayList<Background>();
	}
	
	public void updateBackgrounds(int burnFactor) {
		ArrayList<Background> removalSet = new ArrayList<Background>();
		ArrayList<Background> insertionSet = new ArrayList<Background>();
		
		for(Background bg : backgrounds) {
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
				insertionSet.add(new Background(bg.x, -GlobalConfig.GAME_HEIGHT + bg.y));
				bg.hasFollowingBackground = true;
			}
		}
		
		backgrounds.addAll(insertionSet);
		backgrounds.removeAll(removalSet);
	}
}
