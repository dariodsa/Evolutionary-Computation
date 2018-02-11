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
	
	private final double MUTATION_RATE = 0.34;
	private final double CROSS_RATE = 0.65;
	private final double REPRODUCTION_RATE = 0.01;
	private final int TOURNAMENT_SIZE = 7;
	public final int MAX_NUM_OF_NODES = 200;
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
		bestFitness = population.get(0).getFitness();
		while(iter>=0 || bestFitness > minFitness){
			if(iter % 10 == 0)
				System.out.printf("%d iterations left ...%nCurrent best one %d%n",iter,bestFitness);
			--iter;
			List<Ant> newPopulation = new ArrayList<>();
			while(true)
			{
				/*for(int i=0;i<populationSize;++i)
				{
					newPopulation.add(population.get(i).clone());
				}
				break;*/
				double randNum = Population.rand.nextDouble();
				//System.out.println(randNum + " " + newPopulation.size());
				if(randNum < REPRODUCTION_RATE)
				{
					int id = getOne();
					newPopulation.add(population.get(id).clone());
					if(newPopulation.size()>=populationSize)
						break;
				}
				else if(randNum < MUTATION_RATE)
				{
					//System.out.println("MUTATE");
					int id = getOne();
					Ant newKid = mutate(id);
					newPopulation.add(newKid);
					if(newPopulation.size()>=populationSize)
						break;
				}
				else if(randNum < CROSS_RATE)
				{
					int parent1 = getOne();
					int parent2 = getOne();
					//System.out.printf("CROSS %d %d%n",parent1,parent2);
					//cross(parent1,parent2);
					int node1 = rand.nextInt(population.get(parent1).getNodes().size());
					int node2 = rand.nextInt(population.get(parent2).getNodes().size());
					
					Ant newKid1 = population.get(parent1).clone();
					Ant newKid1_temp = newKid1.clone();
					Ant newKid2 = population.get(parent2).clone();
					
					newKid1.setNode(
							newKid2.getNodes().get(node2)
							, node1);
					newKid2.setNode(newKid1_temp.getNodes().get(node1)
							, node2);
					newKid1.setAllKidsAllOverAgain();
					newKid2.setAllKidsAllOverAgain();
					newPopulation.add(newKid1);
					newPopulation.add(newKid2);
					
					if(newPopulation.size()>=populationSize)
						break;
				}
			}
			population = newPopulation;
			//System.out.println("Evaluation ...");
			for(Ant A : population)
			{
				A.run();
			}
			/*for(Ant A : population)
			{
				System.out.printf("%d ",A.getFitness());
			}
			System.out.printf("%n");*/
			Collections.sort(population);
			bestFitness = population.get(0).getFitness();
			
		}
		population.get(0).run(1);
	}
	private Ant mutate(int id) 
	{
		int node = rand.nextInt(population.get(id).getNodes().size());
		int numOfNodes = MAX_NUM_OF_NODES - population.get(id).getNodes().size();
		Ant temp = population.get(id).clone();
		Node subTree = InitPopulation.getSubTreeSize(numOfNodes, possibleNodes);
		
		Node parent = temp.getNode(node).getParent();
		int pos = -1;
		if(parent != null)
		{
			for(int i = 0; i < parent.getKidsSize(); ++i)
			{
				if(parent.getKids().get(i) == temp.getNode(node))
				{
					pos = i;
				}
			}
			temp.setNode(subTree, node);
			parent.getKids().set(pos, subTree);
		}
		else
		{
			temp.setNode(subTree, node);
			temp.setInitNode(subTree);
		}
		temp.setAllKidsAllOverAgain();
		return temp;
	}
	private int getOne() {
		List<Integer> list = new ArrayList<>();
		for(int i=0;i<TOURNAMENT_SIZE;++i)
			 list.add(rand.nextInt(populationSize));
		int fit = -1;
		int id = 0;
		for(int i=0;i<TOURNAMENT_SIZE;++i)
			 if(population.get(list.get(i)).getFitness()>fit)
			 {
				 id = list.get(i);
				 fit = population.get(list.get(i)).getFitness();
			 }
		return id;
	}
	public Ant getTheBestOne()
	{
		return this.population.get(0);
	}
	private void cross(int parent1, int parent2)
	{
		
	}
	
}
