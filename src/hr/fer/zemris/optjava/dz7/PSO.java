package hr.fer.zemris.optjava.dz7;
import java.io.IOException;
import java.util.*;
public class PSO 
{
	private int maxIter;
	private double meer;
	private int populationSize;
	private int neighbourSize;
	
	private double[] f;
	private double[] pbest_f;
	private double[][] x;
	private double[][] v;
	
	private IReadOnlyDataset dataset;
	
	public PSO(String filePath,int populationSize,int maxIter,double meer,int neighbourSize) throws IOException
	{
		this.populationSize=populationSize;
		this.maxIter=maxIter;
		this.meer=meer;
		this.neighbourSize=neighbourSize;
		
		dataset=IReadOnlyDataset.LoadData(filePath);
		
		start();
	}
	private void start()
	{
		for(int i=0;i<populationSize;++i)
		{
			for(int d=0;d<DIM;++d)
			{
				x[i][d] = random(xmin[d],xmax[d]);
				v[i][d] = random(vmin[d],vmax[d]);
			}
		}
		
		while(maxIter-->=0)
		{
			
			for(int i=0;i<populationSize;++i)
				f[i]=funkcija(x[i]);
			
			for(int i=0;i<populationSize;++i)
			{
				if(f[i]<pbest_f[i])
				{
					pbest_f[i]=f[i];
					pbest[i]=x[i];
				}
			}
			
			
			for(int i=0;i<populationSize;++i)
			{
				for(int d=0;d<DIM;++d)
				{
					
				}
			}
		}
		
	}
	private double random(double x,double y)
	{
		return new Random().nextDouble()*(y-x)+x;
	}
}
