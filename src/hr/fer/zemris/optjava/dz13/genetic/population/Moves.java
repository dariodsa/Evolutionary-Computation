package hr.fer.zemris.optjava.dz13.genetic.population;

import java.util.List;

import hr.fer.zemris.optjava.dz13.genetic.Population;
import hr.fer.zemris.optjava.dz13.genetic.ant.Ant;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;

public class Moves {

	public static void addMoves(List<Node> possibleNodes)
	{
		possibleNodes.add(new Node("MOVE",
			      (e)->{return new Boolean(true);},
			      0)
		{
			@Override
			public void perform(Ant e) {
				e.getAntPosition().setXandY(e.getAntPosition().getX()+e.getAntPosition().getMoveX(),
						                    e.getAntPosition().getY()+e.getAntPosition().getMoveY());
				if(Population.matrix[e.getAntPosition().getX()]
						            [e.getAntPosition().getY()] == '1')
					e.addFitness();
					//found food !!!!
			}
			
		});
		possibleNodes.add(new Node("LEFT",
			      (e)->{return new Boolean(true);},
			      0)
		{
			@Override
			public void perform(Ant e) {
				e.getAntPosition().moveLeft();
			}
			
		});
		possibleNodes.add(new Node("RIGHT",
			      (e)->{return new Boolean(true);},
			      0)
		{
			@Override
			public void perform(Ant e) {
				e.getAntPosition().moveRight();
			}
			
		});
		possibleNodes.add(new Node("PROG2",
			      (e)->{return new Boolean(true);},
			      2)
		{
			@Override
			public void perform(Ant e) {
				this.visitKids(e);
			}
			
		});
		possibleNodes.add(new Node("PROG3",
			      (e)->{return new Boolean(true);},
			      3)
		{
			@Override
			public void perform(Ant e) {
				this.visitKids(e);
			}
			
		});
		possibleNodes.add(new Node("IfFoodAhead",
			      (e)->{
			    	  return Population.matrix[e.getX()+e.getMoveX()][e.getY()+e.getMoveY()] == '1';
			      },
			      2)
		{
			@Override
			public void perform(Ant e) {
				if(this.canPerform(e.getAntPosition()))
					this.kids.get(0).perform(e);
				else 
					this.kids.get(1).perform(e);
			}
			
		});
	}

}
