package hr.fer.zemris.optjava.dz13.genetic.population;

import hr.fer.zemris.optjava.dz13.genetic.ant.Ant;

import java.util.List;

public class InitPopulation {

	public static void initPopulation(List<Ant> population, int populationSize)
	{
		for(int i=0;i<populationSize;++i)
		{
			Ant A = new Ant();
			
			
			
			population.add(A);
		}
	}

}
