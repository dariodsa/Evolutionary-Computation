package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.optjava.dz11.gen.GAIntArrSolution;
import hr.fer.zemris.optjava.dz11.gen.GASolution;
import hr.fer.zemris.optjava.dz11.gen.IGAEvaluator;
import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rng.RNG;
import hr.fer.zemris.optjava.dz11.thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GA2 {
	private int populationSize;
	private double minFitness;
	private int maxIteration;
	private int numOfRectangles;
	private int width;
	private int heigth;
	private IGAEvaluator<int[]> evaluator;
	
	private Queue<Task> tasks;
	private Queue<GASolution<int[]>[]> results;
	
	private final double MUTTATION_RATE = 0.02;
	private final int AT_ONCE = 10;
	
	public GA2(int populationSize, double minFitness, int maxIteration, int numOfRectangles, int width, int height, IGAEvaluator<int[]> evaluator) {
		this.populationSize = populationSize;
		this.minFitness = minFitness;
		this.maxIteration = maxIteration;
		this.numOfRectangles = numOfRectangles;
		this.width = width;
		this.heigth = height;
		this.evaluator = evaluator;
	
		tasks = new ConcurrentLinkedQueue<>();
		results = new ConcurrentLinkedQueue<>();
	}
	public GASolution<int[]> run() 
	{
		int NUM_OF_WORKERS = Runtime.getRuntime().availableProcessors();
		
		ThreadPool2 pool = new ThreadPool2(NUM_OF_WORKERS, tasks, results, evaluator);
		
		List<GASolution<int[]>> population = initPopulation();
		int iteration = 0;
		GASolution<int[]> best = bestOfPopulation(population);
		while(iteration < maxIteration || best.fitness > minFitness) 
		{
			if(iteration % 10 == 0)
				System.out.println(iteration + ". => "+best.fitness);
			List<GASolution<int[]>> newPopulation = new ArrayList<GASolution<int[]>>();
			
			for(int i=0;i<populationSize/AT_ONCE;++i)
			{
				Task task = new Task(AT_ONCE,population); 
				this.tasks.add(task);
			}
			for(int i=0;i<populationSize/AT_ONCE;++i)
			{
				GASolution<int[]>[] result = results.peek();
				results.poll();
				for(GASolution<int[]> sol : result)
					newPopulation.
					
			}
			
			population = newPopulation;
			best = bestOfPopulation(population);
			++iteration;
		}
		
		pool.killAll();
		
		return best;
	}
	private GASolution<int[]> bestOfPopulation(List<GASolution<int[]>> population) 
	{
		GASolution<int[]> best = population.get(0);
		for(GASolution<int[]> current : population) 
			if(current.fitness < best.fitness)
				best = current;
		return best;	
	}
	private List<GASolution<int[]>> initPopulation() {
		
		IRNG rnd = RNG.getRNG();
		List<GASolution<int[]>> population = new ArrayList<>();
		
		int dataSize = 1 +numOfRectangles*5;
		for(int i=0;i<populationSize;++i)
		{
			GASolution<int[]> solution = new GAIntArrSolution();
			
			int[] data = new int[dataSize];
			
			data[0] = rnd.nextInt(0,256);
			int index = 1;
			for(int j=0;j<numOfRectangles;++j)
			{
				data[index] = rnd.nextInt(0,width);
				data[index + 1] = rnd.nextInt(0,heigth);
				data[index + 2] = rnd.nextInt(0, 30);
				data[index + 3] = rnd.nextInt(0, 30);
				data[index + 4] = rnd.nextInt(0, 256);
				
				index+=5;
			}
			solution.setData(data);
			population.add(solution);
		}
		return population;
	}
}
