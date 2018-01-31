package hr.fer.zemris.optjava.dz13;

import java.util.ArrayList;
import java.util.List;

public class Generation {
	
	private char[][] matrix;
	private int maxGeneration;
	private int populationSize;
	private int minFitness;
	
	private int bestFitness;
	
	private final double MUTATION_RATE = 0.14;
	private final double CROSS_RATE = 0.85;
	private final double REPRODUCTION_RATE = 0.01;
	
	private List<Object>population;
	
	public Generation(char[][] matrix, int maxGeneration, int populationSize, int minFitness)
	{
		this.matrix = matrix;
		this.maxGeneration = maxGeneration;
		this.populationSize = populationSize;
		this.minFitness = minFitness;
		
		this.population  = new ArrayList<>();
		this.bestFitness = 0;
	}
	public void run()
	{
		int iter = maxGeneration;
		while(iter>=0 || bestFitness > minFitness){
			if(iter % 10 == 0)
				System.out.printf("%d iterations left ...%nCurrent best one %d%n",iter,bestFitness);
			--iter;
		}
	}
	public void mutate()
	{
		
	}
	
}
