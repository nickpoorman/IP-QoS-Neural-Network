package neuralnetwork;

import java.util.ArrayList;
import java.util.Random;

import framework.Model;

public class Weight extends Model<Double, Double> {

	private double input;
	private double weight;
	private double state;
	private double oldWeight;
	private double error;

	public Weight() {
		Random r = new Random();
		weight = r.nextDouble();
		// weight = 1.0;
		oldWeight = weight;

	}

	/**
	 * This will change the state based on the input and weight
	 */
	@Override
	public void delta() {
		this.state = input * weight;
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
	public ArrayList<Double> lambda() {
		if (NeuralNetwork.DEBUG_ONE)
			System.out.println("WEIGHT OUTPUT: " + this.state);
		ArrayList<Double> tmp = new ArrayList<Double>();
		tmp.add(this.state);
		return tmp;
	}

	@Override
	public void takeInput(ArrayList<Double> input) {
		if (NeuralNetwork.DEBUG)
			System.out.println("W Setting input to: " + input.get(0));
		this.input = input.get(0);
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getError() {
		return this.error;
	}

	public double getOldWeight() {
		return this.oldWeight;
	}

	/**
	 * This method will change the weight of the current object given an error.
	 * It uses the current states of the object to update the weight.
	 * 
	 * @param error
	 */
	public void updateWeight(double error) {
		if (NeuralNetwork.DEBUG)
			System.out.println("Error was: " + this.error + " to: " + error);

		this.error = error;
		this.oldWeight = weight;

		if (NeuralNetwork.DEBUG)
			System.out.println("CALCULATING NEW WEIGHT: " + this.weight + " + (" + error + " * " + this.input + ")");

		this.weight = this.weight + (error * this.input);

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
