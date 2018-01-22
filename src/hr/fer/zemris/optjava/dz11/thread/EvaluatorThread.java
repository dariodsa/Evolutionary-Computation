package hr.fer.zemris.optjava.dz11.thread;

import hr.fer.zemris.optjava.dz11.gen.GAIntArrSolution;
import hr.fer.zemris.optjava.dz11.gen.GASolution;
import hr.fer.zemris.optjava.dz11.gen.IGAEvaluator;

import java.util.Queue;

public class EvaluatorThread implements Runnable{

	private Queue<GASolution<int[]>> tasks;
	private Queue<GASolution<int[]>> results;
	private IGAEvaluator<int[]> evaluator;
	
	public EvaluatorThread(Queue<GASolution<int[]>> tasks, Queue<GASolution<int[]>> resuls, IGAEvaluator<int[]> evaluator)
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
				if(solution == null)
				{
					break;
				}
				evaluator.evaluate(solution);
				results.add(solution);
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		
	}

}
