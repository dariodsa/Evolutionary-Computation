package hr.fer.zemris.optjava.dz8;
import java.lang.Math;
import java.util.function.Function;

public enum ITransfer {
	
	TANH(Math::tanh);
	
	private Function<Double, Double> function;

    ITransfer(Function<Double, Double> function){
        this.function = function;
    }

    public double valueAt(double x){
        return function.apply(x);
}
}
