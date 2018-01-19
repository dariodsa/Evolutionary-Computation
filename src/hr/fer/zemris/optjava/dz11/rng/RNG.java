package hr.fer.zemris.optjava.dz11.rng;

import java.util.*;

public class RNG {
	private static IRNGProvider rngProvider;
	static {

	}
	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}