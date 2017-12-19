package hr.fer.zemris.optjava.dz9;
import java.util.*;
import java.util.function.*;
public class MOOP {

	public static void main(String[] args) {
		//fja ( 1 ili 2)
		//n velicina populacije
		//vrsta prostora  decision-space || objective-space
		//maxiter
		
		/*int problemType = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);
		int maxiter = Integer.parseInt(args[3]);*/
		
		List<Function<Vector,Double>> functions = new ArrayList<>();
		functions.add(x -> x.get(0) * x.get(0));
		functions.add(x -> x.get(1) * x.get(1));
		functions.add(x -> x.get(2) * x.get(2));
		functions.add(x -> x.get(3) * x.get(3));
		
		List<DomainFunction> domainFunctions = new ArrayList<>();
		domainFunctions.add(new DomainFunction(x -> -5 < x.get(0) && x.get(0) < 5));
		domainFunctions.add(new DomainFunction(x -> -5 < x.get(1) && x.get(1) < 5));
		domainFunctions.add(new DomainFunction(x -> -5 < x.get(2) && x.get(2) < 5));
		domainFunctions.add(new DomainFunction(x -> -5 < x.get(3) && x.get(3) < 5));
		
		
		MOOPSolution mo= new MOOPSolution(
					functions,
					2,
					domainFunctions);
		
		
	}

}
