package org.prototype.math;

import org.prototype.globals.GlobalConfig;

public class ReverseLinearFunction implements Function {

	@Override
	public float compute(float x) {
		return GlobalConfig.GAME_WIDTH - x;
	}
	
	public FunctionType getType() {
		return FunctionType.REVERSE_LINEAR;
	}

}
