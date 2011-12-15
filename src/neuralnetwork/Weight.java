package neuralnetwork;

import java.util.ArrayList;
import java.util.Random;

import framework.Model;

public class Weight extends Model<Double, Double> {

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
	@Override
	public void delta() {
		this.state = input * weight;
		if (NeuralNetwork.DEBUG) System.out.println("W Input: " + this.input + " weight: " + this.weight);
		if (NeuralNetwork.DEBUG) System.out.println("W Setting state to: " + this.state);
	}

	/**
	 * This will output a value based on the current state
	 * 
	 * @return
	 */
	@Override
	public ArrayList<Double> lambda() {
		ArrayList<Double> tmp = new ArrayList<Double>();
		tmp.add(this.state);
		return tmp;
	}

	@Override
	public void takeInput(ArrayList<Double> input) {
		this.input = input.get(0);
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * This method will change the weight of the current object given an error.
	 * It uses the current states of the object to update the weight.
	 * 
	 * @param error
	 */
	public void updateWeight(double error) {
		this.weight = this.weight + (error * this.input);

	}
}
