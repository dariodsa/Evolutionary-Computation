package hr.fer.zemris.optjava.dz13.genetic.ant;

import hr.fer.zemris.optjava.dz13.genetic.node.Node;

import java.util.*;

public class Ant implements Comparable<Ant>
{
	private AntPosition antPosition;
	List<Node> nodes = new ArrayList<>();
	private int fitness;
	public Ant()
	{
		this.antPosition = new AntPosition(0, 0);
		this.fitness = 0;
	}
	public void run()
	{
		this.fitness = 0;
		nodes.get(0).perform(this);
	}
	public int getFitness()
	{
		return this.fitness;
	}
	public void addFitness()
	{
		this.fitness++;
	}
	public AntPosition getAntPosition()
	{
		return this.antPosition;
	}
	public int compareTo(Ant A)
	{
		return Integer.compare(A.fitness, fitness);
	}
}
