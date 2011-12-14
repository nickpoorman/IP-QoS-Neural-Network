package neuralnetwork;

import java.util.Random;

public class Weight {

	private double input;
	private double weight;
	private double state;

	public Weight() {
		Random r = new Random();
		weight = r.nextDouble();

	}

	/**
	 * This will change the state based on the input and weight
	 */
	public void delta() {
		this.state = input * weight;
	}

	/**
	 * This will output a value based on the current state
	 * 
	 * @return
	 */
	public double lambda() {
		return this.state;
	}

	public void takeInput(double input) {
		this.input = input;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
