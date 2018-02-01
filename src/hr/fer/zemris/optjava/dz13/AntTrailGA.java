package hr.fer.zemris.optjava.dz13;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.optjava.dz13.graphics.Window;

import javax.swing.SwingUtilities;

public class AntTrailGA {

	private static Path mapPath;
	private static int maxGeneration;
	private static int populationSize;
	private static int minFitness;
	private static Path resultPath;

	public static void main(String[] args) throws IOException 
	{
		
		mapPath = Paths.get(args[0]);
		maxGeneration = Integer.parseInt(args[1]);
		populationSize= Integer.parseInt(args[2]);
		minFitness    = Integer.parseInt(args[3]);
		resultPath    = Paths.get(args[4]);
		
		char[][] matrix = parseMatrix(mapPath);
		
		int rows = matrix.length;
		int cols = matrix[0].length;
		
		Generation generation = new Generation(matrix, maxGeneration, populationSize, minFitness);
		generation.run();
		try {
			SwingUtilities.invokeAndWait(
					()->
					{
						Window window = new Window(600, 500, rows, cols);
						window.initGUI();
						window.getGamePanel().update(matrix);
					});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static char[][] parseMatrix(Path mapPath) throws IOException
	{
		List<String> lines = Files.readAllLines(mapPath);
		int height = Integer.parseInt(lines.get(0).split("x")[0]);
		int width = Integer.parseInt(lines.get(0).split("x")[1]);
		
		char[][] matrix = new char[height][width];
		for(int row = 0;row < lines.size()-1; ++row)
		{
			String line = lines.get(row+1);
			for(int col = 0; col < line.length(); ++col)
			{
				matrix[row][col] = line.charAt(col);
				System.out.printf("%c",matrix[row][col]);
			}
			System.out.printf("%n");
		}
		return matrix;
	}
}
