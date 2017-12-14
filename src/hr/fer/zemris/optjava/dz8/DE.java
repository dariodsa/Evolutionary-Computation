package hr.fer.zemris.optjava.dz8;

import java.util.*;

public class DE 
{

	private int population;
	private int maxiter;
	private Network network;
	private double weights[][][];
	private double merr;
	private double F=0.65;
	
	private IReadOnlyDataset datasetTrain;
	private IReadOnlyDataset datasetReal;
	
	private Random rand = new Random();
	private int num=0;
	
	public DE(Network network, int population, double merr, int maxiter, IReadOnlyDataset datasetTrain, IReadOnlyDataset datasetReal) 
	{
		this.population = population;
		this.merr = merr;
		this.maxiter = maxiter;
		this.network=network;
		this.datasetReal = datasetReal;
		this.datasetTrain = datasetTrain;
		//System.out.println("weights count "+network.getWeightsCount());
		this.weights= new double[2][population][network.getWeightsCount()];
		for(int z=0;z<2;++z)
		for(int j=0;j<population;++j)
			for(int i=0,len=weights[0][0].length;i<len;++i)
			{
				weights[z][j][i]=rand.nextDouble()*2.0-1.0;
			}
		
		start();
	}

	private void start()
	{
		
		double bestError = Double.POSITIVE_INFINITY;
		
		while(num<maxiter || bestError<merr)
		{
			System.out.printf("Iteration %d. Best error %f %n",num,bestError);
			for(int i=0,len=population;i<len;++i)
			{
				int vectorTargetId = i;
				//System.out.println(network.getWeightsCount());
				double [] vectorMutant= getVectorMutant();
				
				double [] trailVector = getTrailVector(vectorTargetId,vectorMutant);
				
				double error1=network.calcError(weights[num%2][vectorTargetId],datasetTrain);
				double error2=network.calcError(trailVector, datasetTrain);
				
				/*for(int j=0;j<6;++j)
					System.out.printf("%.3f ",trailVector[j]);
				System.out.printf("%n");
				for(int j=0;j<6;++j)
					System.out.printf("%.3f ",weights[num%2][vectorTargetId][j]);
				System.out.printf("%n");
				
				System.out.printf("%.3f %.3f %n", error1,error2);*/
				
				
				
				if(error1 >= error2)
					weights[(num+1)%2][i]=Arrays.copyOf(trailVector, trailVector.length);
				else 
					weights[(num+1)%2][i]=Arrays.copyOf(weights[(num)%2][vectorTargetId], weights[(num)%2][vectorTargetId].length);
			}
			for(int i=0;i<population;++i)
				bestError = Math.min(bestError,network.calcError(weights[(num+1)%2][i], datasetReal));
			
			++num;
		}
	}
	private double[] getVectorMutant()
	{
		int left=getIdTargetVectorRand();
		int right=getIdTargetVectorRand();
		double[] result = new double[network.getWeightsCount()];
		for(int i=0;i<result.length;++i)
		{
			result[i]=(rand.nextDouble()*2)*(weights[num%2][left][i]-weights[num%2][right][i]);
		}
		int add = getIdTargetVectorRand();
		for(int i=0;i<result.length;++i)
		{
			result[i]+=weights[num%2][add][i];
		}
		return result;
	}
	private double[] getTrailVector(int targetId, double[] mutant)
	{
		double[] result = new double[mutant.length];
		double randNum = rand.nextDouble()*mutant.length;
		for(int i=0;i<randNum;++i)
		{
			result[i] = mutant[i];
		}
		for(int i=(int)randNum;i<mutant.length;++i)
			result[i] = weights[num%2][targetId][i];
		return result;
	}
	private int getIdTargetVectorRand()
	{
		return rand.nextInt(population);
	}
}
