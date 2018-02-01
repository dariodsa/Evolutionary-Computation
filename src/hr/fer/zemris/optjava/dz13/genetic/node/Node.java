package hr.fer.zemris.optjava.dz13.genetic.node;

import hr.fer.zemris.optjava.dz13.genetic.ant.*;

import java.util.function.Function;

public class Node implements INode 
{
	private int kidsSize;
	private String name;
	private Function<AntPosition,Boolean> functionCan;
	private Function<AntPosition, Void> functionPerform;
	
	
	public Node(String name, Function<AntPosition,Boolean> functionCan, Function<AntPosition,Void> functionPerform, int kidsSize)
	{
		this.name = name;
		this.functionCan = functionCan;
		this.functionPerform = functionPerform;
		this.kidsSize = kidsSize;
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
	public void perform(AntPosition position)
	{
		functionPerform.apply(position);
		return;
	}
	
}
