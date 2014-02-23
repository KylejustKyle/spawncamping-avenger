package org.prototype.math;

public class LinearFunction implements Function {
	public LinearFunction() {
	}
	
	public float compute(float x) {
		return x;
	}
	
	public FunctionType getType() {
		return FunctionType.LINEAR;
	}
}
