package hr.fer.zemris.optjava.dz11.rng;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class RNG {
	private static IRNGProvider rngProvider;
	static {
		// Stvorite primjerak razreda Properties;
		// Nad Classloaderom razreda RNG tražite InputStream prema resursu rng-config.properties
		// recite stvorenom objektu razreda Properties da se učita podatcima iz tog streama.
		// Dohvatite ime razreda pridruženo ključu "rng-provider"; zatražite Classloader razreda
		// RNG da učita razred takvog imena i nad dobivenim razredom pozovite metodu newInstance()
		// kako biste dobili jedan primjerak tog razreda; castajte ga u IRNGProvider i zapamtite.
		Properties properties = new Properties();
		ClassLoader loader = RNG.class.getClassLoader();
		try {
			InputStream is = loader.getResourceAsStream("rng-config.properties");
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 String className = properties.getProperty("rng-provider");

	        try {
	            rngProvider = (IRNGProvider)loader.loadClass(className).newInstance();
	        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
		
		
	}
	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}