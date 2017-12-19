package hr.fer.zemris.optjava.dz9;
import java.util.function.*;
public class DomainFunction {
	
	private int variable;
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
