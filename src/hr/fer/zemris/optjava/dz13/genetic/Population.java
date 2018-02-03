package hr.fer.zemris.optjava.dz13.genetic;

import hr.fer.zemris.optjava.dz13.genetic.ant.Ant;
import hr.fer.zemris.optjava.dz13.genetic.ant.AntPosition;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;
import hr.fer.zemris.optjava.dz13.genetic.population.InitPopulation;
import hr.fer.zemris.optjava.dz13.genetic.population.Moves;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
	
	public static char[][] matrix;
	
	private int maxGeneration;
	private int populationSize;
	private int minFitness;
	
	private static Random rand = new Random();
	
	private int bestFitness;
	
	private final double MUTATION_RATE = 0.14;
	private final double CROSS_RATE = 0.85;
	private final double REPRODUCTION_RATE = 0.01;
	
	private List<Ant>population;
	
	public static List<Node> possibleNodes;
	
	public Population(char[][] matrix, int maxGeneration, int populationSize, int minFitness)
	{
		Population.matrix = matrix;
		this.maxGeneration = maxGeneration;
		this.populationSize = populationSize;
		this.minFitness = minFitness;
		
		this.population  = new ArrayList<>();
		this.bestFitness = 0;
		
		possibleNodes = new ArrayList<>();
		Moves.addMoves(possibleNodes);
		
	}
	public void run()
	{
		InitPopulation.initPopulation(population, populationSize);
		
		int iter = maxGeneration;
		while(iter>=0 || bestFitness > minFitness){
			if(iter % 10 == 0)
				System.out.printf("%d iterations left ...%nCurrent best one %d%n",iter,bestFitness);
			--iter;
			List<Ant> newPopulation = new ArrayList<>();
			while(true)
			{
				double randNum = Population.rand.nextDouble();
				if(randNum < REPRODUCTION_RATE)
				{
					
					if(newPopulation.size()==populationSize)
						break;
				}
				if(randNum < MUTATION_RATE)
				{
					
					if(newPopulation.size()==populationSize)
						break;
				}
				if(randNum < CROSS_RATE)
				{
					
					if(newPopulation.size()==populationSize)
						break;
				}
			}
			population = newPopulation;
			for(Ant A : population)
				A.run();
			population.sort(null);
			bestFitness = population.get(0).getFitness();
		}
	}
	public void mutate()
	{
		
	}
	
}
