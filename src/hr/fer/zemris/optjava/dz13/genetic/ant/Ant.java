package hr.fer.zemris.optjava.dz13.genetic.ant;

import hr.fer.zemris.optjava.dz13.genetic.Population;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;

import java.util.*;

public class Ant implements Comparable<Ant>, Cloneable
{
	private final int MAX_OPERATIONS = 600;
	private AntPosition antPosition;
	private Node initNode;
	private List<Node> nodes;
	private int fitness;
	public int sumNodes;
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
		this.sumNodes = 0;
		while(this.sumNodes<MAX_OPERATIONS)
		{
			int k = this.sumNodes;
			initNode.perform(this);
			if(k==this.sumNodes)
				System.out.println(this.sumNodes + " :-(");
		}
	}
	public void run(int pos)
	{
		this.fitness = 0;
		this.antPosition = new AntPosition(0, 0);
		Population.positions.add(this.antPosition.clone());
		sumNodes = 0;
		while(sumNodes<MAX_OPERATIONS)
		{
			initNode.perform(this,1);
		}
	}
	public String run(String t)
	{
		this.fitness = 0;
		this.antPosition = new AntPosition(0, 0);
		Population.positions.add(this.antPosition.clone());
		sumNodes = 0;
		String s="";
		while(sumNodes<MAX_OPERATIONS)
		{
			s+=initNode.perform(this,1,1);
			s+="\n";
		}
		return s;
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
	@Override
	public int compareTo(Ant A)
	{
		return Integer.compare(A.fitness, fitness);
	}
	public Node getNode(int pos)
	{
		return this.nodes.get(pos);
	}
	private void construct(Node pos)
	{
		addNode(pos);
		for(Node kid : pos.getKids())
		{
			construct(kid);
		}
	}
	public void setAllKidsAllOverAgain()
	{
		nodes.clear();
		setAllKidsAllOverAgain(initNode);
	}
	private void setAllKidsAllOverAgain(Node node) 
	{		
		nodes.add(node);
		for(Node kid: node.kids)
		{
			setAllKidsAllOverAgain(kid);
		}
	}
	@Override
	public Ant clone()
	{
		Ant A = new Ant();
		A.fitness = fitness;
		
		A.initNode = initNode.clone();
		A.construct(A.initNode);
		return A;
	}
}
