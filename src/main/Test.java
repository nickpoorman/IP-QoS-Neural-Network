package main;

import neuralnetwork.Neuron;

public class Test {

	public static void main(String[] args) {

		Neuron n = new Neuron();
		Neuron n2 = new Neuron();

		System.out.println("should be false: " + n.equals(n2));
		System.out.println("should be false: " + n.equals(n));

	}

}
