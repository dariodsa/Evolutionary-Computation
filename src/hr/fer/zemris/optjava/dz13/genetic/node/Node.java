package hr.fer.zemris.optjava.dz13.genetic.node;

import hr.fer.zemris.optjava.dz13.genetic.Population;
import hr.fer.zemris.optjava.dz13.genetic.ant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Node implements INode, Cloneable
{
	private int kidsSize;
	private String name;
	private NodeType type;
	/*private Function<AntPosition,Boolean> functionCan;
	private Function<Integer, Void> functionPerform;*/
	
	public List<Node>kids = new ArrayList<>();
	
	public Node(String name, NodeType type, int kidsSize)
	{
		this.name = name;
		this.type = type;
		//this.functionCan = functionCan;
		//this.functionPerform = functionPerform;
		this.kidsSize = kidsSize;
		this.kids = new ArrayList<>(kidsSize);
	}
	public void addKid(Node kid)
	{
		kids.add(kid);
	}
	public int getKidsSize()
	{
		return this.kidsSize;
	}
	public List<Node> getKids()
	{
		return this.kids;
	}
	public NodeType getType()
	{
		return this.type;
	}
	@Override
	public boolean canPerform(AntPosition e)
	{
		switch (this.type) {
		case MOVE:
			int tempX = e.getX()+e.getMoveX();
			int tempY = e.getY()+e.getMoveY();
			if(tempX < 0 || tempX >= Population.height || tempY < 0 || tempY >= Population.width)
				return false;
			return true;
		case IFFOODAHEAD:
			tempX = e.getX()+e.getMoveX();
			tempY = e.getY()+e.getMoveY();
			if(tempX < 0 || tempX >= Population.height || tempY < 0 || tempY >= Population.width)
				return false;
			return Population.matrix[e.getX()+e.getMoveX()][e.getY()+e.getMoveY()] == '1';
		default:
			return true;
		}
	}
	@Override
	public void perform(Ant e)
	{
		switch (this.type) {
		case RIGHT:
			e.getAntPosition().moveRight();
			break;
		case LEFT:
			e.getAntPosition().moveLeft();
			break;
		case MOVE:
			if(!canPerform(e.getAntPosition()))
				break;
			e.getAntPosition().setXandY(e.getAntPosition().getX()+e.getAntPosition().getMoveX(),
                    					e.getAntPosition().getY()+e.getAntPosition().getMoveY());
			if(Population.matrix[e.getAntPosition().getX()]
								[e.getAntPosition().getY()] == '1' && !e.bio[e.getAntPosition().getX()]
								[e.getAntPosition().getY()])
			{
					e.addFitness();
					e.bio[e.getAntPosition().getX()][e.getAntPosition().getY()] = true;
					//System.out.println("found food !!!!");
			}
			break;
		case PROG2:
			visitKids(e);
			break;
		case PROG3:
			visitKids(e);
			break;
		case IFFOODAHEAD:
			if(this.canPerform(e.getAntPosition()))
				this.kids.get(0).perform(e);
			else 
				this.kids.get(1).perform(e);
			
			break;
		default:
			break;
		}
	}
	public void perform(Ant e, int br)
	{
		switch (this.type) {
		case RIGHT:
			e.getAntPosition().moveRight();
			break;
		case LEFT:
			e.getAntPosition().moveLeft();
			break;
		case MOVE:
			if(!canPerform(e.getAntPosition()))
				break;
			e.getAntPosition().setXandY(e.getAntPosition().getX()+e.getAntPosition().getMoveX(),
                    					e.getAntPosition().getY()+e.getAntPosition().getMoveY());
			Population.positions.add(e.getAntPosition().clone());
			if(Population.matrix[e.getAntPosition().getX()]
								[e.getAntPosition().getY()] == '1')
			{
					//e.addFitness();
					//System.out.println("found food !!!!");
			}
			break;
		case PROG2:
			this.kids.get(0).perform(e,1);
			this.kids.get(1).perform(e,1);
			break;
		case PROG3:
			this.kids.get(0).perform(e,1);
			this.kids.get(1).perform(e,1);
			this.kids.get(2).perform(e,1);
			break;
		case IFFOODAHEAD:
			if(this.canPerform(e.getAntPosition()))
				this.kids.get(0).perform(e,1);
			else 
				this.kids.get(1).perform(e,1);
			
			break;
		default:
			break;
		}
	}
	public void visitKids(Ant A)
	{
		for(Node kid : kids)
		{
			kid.perform(A);
		}
		return;
	}
	@Override
	public Node clone()
	{
		return new Node(name,type,kidsSize);
	}
	
}
