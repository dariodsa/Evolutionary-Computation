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

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) 
	{
		boolean everythingIsOK = true;
		for(DomainFunction D : domainFunctions)
		{
			if(D.isTrue(solution)==false)
			{
				everythingIsOK = false;
				break;
			}
		}
		if(everythingIsOK)
		{
			int br=0;
			for(Function<Vector,Double> F : functions)
			{
				objectives[br++] = F.apply(new Vector(solution));
			}
		}
		else
		{
			//everything is not OK
		}
	}

}
