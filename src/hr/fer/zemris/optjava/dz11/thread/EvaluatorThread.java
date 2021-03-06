package hr.fer.zemris.optjava.dz11.thread;

import hr.fer.zemris.optjava.dz11.gen.GAIntArrSolution;
import hr.fer.zemris.optjava.dz11.gen.GASolution;
import hr.fer.zemris.optjava.dz11.gen.IGAEvaluator;

import java.util.Queue;

public class EvaluatorThread implements Runnable{

	private Queue<GASolution<int[]>> tasks;
	private Queue<GASolution<int[]>> results;
	private IGAEvaluator<int[]> evaluator;
	
	public EvaluatorThread(Queue<GASolution<int[]>> tasks, Queue<GASolution<int[]>> results, IGAEvaluator<int[]> evaluator)
	{
		this.tasks = tasks;
		this.results = results;
		this.evaluator = evaluator;
	}
	@Override
	public void run() {
		while(true)
		{
			try
			{
				GASolution<int[]> solution = tasks.peek();
				tasks.poll();
				if(solution == null)
				{
					break;
				}
				if(solution.fitness==-1)
					break;
				//System.out.println("nasao");
				
				evaluator.evaluate(solution);
				//System.out.println("dodajem");
				results.add(solution);
			}
			catch(Exception ex)
			{
				//System.err.println(ex.getMessage());
				//ex.printStackTrace();
				break;
				
			}
		}
		
	}

}
