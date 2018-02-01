package hr.fer.zemris.optjava.dz13.genetic.node;

import hr.fer.zemris.optjava.dz13.genetic.ant.*;

public interface INode {
	
	public boolean canPerform(AntPosition position);
	public void perform(AntPosition position);
}
