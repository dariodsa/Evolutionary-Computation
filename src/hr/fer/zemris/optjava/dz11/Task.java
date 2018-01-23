package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.optjava.dz11.gen.GASolution;

import java.util.List;

public class Task {
	private int br;
	private List<GASolution<int[]>> population;
	public Task(int br,List<GASolution<int[]>> population)
	{
		this.br = br;
		this.population = population;
	}
	public int getBr()
	{
		return this.br;
	}
	public void setBr(int br)
	{
		this.br = br;
	}
	public List<GASolution<int[]>> getPopulation() 
	{
        return population;
    }

    public void setPopulation(List<GASolution<int[]>> population) 
    {
        this.population = population;
    }
    @Override
    public boolean equals(Object o) 
    {
    	Task task = (Task) o;

        if (br != task.br) return false;
        return population != null ? population.equals(task.population) : task.population == null;
    }
}
