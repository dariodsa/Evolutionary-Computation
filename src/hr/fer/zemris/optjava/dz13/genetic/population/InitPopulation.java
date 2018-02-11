package hr.fer.zemris.optjava.dz13.genetic.population;

import hr.fer.zemris.optjava.dz13.genetic.ant.Ant;
import hr.fer.zemris.optjava.dz13.genetic.node.Node;

import java.util.*;


public class InitPopulation {

	private static Random random = new Random();
	//private static double[] prob = {0.0866, 0.0566, 0.05666, 0.2666, 0.25, 0.27666};
	private static double[] prob = {0.16666, 0.16666, 0.16666, 0.16666, 0.16666, 0.16666};
	public static void initPopulation(List<Ant> population, int populationSize, List<Node> possibleNodes, int MAX_NUM_OF_NODES)
	{
		int first = getNode();  
		
		for(int i=0;i<populationSize;++i)
		{
			first = getNode();
			Ant A = new Ant();
			System.out.println(i);
			Queue<Node> Q = new LinkedList<>();
			Node initNode = possibleNodes.get(first).clone();
			A.setInitNode(initNode);
			A.addNode(initNode);
			Q.add(initNode);
			int sumNode = 1;
			while(!Q.isEmpty())
			{
				Node N = Q.poll();
				//System.out.println(Q.size()+ " " + N.getType());
				for(int j=0;j<N.getKidsSize();++j)
				{
					int idNode = getNode();
					if(sumNode > MAX_NUM_OF_NODES-65)
						idNode = random.nextInt(3);
					Node newNode = possibleNodes.get(idNode).clone();
					N.addKid(newNode);
					A.addNode(newNode);
					Q.add(newNode);
					++sumNode;
				}
			}
			System.out.printf("%d. %d %d%n",i+1,sumNode,A.getNodes().size());
			population.add(A);
		}
		
	}
	public static Node getSubTreeSize(int numOfNodes, List<Node> possibleNodes)
	{
		int first = getNode();
		Node initNode = possibleNodes.get(first).clone();
		Queue<Node> Q = new LinkedList<>();
		Q.add(initNode);
		int sumNode = 1;
		while(!Q.isEmpty())
		{
			Node N = Q.poll();
			//System.out.println(Q.size()+ " " + N.getType());
			for(int j=0;j<N.getKidsSize();++j)
			{
				int idNode = getNode();
				if(sumNode > numOfNodes)
					idNode = random.nextInt(3);
				Node newNode = possibleNodes.get(idNode).clone();
				N.addKid(newNode);
				Q.add(newNode);
				++sumNode;
			}
		}
		return initNode;
	}
	private static int getNode()
	{
		double rnd = random.nextDouble();
		double sum = 0;
		for(int i=0;i<prob.length;++i)
		{
			sum += prob[i];
			if(rnd <= sum)
				return i;
		}
		return 1;
	}
	
}
