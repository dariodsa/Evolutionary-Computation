package hr.fer.zemris.optjava.dz13.genetic.node;

import hr.fer.zemris.optjava.dz13.genetic.ant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Node implements INode 
{
	private int kidsSize;
	private String name;
	private Function<AntPosition,Boolean> functionCan;
	private Function<Integer, Void> functionPerform;
	
	public List<Node>kids = new ArrayList<>();
	
	public Node(String name, Function<AntPosition,Boolean> functionCan, int kidsSize)
	{
		this.name = name;
		this.functionCan = functionCan;
		//this.functionPerform = functionPerform;
		this.kidsSize = kidsSize;
		this.kids = new ArrayList<>(kidsSize);
	}

	public int getKidsSize()
	{
		return this.kidsSize;
	}
	
	@Override
	public boolean canPerform(AntPosition position)
	{
		return functionCan.apply(position);
	}
	@Override
	public abstract void perform(Ant ant);
	public void visitKids(Ant A)
	{
		for(Node kid : kids)
		{
			kid.perform(A);
		}
		return;
	}
	
}
