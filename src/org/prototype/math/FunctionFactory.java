package org.prototype.math;

public class FunctionFactory {
	public static Function createFunction(FunctionType funcType, float ... fs ) {
		switch(funcType) {
			case LINEAR : 
			{
				return new LinearFunction();
			}
			case REVERSE_LINEAR : 
			{
				return new ReverseLinearFunction();
			}
		}
		
		return null;
	}
}
