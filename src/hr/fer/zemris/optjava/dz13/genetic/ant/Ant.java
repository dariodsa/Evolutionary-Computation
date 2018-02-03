package hr.fer.zemris.optjava.dz13.genetic.ant;

import hr.fer.zemris.optjava.dz13.genetic.Population;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;

import java.util.*;

public class Ant implements Comparable<Ant>
{
	private AntPosition antPosition;
	private Node initNode;
	private List<Node> nodes;
	private int fitness;
	public boolean[][] bio;
	public Ant()
	{
		this.antPosition = new AntPosition(0, 0);
		this.nodes = new ArrayList<>();
		this.fitness = 0;
	}
	public void setInitNode(Node N)
	{
		initNode = N;
	}
	public void run()
	{
		this.fitness = 0;
		bio = new boolean[Population.height][Population.width];
		for(int i=0;i<Population.height;++i)
			for(int j=0;j<Population.width;++j)
				bio[i][j] = false;
		this.antPosition = new AntPosition(0, 0);
		initNode.perform(this);
	}
	public void run(int pos)
	{
		this.fitness = 0;
		this.antPosition = new AntPosition(0, 0);
		Population.positions.add(this.antPosition.clone());
		initNode.perform(this,1);
	}
	public int getFitness()
	{
		return this.fitness;
	}
	public void addNode(Node N)
	{
		nodes.add(N);
	}
	public List<Node> getNodes()
	{
		return this.nodes;
	}
	public void setNode(Node N,int pos)
	{
		nodes.set(pos, N);
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
