package hr.fer.zemris.optjava.dz7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class IReadOnlyDataset {
	
	public static IReadOnlyDataset LoadData(String filePath) throws IOException
	{
		 List<String> lines=Files.readAllLines(Paths.get(filePath, ""));
		 for(String line : lines)
		 {
			 String[] twoPart =line.split("):(");
			 
			 twoPart[0]=removeChar(twoPart[0], '(');
			 twoPart[1]=removeChar(twoPart[1], ')');
			 
			 String[] inputVector  = twoPart[0].split(",");
			 String[] resultVector = twoPart[1].split(",");
			 
			 
			 
		 }
	}
	public int numberOfSamples()
	{
		
	}
	public int numberOfInputs()
	{}
	public int numberOfOutputs()
	{}
	public Object getSample(int pos)
	{
		
	}
	private static String removeChar(String S,char removeChr)
	{
		String ans="";
		for(int i=0,len=S.length();i<len;++i)
		{
			if(S.charAt(i)!=removeChr)
				ans+=S.charAt(i);
		}
		return ans;
	}
	
}
