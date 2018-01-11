package hr.fer.zemris.optjava.dz10;
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
	
	private final int S = 5;
	
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
		this.results    = new double[2*populationSize][solution.getNumberOfObjectives()];
		generatePopulation();
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
					System.out.print(fronts.get(0).get(0).array[i]+" ");
				}
				System.out.println();
			}
			int iter = 0;
			for(Vector member : population)
			{
				results[iter++] = solution.evaluateSolution(member.array);
			}
			TopologicalSort.runTopologicalSort(results,population);
			generateGroupDistance(population);
			
			List<Vector> newPopulation = new ArrayList<>();
			for(int i=0;i<populationSize;++i)
			{
				Vector parent1 = getParent(S);
				Vector parent2 = getParent(S);
				newPopulation.add(new Vector(getKid(parent1.clone(),parent2.clone())));
			}
			
			for(Vector V : newPopulation){
				population.add(V);
			}
			iter = 0;
			//System.out.println(population.size()+" "+newPopulation.size());
			for(Vector member : population)
			{
				results[iter++] = solution.evaluateSolution(member.array);
			}
			List<List<Vector>> fronts = TopologicalSort.runTopologicalSort(results,population);
			int kol = 0;
			int sigmaFront = 0;
			int toAdd = 0;
			for(int i=0;i<fronts.size();++i){
				kol+=fronts.get(i).size();
				if(kol>=populationSize){
					sigmaFront = i;
					toAdd = kol - populationSize;
					break;
				}
			}
			newPopulation.clear();
			for(int i=0;i<sigmaFront;++i){
				for(Vector V : fronts.get(i)){
					newPopulation.add(V);
				}
			}
			toAdd = populationSize - newPopulation.size();
			//System.out.println("population size "+newPopulation.size()+" toAdd "+toAdd);
			generateGroupDistance(fronts.get(sigmaFront));
			List<Par>parovi = new ArrayList<>();
			iter = 0;
			for(Vector V : fronts.get(sigmaFront)){
				parovi.add(new Par(
						++iter,
						V.groupDistance));
			}
			Collections.sort(parovi);
			for(int i=0,j=parovi.size()-1;i<toAdd;++i,--j){
				newPopulation.add(fronts.get(sigmaFront).get(j).clone());
			}
			
			population = newPopulation;
			//System.out.println("Final sol "+population.size()+" "+newPopulation.size());
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
	private Vector getParent(int S)
	{
		List<Vector>potentional_parents = new ArrayList<>();
		for(int i=0;i<S;++i)
		{
			potentional_parents.add(population.get(rand.nextInt(population.size())));
		}
		Vector V = null;
		int value1 = Integer.MAX_VALUE;
		double value2 = Double.NEGATIVE_INFINITY;
		for(Vector parent : potentional_parents){
			if(parent.frontId<value1){
				value1 = parent.frontId;
				value2 = parent.groupDistance;
				V = parent.clone();
			}
			if(V.frontId==value1 && parent.groupDistance>value2){
				value1 = parent.frontId;
				value2 = parent.groupDistance;
				V = parent.clone();
			}
		}
		return V;
	}
	private double[] getKid(Vector parent1, Vector parent2)
	{
		while(true)
		{
			double[] kid = new double[solution.getNumOfVariables()];
			for(int i=0;i<solution.getNumOfVariables();++i)
			{
				double raz = rand.nextDouble()*(parent2.array[i]-parent1.array[i]+0.02);
				kid[i] = (parent1.array[i]-0.01)+raz;
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
	private void generateGroupDistance(List<Vector> list) throws Exception
	{
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for(int i=0;i<list.size();++i){
			list.get(i).groupDistance = 0;
		}
		for(int i=0;i<this.solution.getNumberOfObjectives();++i){
			List<Par> parovi = new ArrayList<>();
			for(int j=0;j<list.size();++j){
				parovi.add(new Par(
						j,
						solution.evaluateSolution(list.get(j).array)[i]));
			}
			Collections.sort(parovi);
			
			min = parovi.get(0).val;
			max = parovi.get(parovi.size()-1).val;
			
			list.get(parovi.get(0).id).groupDistance += Double.MAX_VALUE;
			list.get(parovi.get(parovi.size()-1).id).groupDistance += Double.MAX_VALUE;
			
			for(int j=1;j<parovi.size()-1;++j){
				list.get(parovi.get(j).id).groupDistance += 
						 +  (parovi.get(j-1).val - parovi.get(j+1).val) 
						     / (max-min);
			}
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
	
}
class Par implements Comparable<Par>{
	int id;
	double val;
	public Par(int id,double val){
		this.id = id;
		this.val = val;
	}
	public int compareTo(Par A)
	{
		return Double.compare(val, A.val);
	}
}
