package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz7.ITransferFunction;

import java.io.IOException;

public class ANNTrainer {

	private static int population;
	private static double merr;
	private static int maxiter;
	
	public static void main(String[] args) {
		
		//file, net, n, merr, maxiter
		
		ANNTrainer.population = Integer.parseInt(args[2]);
		ANNTrainer.merr = Double.parseDouble(args[3]);
		ANNTrainer.maxiter = Integer.parseInt(args[4]);
		
		IReadOnlyDataset datasetTrain=null;
		IReadOnlyDataset datasetReal=null;
		
		
		
		try 
		{
			datasetTrain= new IReadOnlyDataset(args[0], 592);
			datasetReal= new IReadOnlyDataset(args[0], -1);
		}
		catch (IOException e) {e.printStackTrace();}
		
		TDNN network = new TDNN(new int[] {1,10,1},8,ITransfer.TANH,datasetTrain);
		
		//System.out.println(dataset.numberOfSamples());
		
		if(true)
		{
			DE de = new DE(network, ANNTrainer.population, ANNTrainer.merr, ANNTrainer.maxiter,datasetTrain,datasetReal);
		}
	}
	
}