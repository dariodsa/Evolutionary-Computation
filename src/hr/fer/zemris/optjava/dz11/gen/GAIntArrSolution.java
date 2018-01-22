package hr.fer.zemris.optjava.dz11.gen;

public class GAIntArrSolution extends GASolution<int[]>{

	@Override
	public GASolution<int[]> duplicate() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setData(int[] data){
		this.data = new int[data.length];
		for(int i=0;i<data.length;++i)
		{
			this.data[i] = data[i];
		}
	}
}
