package hr.fer.zemris.optjava.dz7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class IReadOnlyDataset {
	
	private double[][] inputs;
	private double[][] outputs;
	private int numOfInputs;
	private int numOfOutputs;
	private int numOfSamples;
	
	public IReadOnlyDataset(String filePath) throws IOException
	{
		IReadOnlyDataset ans = null;
		List<String> lines=Files.readAllLines(Paths.get(filePath, ""));
		int br=0;
		
		for(String line : lines)
		{
			String[] twoPart =line.split("):(");
			 
			twoPart[0]=removeChar(twoPart[0], '(');
			twoPart[1]=removeChar(twoPart[1], ')');
			 
			String[] inputVector  = twoPart[0].split(",");
			String[] outputVector = twoPart[1].split(",");
			 
			for(int i=0;i<inputVector.length;++i)
				inputs[br][i]=Double.parseDouble(inputVector[i]);
			
			for(int i=0;i<outputVector.length;++i)
				outputs[br][i]=Double.parseDouble(outputVector[i]);
			
			if(br==0)
			{
				setNumOfInputs(inputVector.length);
				setNumOfOutputs(outputVector.length);
			}
			++br;
		}
		
	}
	public int numberOfSamples()
	{
		return numOfSamples;
	}
	private void setNumOfInputs(int numOfInputs)
	{
		this.numOfInputs=numOfInputs;
	}
	private void setNumOfOutputs(int numOfOutputs)
	{
		this.numOfOutputs=numOfOutputs;
	}
	public int numberOfInputs()
	{
		return numOfInputs;
	}
	public int numberOfOutputs()
	{
		return numOfOutputs;
	}
	public double[] getSample(int pos)
	{
		double[] ans=new double[numOfInputs];
		for(int i=0;i<numOfInputs;++i)
			ans[i]=inputs[pos][i];
		return ans;
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
