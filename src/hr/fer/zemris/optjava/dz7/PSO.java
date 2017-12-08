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
	private double[][] pbest;
	
	
	private final double C1 = 2;
	private final double C2 = 2;
	
	private final double VMAX = 0.4;
	
	private FFANN network;
	private IReadOnlyDataset dataset;
	private int DIM;
	public PSO(FFANN network,int populationSize,int maxIter,double meer,int neighbourSize) throws IOException
	{
		this.network=network;
		this.populationSize=populationSize;
		this.maxIter=maxIter;
		this.meer=meer;
		this.neighbourSize=neighbourSize;
		
		
		
		f=new double[populationSize];
		pbest_f=new double[populationSize];
		for(int i=0;i<populationSize;++i)pbest_f[i]=898989;
		start();
	}
	private void start()
	{
		
		
		DIM=network.getWeightsCount();
		
		int brojCvorova=network.getWeightsCount();
		x=new double[populationSize][DIM];
		v=new double[populationSize][DIM];
		pbest=new double[populationSize][DIM];
		
		for(int i=0;i<populationSize;++i)
		{
			for(int d=0;d<DIM;++d)
			{
				x[i][d] = random(-1,1);
				v[i][d] = random(-0.4,0.4);
			}
		}
		
				
		while(maxIter-->=0)
		{
			for(int i=0;i<populationSize;++i)
			{
				double[] weights = new double[network.getWeightsCount()];
				int cnt=0;
				
				for(int k=0;k<DIM;++k)
					weights[cnt++]=x[i][k];
				double error=0;
				
				for(int k=0,len=dataset.numberOfSamples();k<len;++k)
				{
					double[] input=dataset.getSample(k);
					double[] output=new double[1];
					output=network.calcOutputs(input, weights);
					
					double[] realResult=dataset.getOutput(k);
					for(int j=0,len2=realResult.length;j<len2;++j)
					{
						error+=(output[j]-realResult[j])*(output[j]-realResult[j]);
						//System.out.println(output[j]+","+realResult[j]);
					}
					
				}
				f[i]=error/dataset.numberOfSamples();
				//System.out.println("Error: "+f[i]);
			}
			for(int i=0;i<populationSize;++i)
			{
				if(f[i]<pbest_f[i])
				{
					pbest_f[i]=f[i];
					//pbest[i]=x[i];
					pbest[i]=Arrays.copyOf(x[i], x[i].length);
					
				}
			
				double[] bestNeighbour=new double[DIM];
				
				int poz=0;
				double bestLocalVal=87987987987.0;
				for(int k=-neighbourSize;k<=neighbourSize;++k)
				{
					if(k<0)poz=populationSize+k;
					else if(k>=populationSize)poz=neighbourSize-populationSize;
					if(f[poz]<bestLocalVal)
					{
						bestLocalVal=f[poz];
						bestNeighbour=x[poz];
					}
				}
			
				for(int d=0;d<DIM;++d)
				{
						v[i][d]+=C1*(new Random().nextDouble())*(pbest[i][d]-x[i][d]);
						v[i][d]+=C2*(new Random().nextDouble())*(bestNeighbour[d]-x[i][d]);
						if(v[i][d]<-VMAX)v[i][d]=-VMAX;
						if(v[i][d]>VMAX)v[i][d]=VMAX;
						x[i][d]+=v[i][d];
				}
				
			}
			double mini=f[0];
			for(int j=1;j<populationSize;++j)
			{
				mini=Math.min(mini,f[j]);
			}
			System.out.println("Error: ("+maxIter+","+mini+")");
			
		}
		
	}
	private double random(double x,double y)
	{
		return new Random().nextDouble()*(y-x)+x;
	}
	
}
