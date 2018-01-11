package hr.fer.zemris.optjava.dz10;
import java.util.function.*;
public class DomainFunction {
	
	private Function<Vector, Boolean> function;
	public DomainFunction(Function<Vector,Boolean> function)
	{
		this.function = function; 
	}
	public boolean isTrue(double[] input)
	{
		return function.apply(new Vector(input));
	}
}
