package hr.fer.zemris.optjava.dz9;

import java.util.Arrays;

public class Vector implements Comparable<Vector>{
	double[] array;
	private Vector(){}
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
	public Vector clone()
	{
		Vector V = new Vector();
		V.array = new double[array.length];
		for(int i=0,len=array.length;i<len;++i)
		{
			V.array[i] = array[i];
		}
		return V;
	}
	public int getNumOfVariables()
	{
		return this.array.length;
	}
	@Override
	public int compareTo(Vector V)
	{
		boolean better = true;
		for(int i=0;i<array.length;++i)
		{
			if(array[i]<V.array[i])
			{
				better = false;
				break;
			}
		}
		if(better)
			return -1;
		boolean worse = true;
		for(int i=0;i<array.length;++i)
		{
			if(array[i]>V.array[i])
			{
				worse = false;
				break;
			}
		}
		if(worse)
			return 1;
		return 0;
	}
}