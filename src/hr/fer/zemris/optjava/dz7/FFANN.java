package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;

public class FFANN 
{
	private IReadOnlyDataset dataset;
	private int[] sizeOfLayers; 
	private int weightsCount;
	private ITransferFunction[] functions;
	
	public FFANN(int[] sizeOfLayers,ITransferFunction[] functions,IReadOnlyDataset dataset)
	{
		this.sizeOfLayers=new int[sizeOfLayers.length];
		for(int i=0;i<sizeOfLayers.length;++i)
			this.sizeOfLayers[i]=sizeOfLayers[i];
		
		this.functions=Arrays.copyOf(functions, functions.length);
		this.dataset=dataset;
		setWeigthsCount();
	}
	public double calcError(double[] values)
	{
		double ans=0;
		//System.out.println(dataset.numberOfSamples());
		for(int k=0;k<dataset.numberOfSamples();++k)
		{
			double[] inputs=new double[dataset.numberOfInputs()];
			//double[] output=new double[dataset.numberOfOutputs()];
			
			inputs=dataset.getSample(k);
			
			double[] output=calcOutputs(inputs, values);
			for(int j=0;j<dataset.numberOfOutputs();++j)
			{
				//System.out.println(output[j]+","+dataset.getOutput(k)[j]);
				ans+=(dataset.getOutput(k)[j]-output[j])*(dataset.getOutput(k)[j]-output[j]);
			}
		}
		return ans/dataset.numberOfSamples();
	}
	private void setWeigthsCount()
	{
		int cnt=0;
		for(int i=1,len=sizeOfLayers.length;i<len;++i)
		{
			cnt+=(sizeOfLayers[i-1]*sizeOfLayers[i]);
			cnt+=sizeOfLayers[i];
		}
		this.weightsCount=cnt;
		return;
	}
	public int getWeightsCount()
	{
		return weightsCount;
	}
	public double[] calcOutputs(double[] inputs,double[] weights)
	{
		int br=0;
		double[] outputs=new double[1];
		//System.out.println(functions[0].function(5));
		for(int i=1,len=sizeOfLayers.length;i<len;++i)
		{
			outputs=new double[sizeOfLayers[i]];
			for(int j=0;j<sizeOfLayers[i];++j)
			{
				double sum=0;
				for(int k=0;k<sizeOfLayers[i-1];++k)
				{
					sum+=inputs[k]*weights[br++];
				}
				//System.out.println(i-1+"+"+functions.length);
				outputs[j]=functions[i-1].function(sum+weights[br++]);
			}
			inputs=new double[sizeOfLayers[i]];
			for(int j=0;j<sizeOfLayers[i];++j)
				inputs[j]=outputs[j];
		}
		return outputs;
	}
}
