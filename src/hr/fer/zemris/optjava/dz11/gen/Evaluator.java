package hr.fer.zemris.optjava.dz11.gen;

import hr.fer.zemris.optjava.dz11.art.GrayScaleImage;

public class Evaluator implements IGAEvaluator<int[]> {
	private GrayScaleImage template;
	private GrayScaleImage im;
	public Evaluator(GrayScaleImage template) 
	{
		super();
		this.template = template;
		
	}
	public GrayScaleImage draw(GASolution<int[]> p, GrayScaleImage im) 
	{
		if(im==null) 
		{
			im = new GrayScaleImage(template.getWidth(), template.getHeight());
		}
		int[] pdata = p.getData();
		byte bgcol = (byte)pdata[0];
		im.clear(bgcol);
		int n = (pdata.length-1)/5;
		int index = 1;
		for(int i = 0; i < n; i++) 
		{
			im.rectangle(
					pdata[index], pdata[index+1], pdata[index+2], pdata[index+3],
					(byte)pdata[index+4]
			);
			index+=5;
		}
		return im;
	}
	@Override
	public void evaluate(GASolution<int[]> p) 
	{
		im = draw(p, null);
		byte[] data = im.getData();
		byte[] tdata = template.getData();
		int width = im.getWidth();
		int height = im.getHeight();
		
		double error = 0;
		int index = 0;
		for(int i=0;i<height;++i)
		{
			for(int j=0;j<width;++j)
			{
				/*if(Math.abs(((int) data[index] & 0xFF) - ((int) tdata[index] & 0xFF))==0)
				{
					System.out.println(((int) data[index] & 0xFF)+" "+((int) tdata[index] & 0xFF));
				}*/
				error+= (Math.abs(((int) data[index] & 0xFF) - ((int) tdata[index] & 0xFF)));
				
				++index;
			}
			
		}
		p.fitness = -error;
	}
}
