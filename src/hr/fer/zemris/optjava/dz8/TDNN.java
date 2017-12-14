package hr.fer.zemris.optjava.dz8;


public class TDNN implements Network{
	
	int[] sizeOfLayers;
	
	
	private ITransfer function; 
	private IReadOnlyDataset dataset;
	private int weightsCount;
	private int l;
	
	public TDNN(int[] layers,int l,ITransfer function,IReadOnlyDataset dataset)
	{
		this.sizeOfLayers=new int[layers.length];
		this.function=function;
		this.dataset=dataset;
		this.l=l;
		
		for(int k=0;k<layers.length;++k)
		{
			if(k==0)
			{
				sizeOfLayers[k]=l*layers[layers.length-1];
			}
			else
				sizeOfLayers[k]=layers[k];
		}
		
		setWeightsCount();
		
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
	public double calcError(double[] values, IReadOnlyDataset dataset)
	{
		double ans=0;
		//System.out.println("Number of samples: "+dataset.numberOfSamples());
		for(int i=l,len=dataset.numberOfSamples();i<len;++i)
		{
			double inputs[] = new double[sizeOfLayers[0]];
			for(int j=i-1,br=0;j>=(i-l);--j)
			{
				inputs[br++]=dataset.getOutput(j);
			}
			/*if(inputs.length==0)
				inputs[0]=0;*/
			
			double[] output=calcOutputs(inputs, values);
			for(int j=0;j<1;++j)
			{
				ans+=(dataset.getOutput(i)-output[j])*(dataset.getOutput(i)-output[j]);
			}
			
			//if(i<13)System.out.println(dataset.getOutput(i)+"/="+output[0]);
		}
		return ans;///dataset.numberOfSamples();
	}
	public double[] calcOutputs(double[] inputs,double[] weights)
	{
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
					sum+=inputs[k]*weights[br++];
				}
				//System.out.println(i-1+"+"+functions.length);
				outputs[j]=function.valueAt(sum+weights[br++]);
			}
			inputs=new double[sizeOfLayers[i]];
			for(int j=0;j<sizeOfLayers[i];++j)
				inputs[j]=outputs[j];
		}
		//System.out.printf("Out: %.3f %n ",outputs[0]);
		return outputs;
	}
}
