package hr.fer.zemris.optjava.dz7;
import java.util.*;

public class AntiBody implements Comparable<AntiBody>
{
	private static final Random rand=new Random();
	private double fitness;
	private double[] values;
	private FFANN network;
	private int size;
	private AntiBody(AntiBody other){
        this.network = other.network;
        this.values = new double[other.values.length];
        this.size = other.size;

        System.arraycopy(other.values, 0, this.values, 0, other.values.length);
    }
	public AntiBody(int n, double min, double max, FFANN network){
        
        this.size = n;
        this.network=network;
        values = new double[n];

        for (int i = 0; i < n; ++i){
            double value = rand.nextDouble() * (max - min) + min;
            values[i] = value;
        }
	}
	public static AntiBody mutate(AntiBody solution, double mutationRate){
		AntiBody other = solution.clone();

        for (int i = 0; i < other.getSize(); ++i){
            double value = other.getValue(i);
            value += (2*rand.nextDouble() - 1) * mutationRate;
            other.setValue(i, value);
        }

        return other;
	}
	public AntiBody clone(){
        return new AntiBody(this);
}
	public double[] getValues()
	{
		return values;
	}
	public int getSize()
	{
		return this.size;
	}
	public double getValue(int pos)
	{
		return values[pos];
	}
	public void setValue(int pos,double value)
	{
		values[pos]=value;
	}
	public double getFitness()
	{
		return this.fitness;
	}
	public void muttate()
	{
		
	}
	/*public AntiBody clone()
	{
		AntiBody A=new AntiBody();
		return A;
	}*/
	
	public void setEvaluationValue()
	{
		double error=network.calcError(values);
		this.fitness=error;
	}

	public int compareTo(AntiBody O)
	{
		return Double.compare(this.fitness, O.getFitness());
	}
	
}
