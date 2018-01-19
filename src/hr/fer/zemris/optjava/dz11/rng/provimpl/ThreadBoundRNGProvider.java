package hr.fer.zemris.optjava.dz11.rng.provimpl;

import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rng.IRNGProvider;

public class ThreadBoundRNGProvider implements IRNGProvider{

	@Override
	public IRNG getRNG() {
		Thread current = Thread.currentThread();
		IRNGProvider threadProvider = (IRNGProvider) current;
		
		return threadProvider.getRNG();
	}
	
}