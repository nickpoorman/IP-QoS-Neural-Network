package neuralnetwork;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import framework.Model;

public class Weight extends Model<BigDecimal, BigDecimal> {

	private BigDecimal input = new BigDecimal(0);
	private BigDecimal weight = new BigDecimal(0);
	private BigDecimal state = new BigDecimal(0);
	private BigDecimal oldWeight = new BigDecimal(0);
	private BigDecimal error = new BigDecimal(0);

	/**
	 * This constructor will set the weights to a random value.
	 */
	public Weight() {
		Random r = new Random();
		weight = new BigDecimal(r.nextDouble());
		oldWeight = new BigDecimal(weight.doubleValue());

	}

	/**
	 * This will change the state based on the input and weight
	 */
	@Override
	public void delta() {
		this.state = input.multiply(weight);
		if (NeuralNetwork.DEBUG)
			System.out.println("W Input: " + this.input + " weight: " + this.weight);
		if (NeuralNetwork.DEBUG)
			System.out.println("W Setting state to: " + this.state);
	}

	/**
	 * This will output a value based on the current state
	 * 
	 * @return
	 */
	@Override
	public ArrayList<BigDecimal> lambda() {
		if (NeuralNetwork.DEBUG_ONE)
			System.out.println("WEIGHT OUTPUT: " + this.state);
		ArrayList<BigDecimal> tmp = new ArrayList<BigDecimal>();
		tmp.add(this.state);
		return tmp;
	}

	@Override
	public void takeInput(ArrayList<BigDecimal> input) {
		if (NeuralNetwork.DEBUG)
			System.out.println("W Setting input to: " + input.get(0));
		this.input = input.get(0);
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getError() {
		return this.error;
	}

	public BigDecimal getOldWeight() {
		return this.oldWeight;
	}

	/**
	 * This method will change the weight of the current object given an error.
	 * It uses the current states of the object to update the weight.
	 * 
	 * @param error
	 */
	public void updateWeight(BigDecimal error) {
		if (NeuralNetwork.DEBUG)
			System.out.println("Error was: " + this.error + " to: " + error);

		this.error = error;
		this.oldWeight = weight;

		if (NeuralNetwork.DEBUG)
			System.out.println("CALCULATING NEW WEIGHT: " + this.weight + " + (" + error + " * " + this.input + ")");

		this.weight = this.weight.add((error.multiply(this.input)));

		if (NeuralNetwork.DEBUG)
			System.out.println("Updating the weight: " + this.oldWeight + " to: " + this.weight);
	}

	/**
	 * This does nothing for atomic models
	 */
	@Override
	public void train(boolean train) {
		// STUB
	}
}
