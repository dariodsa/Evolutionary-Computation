package hr.fer.zemris.optjava.dz9;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Population 
{
	private static Random rand = new Random(System.currentTimeMillis());
	
	private final double minValue = -10;
	private final double maxValue = 10;
	private final double sigmaShare = 0.1;
	private final double epsilon = 0.9;
	private final double alpha = 2;
	
	private int populationSize;
	private int maxiter;
	
	private MOOPSolution solution;
	private List<Vector> population;
	
	public Population(int populationSize, int maxiter,MOOPSolution solution)
	{
		this.populationSize = populationSize;
		this.maxiter        = maxiter;
		this.solution       = solution;
		
		this.population = new ArrayList<>();
		generatePopulation();
	}
	public void run()
	{
		while(maxiter-->0)
		{
			generateKindnessOfPopulation();
			
			List<Vector> newPopulation = new ArrayList<>();
			for(int i=0;i<populationSize;++i)
			{
				Vector parent1 = getParent();
				Vector parent2 = getParent();
				newPopulation.add(getKid(parent1,parent2));
			}
			population = newPopulation.stream()
						.map(t -> t.clone())
						.collect(Collectors.toList());
						
		}
	}
	private Vector getParent()
	{
		return null;
	}
	private Vector getKid(Vector parent1, Vector parent2)
	{
		return null;
	}
	private void generatePopulation() //random from 
	{
		int num = 0;
		double distance = Math.abs(minValue - maxValue);
		while(num<populationSize)
		{
			double[] kid = new double[solution.getNumOfVariables()];
			for(int i=0, len=solution.getNumOfVariables();i<len;++i)
			{
				double x = rand.nextDouble() * distance - minValue;
				kid[i] = x;
			}
			if(solution.isDomainOk(kid))
			{
				population.add(new Vector(kid));
				++num;
			}
		}
	}
	private void generateKindnessOfPopulation()
	{
		int N = populationSize;
		double Fmin = N + epsilon;
		List<List<Vector>> fronts = TopologicalSort.runTopologicalSort(population);
		for(List<Vector> front : fronts)
		{
			List<Double> values = new ArrayList<>();
			double min = Double.POSITIVE_INFINITY;
			for(int j=0;j<front.size();++j)
			{
				double gustocaNise = gustocaNise(j, front);
				values.add((Fmin-epsilon)/gustocaNise);
				min = Math.min(min, (Fmin-epsilon)/gustocaNise);
			}
			Fmin = min;
		}
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
		return d<sigmaShare?(1-potencija(d/sigmaShare,alpha)):0;
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
