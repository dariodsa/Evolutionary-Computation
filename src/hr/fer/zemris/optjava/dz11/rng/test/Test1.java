package hr.fer.zemris.optjava.dz11.test;

import hr.fer.zemirs.optjava.dz11.rng.*;

public class Test1 {
	public static void main(String[] args) {
		IRNG rng = RNG.getRNG();
		
		for(int i=0;i<20;++i) {
			System.out.println(rng.nextInt(-5,5));
		}
	}
}