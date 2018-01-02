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
	private List<List<Vector>> runTopologicalSort()
	{
		return null;
	}
	private void generateKindnessOfPopulation()
	{
		//odredi parametar sigma share i MALU POZITIVNU konstantu e
		//Fmin = N + e
		List<List<Vector>> fronts = runTopologicalSort();
		//Topološko sortiranje
		// for petlja za svaku frontu
		   // q rjesenje u fronti 
		   // Fj,q = Fmin - e
		   // gustoca nise ncq 
	}
}
