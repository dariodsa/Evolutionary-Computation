package hr.fer.zemris.optjava.dz13.genetic.node;

import hr.fer.zemris.optjava.dz13.genetic.Population;
import hr.fer.zemris.optjava.dz13.genetic.ant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Node implements INode, Cloneable
{
	private int kidsSize;
	private Node parent;
	private String name;
	private NodeType type;
	/*private Function<AntPosition,Boolean> functionCan;
	private Function<Integer, Void> functionPerform;*/
	
	public List<Node>kids = new ArrayList<>();
	
	public Node(String name, NodeType type, int kidsSize)
	{
		this.name = name;
		this.type = type;
		this.parent = null;
		//this.functionCan = functionCan;
		//this.functionPerform = functionPerform;
		this.kidsSize = kidsSize;
		this.kids = new ArrayList<>(kidsSize);
	}
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	public Node getParent()
	{
		return this.parent;
	}
	public void addKid(Node kid)
	{
		kids.add(kid);
		kid.setParent(this);
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
			e.sumNodes++;
			break;
		case LEFT:
			e.getAntPosition().moveLeft();
			e.sumNodes++;
			break;
		case MOVE:
			e.sumNodes++;
			if(!canPerform(e.getAntPosition()))
				break;
			e.getAntPosition().setXandY( mod(e.getAntPosition().getX()+e.getAntPosition().getMoveX(),e.bio[0].length),
                    					 mod(e.getAntPosition().getY()+e.getAntPosition().getMoveY(),e.bio.length));
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
			e.sumNodes++;
			break;
		case LEFT:
			e.sumNodes++;
			e.getAntPosition().moveLeft();
			break;
		case MOVE:
			e.sumNodes++;
			
			if(!canPerform(e.getAntPosition()))
				break;
			e.getAntPosition().setXandY( mod(e.getAntPosition().getX()+e.getAntPosition().getMoveX(), e.bio[0].length),
                    					 mod(e.getAntPosition().getY()+e.getAntPosition().getMoveY()  , e.bio.length)  );
			Population.positions.add(e.getAntPosition().clone());
			try{
			if(Population.matrix[e.getAntPosition().getX()]
								[e.getAntPosition().getY()] == '1')
			{
					//e.addFitness();
					//System.out.println("found food !!!!");
			}}
			catch(Exception ex){
				System.out.println(ex.getMessage());
				System.out.printf("vel %d %d%n",e.bio[0].length,e.bio.length);
				System.out.printf("%d %d%n",e.getAntPosition().getX(),e.getAntPosition().getY());
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
	public String perform(Ant e, int br,int x)
	{
		switch (this.type) {
		case RIGHT:
			e.getAntPosition().moveRight();
			e.sumNodes++;
			return "Right";
		case LEFT:
			e.sumNodes++;
			e.getAntPosition().moveLeft();
			return "Left";
			
		case MOVE:
			e.sumNodes++;
			return "Move";
			
			
		case PROG2:
			String te="PROG2( ";
			te +=this.kids.get(0).perform(e,1,1);
			te +=", ";
			this.kids.get(1).perform(e,1,1);
			te +=")";
			return te;
			
		case PROG3:
			String tea="PROG2( ";
			tea += this.kids.get(0).perform(e,1,1);
			tea +=", ";
			tea += this.kids.get(1).perform(e,1,1);
			tea +=", ";
			tea += this.kids.get(2).perform(e,1,1);
			tea +=")";
			return tea;
		case IFFOODAHEAD:
			String teb="IfFoodAhead( ";
			teb += this.kids.get(0).perform(e,1,1);
			teb +=", ";
			teb += this.kids.get(1).perform(e,1,1);
			teb +=")";
			return teb;
		default:
			break;
		}
		return "";
	}
	private int mod(int x,int y) {
		
		/*if(x < 0)
		{
			while(x<0)
			{
				x+=y;
			}
		}
		else if(x>0)
		{
			while(x>=y)
			{
				x-=y;
			}
		}*/
		return x;
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
		Node kidClone = new Node(name,type,kidsSize);
		for(Node kid : kids)
		{
			kidClone.addKid(kid.clone());
		}
		return kidClone;
	}
	
}
