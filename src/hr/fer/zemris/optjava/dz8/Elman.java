package hr.fer.zemris.optjava.dz8;

public class Elman implements Network{

	private int[] sizeOfLayers;
	
	private ITransfer function; 
	private IReadOnlyDataset dataset;
	private int weightsCount;
	
	private double[] backUpValue;
	
	public Elman(int[] layers,ITransfer function,IReadOnlyDataset dataset)
	{
		this.sizeOfLayers=new int[layers.length];
		this.function=function;
		this.dataset=dataset;
		
		for(int k=0;k<layers.length;++k)
		{
			if(k==0)
			{
				sizeOfLayers[k]=layers[0]+layers[1];
			}
			else
				sizeOfLayers[k]=layers[k];
		}
		
		backUpValue = new double[sizeOfLayers[1]];
		
		setWeightsCount();
		
	}
	private void cleanBackUp()
	{
		backUpValue = new double[sizeOfLayers[1]];
	}
	public int getWeightsCount()
	{
		return this.weightsCount;
	}
	public void setWeightsCount()
	{
		int cnt=0;
		for(int i=1,len=sizeOfLayers.length;i<len;++i)
		{
			cnt+=(sizeOfLayers[i-1]*sizeOfLayers[i]);
			cnt+=sizeOfLayers[i];
		}
		this.weightsCount=cnt;
	}
	
	public double calcError(double[] values, IReadOnlyDataset dataset) {
		cleanBackUp();
		double ans=0;
		//System.out.println("Number of samples: "+dataset.numberOfSamples());
		for(int i=1,len=dataset.numberOfSamples();i<len;++i)
		{
			double inputs[] = new double[sizeOfLayers[0]];
			inputs[0]=dataset.getOutput(i-1);
			for(int j=0;j<backUpValue.length;++j)
				inputs[j+1]=backUpValue[j];
			double[] output=calcOutputs(inputs, values);
			for(int j=0;j<1;++j)
			{
				ans+=(dataset.getOutput(i)-output[j])*(dataset.getOutput(i)-output[j]);
			}
			
		}
		return ans;
	}
	private double[] calcOutputs(double[] inputs, double[] values) {
		int br=0;
		double[] outputs=new double[1];
		
		for(int i=1,len=sizeOfLayers.length;i<len;++i)
		{
			outputs=new double[sizeOfLayers[i]];
			for(int j=0;j<sizeOfLayers[i];++j)
			{
				double sum=0;
				for(int k=0;k<sizeOfLayers[i-1];++k)
				{
					sum+=inputs[k]*values[br++];
				}
				//System.out.println(i-1+"+"+functions.length);
				outputs[j]=function.valueAt(sum+values[br++]);
			}
			if(i==1)
			{
				backUpValue = new double[sizeOfLayers[1]];
				for(int k=0;k<backUpValue.length;++k)
				{
					backUpValue[k]=outputs[k];
				}
			}
			inputs=new double[sizeOfLayers[i]];
			for(int j=0;j<sizeOfLayers[i];++j)
				inputs[j]=outputs[j];
		}
		//System.out.printf("Out: %.3f %n ",outputs[0]);
		return outputs;
	}
	
}
