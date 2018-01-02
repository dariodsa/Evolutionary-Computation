package hr.fer.zemris.optjava.dz9;

public interface MOOPProblem {
	int getNumberOfObjectives();
	double[] evaluateSolution(double[] solution) throws Exception;
}
