package hr.fer.zemris.optjava.dz9;
import java.io.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Population 
{
	private static Random rand = new Random(System.currentTimeMillis());
	
	private final double minValue = -5;
	private final double maxValue = 5;
	private final double sigmaShare = 0.5;
	private final double epsilon = 0.9;
	private final double alpha = 2;
	
	private int populationSize;
	private int maxiter;
	
	private MOOPSolution solution;
	private List<Vector> population;
	private double[][] results;
	double[] kindness; 
	
	public Population(int populationSize, int maxiter,MOOPSolution solution) throws Exception
	{
		this.populationSize = populationSize;
		this.maxiter        = maxiter;
		this.solution       = solution;
		
		this.population = new ArrayList<>();
		this.results    = new double[populationSize][solution.getNumberOfObjectives()];
		this.kindness   = new double[populationSize];
		generatePopulation();
		/*for(int i=0;i<populationSize;++i)
			results[i] = solution.evaluateSolution(population.get(i).array);
		for(int i=0;i<populationSize;++i)
		{
			for(int j=0;j<population.get(i).array.length;++j)
			{
				System.out.printf("%f ",population.get(i).array[j]);
			}
			System.out.printf("%n Result ");
			for(int j=0;j<results[i].length;++j)
			{
				System.out.printf("%f ", results[i][j]);
			}
			System.out.printf("%n");
		}*/
	}
	public void run() throws Exception
	{
		List<String> lines = new ArrayList<>();
		while(maxiter-->0)
		{
			
			if(maxiter%100==0)
			{
				System.out.println("Iterations remains ... " +maxiter);
				System.out.println("Results: %n");
				List<List<Vector>> fronts = TopologicalSort.runTopologicalSort(results,population);
				for(int i=0;i<fronts.get(0).get(0).array.length;++i)
				{
					System.out.print(fronts.get(0).get(0).array[0]+" ");
				}
				System.out.println();
			}
			
			generateKindnessOfPopulation();
			
			List<Vector> newPopulation = new ArrayList<>();
			for(int i=0;i<populationSize;++i)
			{
				Vector parent1 = getParent();
				Vector parent2 = getParent();
				newPopulation.add(new Vector(getKid(parent1.clone(),parent2.clone())));
			}
			population.clear();
			for(Vector V : newPopulation){
				population.add(V.clone());
			}
			newPopulation.clear();	
		}
		List<String>lines2 = new ArrayList<>();
		for(int i=0;i<populationSize;++i)
			results[i] = solution.evaluateSolution(population.get(i).array);
		for(int i=0;i<populationSize;++i)
			lines.add(results[i][0] + " " + results[i][1]);
		for(int i=0;i<populationSize;++i)
			lines2.add(population.get(i).array[0]+ " " + population.get(i).array[1]);
		for(int i=0;i<lines.size();++i)
		{
			lines.set(i,lines.get(i).replace('.', ','));
			lines2.set(i,lines2.get(i).replace('.', ','));
		}
		Path file = Paths.get("izlaz-dec.txt");
		Files.write(file, lines, Charset.forName("UTF-8"));
		Files.write(Paths.get("izlaz-dec-2.txt"), lines2, Charset.forName("UTF-8"));
		printSolution();
	}
	private void printSolution() throws Exception
	{
		for(int i=0;i<populationSize;++i)
			results[i] = solution.evaluateSolution(population.get(i).array);
		List<List<Vector>> fronts = TopologicalSort.runTopologicalSort(results,population);
		System.out.println("Results: %n");
		for(int i=0;i<fronts.get(0).get(0).array.length;++i)
		{
			System.out.print(fronts.get(0).get(0).array[i]+" ");
		}
		
	}
	private Vector getParent()
	{
		double sum = 0;
		for(int i=0;i<kindness.length;++i)
			sum += kindness[i];
		double random = rand.nextDouble() * sum;
		
		sum = 0;
		for(int i=0;i<kindness.length;++i)
		{
			sum+=kindness[i];
			if(sum<=random)
				return population.get(i);
		}
		return population.get(0);
	}
	private double[] getKid(Vector parent1, Vector parent2)
	{
		while(true)
		{
			double[] kid = new double[solution.getNumOfVariables()];
			for(int i=0;i<solution.getNumOfVariables();++i)
			{
				kid[i] = (parent1.array[i]+parent2.array[i])/2.0
						+ rand.nextGaussian()/3.0*0.125;
			}
			if(solution.isDomainOk(kid))
				return kid;
		}
	}
	private void generatePopulation() throws Exception //random from 
	{
		int num = 0;
		double distance = Math.abs(minValue - maxValue);
		while(num<populationSize)
		{
			double[] kid = new double[solution.getNumOfVariables()];
			for(int i=0, len=solution.getNumOfVariables();i<len;++i)
			{
				double x = rand.nextDouble() * distance + minValue;
				kid[i] = x;
			}
			if(solution.isDomainOk(kid))
			{
				population.add(new Vector(kid));
				++num;
			}
		}
		int iter = 0;
		for(Vector member : population)
		{
			results[iter++] = solution.evaluateSolution(member.array);
		}
	}
	private void generateKindnessOfPopulation()
	{
		int N = 50;
		double Fmin = N + epsilon;
		List<List<Vector>> fronts = TopologicalSort.runTopologicalSort(results,population);
		int br = 0;
		List<Vector> tempPopulation = new ArrayList<>();
		for(List<Vector> front : fronts)
		{
			double min = Double.POSITIVE_INFINITY;
			for(int j=0;j<front.size();++j)
			{
				tempPopulation.add(front.get(j));
				double gustocaNise = gustocaNise(j, front);
				//values.add((Fmin-epsilon)/gustocaNise);
				kindness[br++] = (Fmin-epsilon)/gustocaNise; 
				//System.out.println((Fmin-epsilon)+ " -> " + gustocaNise+ " = " + kindness[br-1]);
				min = Math.min(min, (Fmin-epsilon)/gustocaNise);
			}
			Fmin = min;
		}
		population.clear();
		
		for(int i=0;i<populationSize;++i)
			population.add(tempPopulation.get(i).clone());
	}
	private double potencija(double x, double y)
	{
		if(y == 2.0)
		{
			return x*x;
		}
		return Math.pow(x,y);
	}
	private double sh(double d)
	{
		return d<sigmaShare?(1.0-potencija(d/sigmaShare,alpha)):0;
	}
	private double dist(Vector V1, Vector V2,double[] nazivnik)
	{
		double sol = 0;
		for(int i=0;i<V1.array.length;++i)
		{
			sol += (V1.array[i]-V2.array[i])*(V1.array[i]-V2.array[i]) /
					(nazivnik[i]);
		}
		return Math.sqrt(sol);
	}
	private double gustocaNise(int pos, List<Vector> list)
	{
		double ans = 0;
		double[] nazivnik = new double[list.get(0).getNumOfVariables()];
		for(int j=0;j<list.get(0).getNumOfVariables();++j)
		{
			double min = Double.POSITIVE_INFINITY;
			double max = Double.NEGATIVE_INFINITY;
			for(int i=0;i<list.size();++i)
			{
				min = Math.min(min,list.get(i).get(j));
				max = Math.max(max,list.get(i).get(j));
			}	
			nazivnik[j] = (max-min)*(max-min);
		}
		for(int i=0;i<list.size();++i)
		{
			ans += sh(dist(list.get(pos),list.get(i),nazivnik));
		}
		return ans;
	}
}
