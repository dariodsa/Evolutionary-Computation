package hr.fer.zemris.optjava.dz11.thread;

import java.util.Queue;

import hr.fer.zemris.optjava.dz11.gen.GASolution;
import hr.fer.zemris.optjava.dz11.gen.IGAEvaluator;

public class ThreadPool {
	
	private int n;
	private Queue<GASolution<int[]>> tasks;
	private Queue<GASolution<int[]>> results;
	
	private Thread[] workers;
	private IGAEvaluator<int[]> evaluator;
	
	public ThreadPool (int n, Queue<GASolution<int[]>> tasks, Queue<GASolution<int[]>> results, IGAEvaluator<int[]> evaluator)
	{
		this.n = n;
		this.tasks = tasks;
		this.results = results;
		this.evaluator = evaluator;
		workers = new Thread[n];
		for(int i=0;i<n;++i)
		{
			workers[i] = new Thread(new EvaluatorThread(tasks,results,evaluator));
			//workers[i].start();
		}
	}
	public void joinAll()
	{
		for(int i=0;i<n;++i)
		{
			try {
				workers[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void startAll()
	{
		workers = new Thread[n];
		for(int i=0;i<n;++i)
		{
			workers[i] = new Thread(new EvaluatorThread(tasks,results,evaluator));
			//workers[i].start();
		}
		for(int i=0;i<n;++i)
		{
			try{
				workers[i].start();
			}
			catch(Exception e)
			{}
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
