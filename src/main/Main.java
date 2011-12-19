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
		// Hecht-Nielsen theorem
		// numNeuronsAtEachLayer[1] = (numNeuronsAtEachLayer[0] * 2) + 1;
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

		String actual = ".35 .9";

		// String initial = "192.168.1.10";
		//
		// String[] sp = initial.split("\\.");
		// String[] bin = new String[sp.length];
		// // System.out.println("len: " + sp.length);
		// for (int i = 0; i < sp.length; i++) {
		// int a = Integer.parseInt(sp[i]);
		// String outA = Integer.toBinaryString(a);
		// while (outA.length() < 8) {
		// outA = "0" + outA;
		// }
		// bin[i] = outA;
		// // System.out.println(outA);
		// }
		//
		// String actual = "";
		// actual = bin[0] + bin[1] + bin[2] + bin[3];
		// actual = actual.replaceAll(".(?=.)", "$0 ");
		// System.out.println("Input: " + actual);

		// ///// test something else
		// String initial2 = "127.0.0.1";
		//
		// String[] sp2 = initial2.split("\\.");
		// String[] bin2 = new String[sp2.length];
		// // System.out.println("len: " + sp.length);
		// for (int i2 = 0; i2 < sp2.length; i2++) {
		// int a2 = Integer.parseInt(sp2[i2]);
		// String outA2 = Integer.toBinaryString(a2);
		// while (outA2.length() < 8) {
		// outA2 = "0" + outA2;
		// }
		// bin2[i2] = outA2;
		// // System.out.println(outA);
		// }
		//
		// String actual2 = "";
		// actual2 = bin2[0] + bin2[1] + bin2[2] + bin2[3];
		// actual2 = actual2.replaceAll(".(?=.)", "$0 ");
		// // System.out.println("Input: " + actual2);

		double target = .3;
		// double target2 = 0;

		NeuralNetwork model = new NeuralNetwork(numNeuronsAtEachLayer, target);
		SimFramework<Double, Double> sf = new SimFramework<Double, Double>(model);
		sf.takeInputFunction(new BinaryInput());
		// int runTimes = numNeuronsAtEachLayer.length +
		// (numNeuronsAtEachLayer.length - 1);

		// how many times should we trian it?
		int trainTimes = 1000;
		for (int i = 0; i < trainTimes; i++) {
			System.out.println();
			System.out.println("Run iteration: " + i);
			sf.train(actual); // this needs to take in the target
			System.out.println();
		}

		sf.tick(actual);

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