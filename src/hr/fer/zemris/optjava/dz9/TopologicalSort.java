package hr.fer.zemris.optjava.dz9;

import java.util.*;

public class TopologicalSort 
{
	private static int num[];
	private static List<List<Integer>> V = new ArrayList<>();
	public static List<List<Vector>> runTopologicalSort(double[][] results, List<Vector>  population)
	{
		List<List<Vector>> solution = new ArrayList<>();
		num = new int[population.size()];
		for(int i=0;i<population.size();++i)
		{
			V.add(new ArrayList<>()); 
		}
		for(int i=0;i<population.size();++i)
		{
			for(int j=i+1;j<population.size();++j)
			{
				int cmp = compare(results[i],results[j]);
				if(cmp == 0)continue;
				else if(cmp == 1)
				{
					V.get(i).add(j);
					num[j] ++;
				}
				else if(cmp == -1)
				{
					V.get(j).add(i);
					num[i] ++;
				}
			}
		}
		Queue<Integer>Q = new LinkedList<>();
		Queue<Integer>numFrontQ = new LinkedList<>();
		for(int i=0;i<population.size();++i)
			if(num[i]==0)
			{
				Q.add(i);
				numFrontQ.add(0);
			}
		int number = 0;
		while(!Q.isEmpty())
		{
			int pos = Q.peek();
			int numFront = numFrontQ.peek();
			
			Q.poll();
			numFrontQ.poll();
			
			if(solution.size()==numFront)
			{
				solution.add(new ArrayList<Vector>());
			}
			solution.get(numFront).add(population.get(pos));
			
			++number;
			for(int i=0;i<V.get(pos).size();++i)
			{
				int node = V.get(pos).get(i);
				if(--num[node]==0)
				{
					Q.add(node);
					numFrontQ.add(numFront+1);
				}
			}
		}
		
		return solution;
	}
	private static int compare(double[] V1,double[] V2)
	{
		boolean better = true;
		for(int i=0;i<V1.length;++i)
		{
			if(V1[i]<V2[i])
			{
				better = false;
				break;
			}
		}
		if(better)
			return -1;
		boolean worse = true;
		for(int i=0;i<V1.length;++i)
		{
			if(V1[i]>V2[i])
			{
				worse = false;
				break;
			}
		}
		if(worse)
			return 1;
		return 0;
	}
}
