package hr.fer.zemris.optjava.dz11.rng.provimpl;

import hr.fer.zemris.optjava.dz11.rng.IRNG;
import hr.fer.zemris.optjava.dz11.rng.IRNGProvider;
import hr.fer.zemris.optjava.dz11.rng.rngimpl.RNGRandomImpl;

public class ThreadLocalRNGProvider implements IRNGProvider{

	private ThreadLocal<IRNG> threadLocal = new ThreadLocal<>();
	
	@Override
	public IRNG getRNG() {
		IRNG irng = threadLocal.get();
		if( irng == null)
		{
			irng = new RNGRandomImpl();
			threadLocal.set(irng);
		}
		return irng;
	}
	
}