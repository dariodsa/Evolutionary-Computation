package hr.fer.zemris.optjava.dz11.gen;

import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rng.RNG;
import hr.fer.zemris.optjava.dz11.thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class GA {
	
	private int populationSize;
	private double minFitness;
	private int maxIteration;
	private int numOfRectangles;
	private int width;
	private int heigth;
	private IGAEvaluator<int[]> evaluator;
	
	private Queue<GASolution<int[]>> tasks;
	private Queue<GASolution<int[]>> results;
	
	private final double MUTTATION_RATE = 0.1;
	
	private List<GASolution<int[]>> population ;
	private ThreadPool pool;
	
	public GA(int populationSize, double minFitness, int maxIteration, int numOfRectangles, int width, int height, IGAEvaluator<int[]> evaluator) {
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
		
		pool = new ThreadPool(NUM_OF_WORKERS, tasks, results, evaluator);
		
		population = initPopulation();
		int iteration = 0;
		evaluate(population);
		GASolution<int[]> best = bestOfPopulation(population);
		while(iteration < maxIteration || best.fitness > minFitness) 
		{
			if(iteration % 100 == 0)
				System.out.println(iteration + ". => "+best.fitness);
			List<GASolution<int[]>> newPopulation = new ArrayList<GASolution<int[]>>();
			
			for(int i=0; i<populationSize; ++i)
			{
				GASolution<int[]> parent1 = findParent();
				GASolution<int[]> parent2 = findParent();
				GASolution<int[]> kid = cross(parent1,parent2);
				muttate(kid);
				
				newPopulation.add(kid);
			}
			
			evaluate(newPopulation);
			population = newPopulation;
			
			best = bestOfPopulation(population);
			++iteration;
		}
		
		pool.killAll();
		
		return best;
	}

	private GASolution<int[]> findParent() {
		double rand = RNG.getRNG().nextDouble();
		double sum = 0;
		double sum2 = 0;
		for(int i=0;i<populationSize;++i)
		{
			sum += population.get(i).fitness;
		}
		for(int i=0;i<populationSize;++i)
		{
			sum2 += population.get(i).fitness;
			if(sum2>rand*sum)
				return population.get(i);
		}
		return population.get(0);
	}
	private void muttate(GASolution<int[]> kid) 
	{
		IRNG rnd = RNG.getRNG();
		for(int i=0,len = kid.getData().length;i<len;++i)
		{
			if(rnd.nextDouble()<MUTTATION_RATE)
			{
				if(i%5==0)kid.data[i] = change(0,width-1,kid.data[i],5);
				if(i%5==0)kid.data[i] = change(0,heigth-1,kid.data[i],5);
				if(i%5==0)kid.data[i] = change(1,width-kid.data[i],kid.data[i],0.01);
				if(i%5==0)kid.data[i] = change(1,heigth-kid.data[i],kid.data[i],0.01);
				if(i%5==0)kid.data[i] = change(0,256,kid.data[i],0.2);
			}
		}
	}
	private int change(int min,int max, int value,double changeRate)
	{
		IRNG rnd = RNG.getRNG();
		int value2 = value;
		value2+=(int)((double)changeRate * rnd.nextGaussian());
		if(max<value2)
			value2 = max - rnd.nextInt(0, (max-min));
		if(min>value2)
			value2=min - rnd.nextInt(0, (max-min));
		return value2;
	}
	private GASolution<int[]> cross(GASolution<int[]> parent1, GASolution<int[]> parent2)
	{
		GASolution<int[]> kid = new GAIntArrSolution();
		int len = parent1.getData().length;
		
		int[] data = new int[len];
		IRNG rnd = RNG.getRNG();
		
		int cross = rnd.nextInt(0, len-1);
		
		for(int i=0;i<cross;++i)
		{
			data[i] = parent1.getData()[i];
		}
		for(int i=cross;i<len;++i)
		{
			data[i] = parent2.getData()[i];
		}
		kid.setData(data);
		return kid;
	}
	private void evaluate(List<GASolution<int[]>> population) {
		
		for(GASolution<int[]> solution : population)
		{
			tasks.add(solution);
		}
		//System.out.println(tasks.size());
		pool.startAll();
		pool.joinAll();
		//System.out.println(results.size());
		for(int i=0;i<populationSize;++i)
		{
			//results.peek();
			//todo population.
			double fit = results.peek().fitness;
			population.get(i).fitness= fit;
			results.poll();
		}
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
			//System.out.println(solution.getData().length);
			population.add(solution);
		}
		return population;
	}
	
}
