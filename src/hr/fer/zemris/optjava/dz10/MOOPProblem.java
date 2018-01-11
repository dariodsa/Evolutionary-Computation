package hr.fer.zemris.optjava.dz10;

public interface MOOPProblem {
	int getNumberOfObjectives();
	double[] evaluateSolution(double[] solution) throws Exception;
}
