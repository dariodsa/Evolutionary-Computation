package hr.fer.zemris.optjava.dz7;

import java.io.IOException;

public class ANNTrainer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//file , alg ( oznaka algoritma koji se koristi), n size of population, merr ( prihvatljivo srednje odstupanje), maxiter (max broj epoha)
		/*if(args[1].compareTo("pso-a")==0)
		{
			
		}*/
		try {
			PSO P= new PSO("iris.data", 10, 600, 0.1, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
