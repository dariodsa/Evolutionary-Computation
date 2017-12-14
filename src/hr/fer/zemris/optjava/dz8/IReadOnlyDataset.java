package hr.fer.zemris.optjava.dz8;

import java.nio.file.*;
import java.io.*;
import java.util.*;

public class IReadOnlyDataset {
	List<Double> list;
	public IReadOnlyDataset(String path, int num) throws IOException
	{
		list=new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get(path,""));
		int br=0;
		for(String line : lines)
		{
			if(br==num)break;
			String number="";
			for(int i=0,len=line.length();i<len;++i)
			{
				if(line.charAt(i)>='0' && line.charAt(i)<='9')
					number+=line.charAt(i);
			}
			
			list.add(Double.parseDouble(number));
			++br;
		}
		double min=Double.MAX_VALUE;
		double max=Double.MIN_VALUE;
		for(Double number : list)
		{
			if(max<number)
				max=number;
			if(min>number)
				min=number;
		}
		
		for(int i=0;i<list.size();++i)
		{
			double number=(double)Math.abs(list.get(i)-min)/(double)Math.abs(max-min)*2.0-1.0;
			list.set(i, number);
		}
		//for(int i=0;i<10;++i)System.out.println(list.get(i));
	}
	public int numberOfSamples()
	{
		return this.list.size();
	}
	public int numberOfOutputs()
	{
		return 1;
	}
	public double getOutput(int pos)
	{
		return this.list.get(pos);
	}
	
}
