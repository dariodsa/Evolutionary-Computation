package hr.fer.zemris.optjava.dz10;
import java.util.*;
import java.util.function.*;
public class MOOP {

	public static void main(String[] args) {
		//fja ( 1 ili 2)
		//n velicina populacije
		//maxiter
		
		int problemType    = Integer.parseInt(args[0]);
		int populationSize = Integer.parseInt(args[1]);
		int maxiter        = Integer.parseInt(args[2]);
		
		List<Function<Vector,Double>> functions2 = new ArrayList<>();
		functions2.add(x -> x.get(0) * x.get(0));
		functions2.add(x -> x.get(1) * x.get(1));
		functions2.add(x -> x.get(2) * x.get(2));
		functions2.add(x -> x.get(3) * x.get(3));
		
		List<DomainFunction> domainFunctions2 = new ArrayList<>();
		domainFunctions2.add(new DomainFunction(x -> -5 < x.get(0) && x.get(0) < 5));
		domainFunctions2.add(new DomainFunction(x -> -5 < x.get(1) && x.get(1) < 5));
		domainFunctions2.add(new DomainFunction(x -> -5 < x.get(2) && x.get(2) < 5));
		domainFunctions2.add(new DomainFunction(x -> -5 < x.get(3) && x.get(3) < 5));
		
		
		MOOPSolution mo2= new MOOPSolution(
					functions2,
					4,
					domainFunctions2);
		
		List<Function<Vector,Double>> functions1 = new ArrayList<>();
		functions1.add(x -> x.get(0));
		functions1.add(x -> (1+x.get(1))/(x.get(0)));
		
		List<DomainFunction> domainFunctions1 = new ArrayList<>();
		domainFunctions1.add(new DomainFunction(x -> 0.1 < x.get(0) && x.get(0) < 1));
		domainFunctions1.add(new DomainFunction(x -> 0 < x.get(1) && x.get(1) < 5));
		
		MOOPSolution mo1= new MOOPSolution(
				functions1,
				2,
				domainFunctions1);
		
		try{
			Population P = new Population(populationSize,
										  maxiter,
										  problemType==1?mo1:mo2);
			P.run();
		}
		catch(Exception e)
		{
			System.out.println("Variables are not in the valid domain.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
