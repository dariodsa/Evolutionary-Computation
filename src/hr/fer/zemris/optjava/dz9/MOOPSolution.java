package hr.fer.zemris.optjava.dz9;
import java.util.*;
import java.util.function.*;

public class MOOPSolution implements MOOPProblem{

	private List<Function<Vector,Double>>functions;
	private List<DomainFunction>domainFunctions;
	private int numOfVariables;
	public MOOPSolution(List<Function<Vector,Double>> functions, int numOfVariables,List<DomainFunction> domainFunctions)
	{
		this.functions = new ArrayList<>();
		this.functions = functions;
		this.numOfVariables = numOfVariables;
		this.domainFunctions = domainFunctions;
	}
	@Override
	public int getNumberOfObjectives() {
		return this.domainFunctions.size();
	}
	public boolean isDomainOk(double[] solution)
	{
		for(DomainFunction D : domainFunctions)
		{
			if(D.isTrue(solution)==false)
			{
				return false;
			}
		}
		return true;
	}
	@Override
	public void evaluateSolution(double[] solution, double[] objectives) throws Exception
	{
		if(isDomainOk(solution)==false)
			throw new Exception("Domain is not OK.");
		int br=0;
		for(Function<Vector,Double> F : functions)
		{
			objectives[br++] = F.apply(new Vector(solution));
		}
		
	}
	public int getNumOfVariables()
	{
		return this.numOfVariables;
	}
}
