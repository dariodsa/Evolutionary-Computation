package hr.fer.zemris.optjava.dz11.thread;

import java.util.List;
import java.util.Queue;

import hr.fer.zemris.optjava.dz11.Task;
import hr.fer.zemris.optjava.dz11.gen.GASolution;
import hr.fer.zemris.optjava.dz11.gen.IGAEvaluator;

public class ThreadPool2 
{
	private int n;
	private Queue<Task> tasks;
	private Queue<GASolution<int[]>[]> results;
	private IGAEvaluator<int[]> evaluator;
	
	private Thread[] worker;
	public ThreadPool2(int n, Queue<Task> tasks, Queue<GASolution<int[]>[]> results,IGAEvaluator<int[]> evaluator)
	{
		this.n = n;
		this.tasks = tasks;
		this.results = results;
		this.evaluator = evaluator;
		
		this.worker = new Thread[n];
		for(int i=0;i<n;++i)
		{
			worker[i] = new Thread( new CreateChildTask(tasks, results, evaluator)
						
					);
			worker[i].start();
		}
	}
	public void joinAll()
	{
		for(int i=0;i<n;++i)
		{
			try {
				worker[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void startAll()
	{
		for(int i=0;i<n;++i)
		{
			worker[i].start();
		}
	}
	public void killAll()
	{
	}
	static class CreateChildTask implements Runnable
	{
		
		private IGAEvaluator<int[]> evaluator;
		private Queue<GASolution<int[]>[]> results;
		private Queue<Task> tasks;
		private List<GASolution<int[]>> population;
		
		public CreateChildTask(Queue<Task> tasks,
				Queue<GASolution<int[]>[]> results, IGAEvaluator<int[]> evaluator)
		{
			this.tasks = tasks;
			this.results = results;
			this.evaluator = evaluator;
		}
		@Override
		public void run() 
		{
			while(true)
			{
				Task task = tasks.peek();
				tasks.poll();
				if(task.getBr()==-1)break;
				
				population = task.getPopulation();
				GASolution<int[]> solution = new GASolution<int[]>;
				
			}
		}
	}
}
