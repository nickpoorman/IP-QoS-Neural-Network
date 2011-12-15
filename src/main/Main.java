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
		numNeuronsAtEachLayer[1] = 3;
		numNeuronsAtEachLayer[2] = 1;

		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter the 32bit IP Address: ");
		String initial = sc.nextLine();
		System.out.println();
		if (initial.split(" ").length != numNeuronsAtEachLayer[0]) {
			System.err.println("You did not enter enough inputs");
			return;
		}

		NeuralNetwork model = new NeuralNetwork(numNeuronsAtEachLayer);
		SimFramework<Double, Double> sf = new SimFramework<Double, Double>(model);
		sf.takeInputFunction(new BinaryInput());
		// int runTimes = numNeuronsAtEachLayer.length +
		// (numNeuronsAtEachLayer.length - 1);
		for (int i = 0; i < 1; i++) {
			System.out.println("Run iteration: " + i);
			sf.tick(initial);
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
}

class BinaryInput implements Inputtable<Double> {

	@Override
	public ArrayList<Double> input(String line) {
		// TODO: Take each of the inputs in the string and create a collection
		// out of them
		ArrayList<Double> list = new ArrayList<Double>();
		for (String s : line.split("\\s+")) {
			s = s.trim();
			if (s.equals("1")) {
				list.add(1.0);
			} else if (s.equals("0")) {
				list.add(0.0);
			} else {
				System.err.println("[" + s + "] is not a valid input");
			}
		}

		return list;
	}
}