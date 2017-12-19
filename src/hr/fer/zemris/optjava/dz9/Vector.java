package hr.fer.zemris.optjava.dz9;

import java.util.Arrays;

public class Vector {
	double[] array;
	public Vector(double[] array)
	{
		this.array = new double[array.length];
		this.array = Arrays.copyOf(array, array.length);
	}
	public double get(int num)
	{
		if(num < 0 || num >= array.length)
				throw new RuntimeException("Not in the domain of the array.");
		return array[num];
	}
}
