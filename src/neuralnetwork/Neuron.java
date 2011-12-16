package neuralnetwork;

import java.util.ArrayList;

import framework.Model;

public class Neuron extends Model<Double, Double> {

	private double state;
	private ArrayList<Double> inputs;
	private boolean inputLayer = false;
	private double error;

	public Neuron() {
		this.inputs = new ArrayList<Double>();
	}

	@Override
	public void takeInput(ArrayList<Double> input) {
		for (Double d : input) {
			this.inputs.add(d);
		}
	}

	/**
	 * This method does the state change on the atomic model (Neuron) It
	 * calculates based on the inputs
	 */
	@Override
	public void delta() {
		double tmpState = 0;
		for (Double d : inputs) {
			tmpState += d.doubleValue();
		}
		if (NeuralNetwork.DEBUG)
			System.out.println("Setting neuron state to: " + tmpState);
		this.state = tmpState;

		// maybe clear the inputs here?
	}

	/**
	 * This method outputs the state for this atomic model (Neuron)
	 */
	@Override
	public ArrayList<Double> lambda() {
		ArrayList<Double> tmp = new ArrayList<Double>();
		if (inputLayer) {
			if (NeuralNetwork.DEBUG_ONE)
				System.out.println("N: NEURON OUTPUT wo/ sigmoid: "
						+ this.state);
			tmp.add(this.state);
		} else {
			if (NeuralNetwork.DEBUG_ONE)
				System.out
						.println("N: NEURON OUTPUT w/ sigmoid: " + this.state);
			tmp.add(sigmoid(this.state));
		}
		return tmp;
	}

	/**
	 * Clears the inputs used, this should be used when the network is re-run
	 */
	public void clear() {
		this.inputs = new ArrayList<Double>();
	}

	/**
	 * Sigmoid activation function
	 */
	public double sigmoid(double x) {
		return 1d / (1 + Math.pow(Math.E, -x));
	}

	/**
	 * This is used for adjusting the weights
	 * 
	 * @param x
	 * @return
	 */
	public double signmoidDerivative(double x) {
		return Math.log((-x) / (x - 1));
	}

	public double getError() {
		return this.error;
	}

	public void setError(double error) {
		System.out.println("Error was: " + this.error + " error is now: "
				+ error);
		this.error = error;
	}

	public boolean isInputLayer() {
		return this.inputLayer;
	}

	public void setInputLayer(boolean inputLayer) {
		this.inputLayer = inputLayer;
	}

	/**
	 * This does nothing for atomic models
	 */
	@Override
	public void train(boolean train) {
		// STUB

	}
}
