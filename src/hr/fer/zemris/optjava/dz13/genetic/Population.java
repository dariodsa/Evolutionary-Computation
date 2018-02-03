package hr.fer.zemris.optjava.dz13.genetic;

import hr.fer.zemris.optjava.dz13.genetic.ant.Ant;
import hr.fer.zemris.optjava.dz13.genetic.ant.AntPosition;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;
import hr.fer.zemris.optjava.dz13.genetic.population.InitPopulation;
import hr.fer.zemris.optjava.dz13.genetic.population.Moves;

import java.util.*;
import java.util.List;
import java.util.Random;

public class Population {
	
	public static char[][] matrix;
	
	public static int height;
	public static int width;	
	private int maxGeneration;
	private int populationSize;
	private int minFitness;
	
	private static Random rand = new Random();
	
	private int bestFitness;
	
	private final double MUTATION_RATE = 0.14;
	private final double CROSS_RATE = 0.85;
	private final double REPRODUCTION_RATE = 0.01;
	
	public final int MAX_NUM_OF_NODES = 800;
	public final int MAX_DEPTH = 20;
	
	private List<Ant>population;
	
	public static List<Node> possibleNodes;
	
	public static List<AntPosition> positions = new ArrayList<>();
	
	public Population(char[][] matrix, int maxGeneration, int populationSize, int minFitness)
	{
		Population.matrix = matrix;
		height = matrix.length;
		width  = matrix[0].length;
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
		InitPopulation.initPopulation(population, populationSize,possibleNodes,MAX_NUM_OF_NODES);
		
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
					int id = rand.nextInt(populationSize);
					newPopulation.add(population.get(id));
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
					int parent1 = rand.nextInt(populationSize);
					int parent2 = rand.nextInt(populationSize);
					
					cross(parent1,parent2);
					
					newPopulation.add(population.get(parent1));
					if(newPopulation.size()==populationSize)
						break;
				}
			}
			population = newPopulation;
			for(Ant A : population)
			{
				A.run();
				//System.out.println(A.getFitness());
			}
			Collections.sort(population);
			bestFitness = population.get(0).getFitness();
			
		}
		population.get(0).run(1);
	}
	public Ant getTheBestOne()
	{
		return this.population.get(0);
	}
	private void cross(int parent1, int parent2)
	{
		int node1 = rand.nextInt(population.get(parent1).getNodes().size());
		int node2 = rand.nextInt(population.get(parent2).getNodes().size());
		population.get(parent1).setNode(
				population.get(parent2).getNodes().get(node2)
				, node1);
	}
	
}
