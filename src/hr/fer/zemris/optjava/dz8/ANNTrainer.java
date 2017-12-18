package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz7.ITransferFunction;

import java.io.IOException;
import java.util.*;
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
		if(args[1].startsWith("tdnn"))
		{
			TDNN network = new TDNN(izvuci(args[1]),8,ITransfer.TANH,datasetTrain);
			DE de = new DE(network, ANNTrainer.population, ANNTrainer.merr, ANNTrainer.maxiter,datasetTrain,datasetReal);
		}
		if(args[1].startsWith("elman"))
		{
			Elman network = new Elman(izvuci(args[1]),ITransfer.TANH,datasetTrain);
			DE de = new DE(network, ANNTrainer.population, ANNTrainer.merr, ANNTrainer.maxiter,datasetTrain,datasetReal);
		}
		//System.out.println(dataset.numberOfSamples());
		
	}
	private static  int[] izvuci(String S)
	{
		List<Integer>list=new ArrayList<>();
		
		int poz=0;
		for(int i=0;i<S.length();++i)
		{
			if(S.charAt(i)=='-')
			{
				poz = i;
				break;
			}
		}
		String[] s=S.substring(poz+1).split("x"); 
		for(int i=0;i<s.length;++i)
		{
			list.add(Integer.parseInt(s[i]));
		}
		
		int[] arr = new int[list.size()];
		int br=0;
		for(Integer I: list)
		{
			arr[br++]=I;
		}
		return arr;
	}
}