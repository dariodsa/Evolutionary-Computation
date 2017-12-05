package hr.fer.zemris.optjava.dz7;

public class FFANN 
{
	
	private int[] sizeOfLayers; 
	private int weightsCount;
	private ITransferFunction[] functions;
	
	public FFANN(int[] sizeOfLayers,ITransferFunction[] functions,IReadOnlyDataset dataset)
	{
		this.sizeOfLayers=new int[sizeOfLayers.length];
		for(int i=0;i<sizeOfLayers.length;++i)
			this.sizeOfLayers[i]=sizeOfLayers[i];
		
		this.functions=new ITransferFunction[functions.length];
		setWeigthsCount();
	}
	private void setWeigthsCount()
	{
		return 0;
	}
	public int getWeightsCount()
	{
		return weightsCount;
	}
	public void calcOutputs(double[] inputs,double[] weights,double[] outputs)
	{
		for(int i=0,len=sizeOfLayers.length;i<len;++i)
		{
			
		}
	}
}
