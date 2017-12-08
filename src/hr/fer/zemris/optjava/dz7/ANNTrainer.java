package hr.fer.zemris.optjava.dz7;


import java.io.IOException;

public class ANNTrainer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//file , alg ( oznaka algoritma koji se koristi), n size of population, merr ( prihvatljivo srednje odstupanje), maxiter (max broj epoha)
		/*if(args[1].compareTo("pso-a")==0)
		{
			
		}*/
		IReadOnlyDataset dataset=new IReadOnlyDataset("iris.data");
		
		
		FFANN network = new FFANN(new int[] {4,10,10,3},
				  new ITransferFunction[] {
				    new SigmoidTransferFunction(),
				    new SigmoidTransferFunction(),
					new SigmoidTransferFunction()
				  },
				  dataset);
		try {
			//PSO P= new PSO("iris.data", 20, 800, 0.1, 6);
			ClonAlg C=new ClonAlg(network, 20, 800, -0.4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
class SigmoidTransferFunction implements ITransferFunction
{

	public double function(double x) {
		
		return 1.0/(1+Math.exp(-x));
	}
	
}
