package hr.fer.zemris.optjava.dz13.genetic.population;

import java.util.List;

import hr.fer.zemris.optjava.dz13.genetic.Population;
import hr.fer.zemris.optjava.dz13.genetic.ant.Ant;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;
import hr.fer.zemris.optjava.dz13.genetic.node.NodeType;

public class Moves {

	public static void addMoves(List<Node> possibleNodes)
	{
		possibleNodes.add(new Node("MOVE",
					NodeType.MOVE,
			      0));
		possibleNodes.add(new Node("LEFT",
				  NodeType.LEFT,
			      0));
		
		possibleNodes.add(new Node("RIGHT",
				   NodeType.RIGHT,
			      0));
		
		possibleNodes.add(new Node("PROG2",
				   NodeType.PROG2,
			      2));
		
		possibleNodes.add(new Node("PROG3",
				   NodeType.PROG3,
			      3));
		
		possibleNodes.add(new Node("IfFoodAhead",
				   NodeType.IFFOODAHEAD,
			      2));
	}

}
