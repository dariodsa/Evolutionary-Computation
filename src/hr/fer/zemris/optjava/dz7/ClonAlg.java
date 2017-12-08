package hr.fer.zemris.optjava.dz7;

import java.util.*;
import java.util.stream.Collectors;

public class ClonAlg 
{
	private ITransferFunction function;
	private List<AntiBody> population;
	
	private final double PO=1;
	private final double beta = 5; 
	private final double mutationFactor = 0.1;
	
	
	private final int populationSize;
	private final int maxIteration;
	private final double maxError;
	private final FFANN network;
	
	private double bestError;
	
	public ClonAlg(FFANN network,int populationSize, int maxIteration, double maxError)
	{
		this.network=network;
		this.populationSize=populationSize;
		this.maxIteration = maxIteration;
		this.maxError = maxError;
		population=new ArrayList<>();
		bestError=Double.POSITIVE_INFINITY;
		start();
	}
	private void evaluacija()
	{
		/*for(AntiBody A : this.population)
			A.setEvaluationValue(function.function(0));
		Collections.sort(this.population);*/
	}
	private void kloniraj()
	{
		List<AntiBody>clonPopulation=new ArrayList<>();
		
		this.population=new ArrayList<AntiBody>(clonPopulation);
	}
	private void hipermutacija()
	{
		/*for(int i=0,len=population.size();i<len;++i)
		{
			double value=Math.exp(-PO*population.get(i).getEvaluationValue());
			if(new Random().nextDouble()<value)
			{
				population.get(i).muttate();
			}
		}*/
	}
	private void start()
	{
		int iteration=0;
		//inti populacije
		for(int i=0;i<populationSize;++i)
		{
			population.add(new AntiBody(network.getWeightsCount(),-1,1,network));
		}
		for(int i=0;i<populationSize;++i)
		{
			population.get(i).setEvaluationValue();
		}
		Collections.sort(population);
		
		
		
		while(iteration<maxIteration && bestError>maxError)
		{
			if(population.get(0).getFitness()<bestError)
			{
				bestError=population.get(0).getFitness();
				//best=Population.get(0);
			}
			List<AntiBody> clones = new ArrayList<>();
			for(int i=0;i<populationSize;++i)
			{
				AntiBody tmp= population.get(i);
				int numberOfClones = (int)(beta * populationSize / (i + 1));

                double mutationRate = 1-Math.exp(-mutationFactor / tmp.getFitness());
                for (int j = 0; j < numberOfClones; ++j){
                    clones.add(AntiBody.mutate(tmp, mutationRate));
                }
            }
			population.addAll(clones);
			for(int i=0;i<population.size();++i)
			{
				population.get(i).setEvaluationValue();
			}
			population = population.stream()
                    .filter(x -> x.getFitness() > -3.0)
                    .sorted()
                    .limit(populationSize)
                    .collect(Collectors.toList());
			
			//kloniraj ih uz pomoc bete
			iteration++;
			System.out.println(iteration + ": " + bestError);
			
		}
	}
}
