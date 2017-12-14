package hr.fer.zemris.optjava.dz8;

public interface Network 
{
	public int getWeightsCount();
	//public double[] calcOutputs(double[] inputs,double[] weights);
	public double calcError(double[] values, IReadOnlyDataset dataset);
}
