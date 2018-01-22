package hr.fer.zemris.optjava.dz11.thread;

import java.util.Queue;

import hr.fer.zemris.optjava.dz11.gen.GASolution;
import hr.fer.zemris.optjava.dz11.gen.IGAEvaluator;

public class ThreadPool {
	
	private int n;
	private Queue<GASolution<int[]>> tasks;
	private Queue<GASolution<int[]>> results;
	
	private Thread[] workers;
	
	public ThreadPool (int n, Queue<GASolution<int[]>> tasks, Queue<GASolution<int[]>> results, IGAEvaluator<int[]> evaluator)
	{
		this.n = n;
		this.tasks = tasks;
		this.results = results;
		
		workers = new Thread[n];
		for(int i=0;i<n;++i)
		{
			workers[i] = new Thread(new EvaluatorThread(tasks,results,evaluator));
			workers[i].start();
		}
	}
	public void killAll()
	{
		for(int i=0;i<n;++i)
		{
			//workers[i].destroy();
		}
	}
}
