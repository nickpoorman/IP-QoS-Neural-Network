package neuralnetwork;

import java.math.BigDecimal;
import java.util.ArrayList;

import framework.Model;

public class Neuron extends Model<BigDecimal, BigDecimal> {

	private BigDecimal state = new BigDecimal(0);
	private ArrayList<BigDecimal> inputs;
	private boolean inputLayer = false;
	private BigDecimal error = new BigDecimal(0);

	public Neuron() {
		this.inputs = new ArrayList<BigDecimal>();
	}

	@Override
	public void takeInput(ArrayList<BigDecimal> input) {
		for (BigDecimal d : input) {
			this.inputs.add(d);
		}
	}

	/**
	 * This method does the state change on the atomic model (Neuron) It
	 * calculates based on the inputs
	 */
	@Override
	public void delta() {
		BigDecimal tmpState = new BigDecimal(0);
		for (BigDecimal d : inputs) {
			tmpState = tmpState.add(d);
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
	public ArrayList<BigDecimal> lambda() {
		ArrayList<BigDecimal> tmp = new ArrayList<BigDecimal>();
		if (inputLayer) {
			if (NeuralNetwork.DEBUG_ONE)
				System.out.println("N: NEURON OUTPUT wo/ sigmoid: " + this.state);
			tmp.add(this.state);
		} else {
			if (NeuralNetwork.DEBUG_ONE)
				System.out.println("N: NEURON OUTPUT w/ sigmoid: " + this.state);
			tmp.add(sigmoid(this.state));
		}
		return tmp;
	}

	/**
	 * Clears the inputs used, this should be used when the network is re-run
	 */
	public void clear() {
		this.inputs = new ArrayList<BigDecimal>();
	}

	/**
	 * Sigmoid activation function
	 */
	public BigDecimal sigmoid(BigDecimal x) {
		return new BigDecimal(1d / (1 + Math.pow(Math.E, x.negate().doubleValue())));
	}

	/**
	 * This is used for adjusting the weights
	 * 
	 * @param x
	 * @return
	 */
	public BigDecimal signmoidDerivative(double x) {
		return new BigDecimal(Math.log((-x) / (x - 1)));
	}

	public BigDecimal getError() {
		return this.error;
	}

	public void setError(BigDecimal error) {
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
