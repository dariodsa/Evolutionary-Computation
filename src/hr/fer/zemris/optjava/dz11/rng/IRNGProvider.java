package hr.fer.zemris.optjava.dz11.rng;

public interface IRNGProvider {
	
	/**
	* Metoda za dohvat generatora slučajnih brojeva koji pripada
	* ovom objektu.
	*
	* @return generator slučajnih brojeva
	*/
	public IRNG getRNG();
}