package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.optjava.rng;
import java.util.*;

public class RNGRandomImpl implements IRNG {
	private Random rand;
	public RNGRandomImpl(){
		this.rand = new Random();
	}
}