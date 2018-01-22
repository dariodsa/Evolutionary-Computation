package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.optjava.dz11.art.GrayScaleImage;
import hr.fer.zemris.optjava.dz11.gen.Evaluator;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pokretac1 {
	private final int NUM_OF_WORKERS = Runtime.getRuntime().availableProcessors();
	
	private static Path  originalPath;
	private static int numOfRectangles;
	private static int populationSize;
	private static int maxIter;
	private static int minFitness;
	private static Path txtPath;
	private static Path resultPath;
	
	public static void main(String[] args) throws IOException{
		//putanja do originalne png datoteke
		//broj kvadrata kojim se slika aproksimira
		//velicina populacija
		//maxiter
		//minimalna fitness ( definiran kao -kazna) uz koji algoritam moze stati i prije
		//stazu do txt datoteke u koju ce biti ispisani optimalni parametri ( jedan broj po retku)
		//stazu do slike koju ce program generirati i koja prikazuje pronađenu aproksimaciju
		if( args.length != 7) {
			System.out.println("Expected 7 arguments.");
			System.exit(0);
		}
		originalPath = Paths.get(args[0]);
		numOfRectangles = Integer.parseInt(args[1]);
		populationSize = Integer.parseInt(args[2]);
		maxIter = Integer.parseInt(args[3]);
		minFitness  = Integer.parseInt(args[4]);
		txtPath = Paths.get(args[5]);
		resultPath = Paths.get(args[6]);
		
		GrayScaleImage img = GrayScaleImage.load(originalPath.toFile());
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		Evaluator evaluator = new Evaluator(img);
		
		if(!Files.exists(resultPath)) {
			Files.createFile(resultPath);
		}
		GrayScaleImage resultImage = evaluator.draw(p, im);
		resultImage.save(resultPath.toFile());
		
		if(!Files.exists(txtPath)) {
			Files.createFile(txtPath);
		}
		
		FileWriter fw = new FileWriter(txtPath.toFile());
		//for(int data : )
	}
}
