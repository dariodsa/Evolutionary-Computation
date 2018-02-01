package hr.fer.zemris.optjava.dz13.genetic;

import hr.fer.zemris.optjava.dz13.genetic.ant.Ant;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;

import java.util.ArrayList;
import java.util.List;

public class Population {
	
	private char[][] matrix;
	private int maxGeneration;
	private int populationSize;
	private int minFitness;
	
	private int bestFitness;
	
	private final double MUTATION_RATE = 0.14;
	private final double CROSS_RATE = 0.85;
	private final double REPRODUCTION_RATE = 0.01;
	
	private List<Ant>population;
	
	public static List<Node> possibleNodes;
	
	public Population(char[][] matrix, int maxGeneration, int populationSize, int minFitness)
	{
		this.matrix = matrix;
		this.maxGeneration = maxGeneration;
		this.populationSize = populationSize;
		this.minFitness = minFitness;
		
		this.population  = new ArrayList<>();
		this.bestFitness = 0;
		
		possibleNodes = new ArrayList<>();
		possibleNodes.add(new Node(
					"MOVE",
					(e)->{return new Boolean(true);},
					(e)->{e.setXandY(e.getX()+e.getMoveX(),e.getY()+e.getMoveY());
						return null;},
					0
		));
		possibleNodes.add(new Node(
				"LEFT",
				(e)->{return new Boolean(true);},
				(e)->{e.moveLeft();
					return null;},
				0
		));
		possibleNodes.add(new Node(
				"RIGHT",
				(e)->{return new Boolean(true);},
				(e)->{e.moveRight();
					return null;},
				0
		));
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
