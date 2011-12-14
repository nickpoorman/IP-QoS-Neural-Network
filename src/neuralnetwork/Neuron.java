package neuralnetwork;

import java.util.ArrayList;

public class Neuron {

	private double state;
	private ArrayList<Double> inputs;

	// private ArrayList<Weight> weights;

	public Neuron() {
		this.inputs = new ArrayList<Double>();
		// this.weights = new ArrayList<Weight>();

	}

	public void takeInput(double input) {
		this.inputs.add(input);
	}

	/**
	 * This method does the state change on the atomic model (Neuron) It
	 * calculates based on the inputs
	 */
	public void delta() {
		int tmpState = 0;
		for (Double d : inputs) {
			tmpState += d.doubleValue();
		}
		this.state = tmpState;

		// maybe clear the inputs here?
	}

	/**
	 * This method outputs the state for this atomic model (Neuron)
	 */
	public double lambda() {
		return this.state;
	}

	/**
	 * Clears the inputs used, this should be used when the network is re-run
	 */
	public void clear() {
		this.inputs.clear();
	}
}
