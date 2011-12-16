package main;

import java.util.ArrayList;
import java.util.Scanner;

import neuralnetwork.NeuralNetwork;
import framework.Inputtable;
import framework.SimFramework;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] numNeuronsAtEachLayer = new int[3];
		numNeuronsAtEachLayer[0] = 2;
		numNeuronsAtEachLayer[1] = 2;
		numNeuronsAtEachLayer[2] = 1;

		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter the 32bit IP Address: ");
		// String initial = sc.nextLine();
		// System.out.println();
		// if (initial.split(" ").length != numNeuronsAtEachLayer[0]) {
		// System.err.println("You did not enter enough inputs");
		// return;
		// }

		String initial = ".35 .9";

		double target = 0.5;

		NeuralNetwork model = new NeuralNetwork(numNeuronsAtEachLayer, target);
		SimFramework<Double, Double> sf = new SimFramework<Double, Double>(
				model);
		sf.takeInputFunction(new BinaryInput());
		// int runTimes = numNeuronsAtEachLayer.length +
		// (numNeuronsAtEachLayer.length - 1);

		// how many times should we trian it?
		int trainTimes = 3;
		for (int i = 0; i < trainTimes; i++) {
			System.out.println();
			System.out.println("Run iteration: " + i);
			sf.train(initial);
			System.out.println();
		}

		sf.tick(initial);

	}
}

class BinaryInput implements Inputtable<Double> {

	@Override
	public ArrayList<Double> input(String line) {
		// TODO: Take each of the inputs in the string and create a collection
		// out of them
		ArrayList<Double> list = new ArrayList<Double>();
		for (String s : line.split("\\s+")) {
			// s = s.trim();
			double num = Double.parseDouble(s);
			if (NeuralNetwork.DEBUG)
				System.out.println("Adding : " + num);
			list.add(num);
			// if (s.equals("1")) {
			// list.add(1.0);
			// } else if (s.equals("0")) {
			// list.add(0.0);
			// } else {
			// System.err.println("[" + s + "] is not a valid input");
			// }
		}

		return list;
	}
}