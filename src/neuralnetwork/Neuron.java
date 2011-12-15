package neuralnetwork;

import java.util.ArrayList;

import framework.Model;

public class Neuron extends Model<Double, Double> {

	private double state;
	private ArrayList<Double> inputs;

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
		this.state = tmpState;

		// maybe clear the inputs here?
	}

	/**
	 * This method outputs the state for this atomic model (Neuron)
	 */
	@Override
	public ArrayList<Double> lambda() {
		ArrayList<Double> tmp = new ArrayList<Double>();
		tmp.add(this.state);
		return tmp;
	}

	/**
	 * Clears the inputs used, this should be used when the network is re-run
	 */
	public void clear() {
		this.inputs.clear();
	}
}
