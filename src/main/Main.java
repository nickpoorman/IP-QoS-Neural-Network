package main;

import java.math.BigDecimal;
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
		numNeuronsAtEachLayer[0] = 5;
		// Hecht-Nielsen theorem
		// numNeuronsAtEachLayer[1] = (numNeuronsAtEachLayer[0] * 2) + 1;
		numNeuronsAtEachLayer[1] = 11;
		numNeuronsAtEachLayer[2] = 1;

		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter the number of layers: ");
		int numLayers = Integer.parseInt(sc.nextLine().trim());
		while (numLayers < 3) {
			System.out.println("You must have at least 3 layers");
			System.out.print("Please enter the number of layers: ");
			numLayers = Integer.parseInt(sc.nextLine().trim());
		}
		numNeuronsAtEachLayer = new int[numLayers];
		System.out.println();
		System.out.println("Please enter the number of neurons at each layer.");
		System.out.println("Suggestion. Use Hecht-Nielsen theorem. (inputs * 2) + 1");
		System.out.println("Note: The more neurons the longer it will take to train.");
		System.out.println("ie. 2 5 1");
		String[] numNeurons = sc.nextLine().trim().split(" ");
		System.out.println();
		for (int i = 0; i < numLayers; i++) {
			numNeuronsAtEachLayer[i] = Integer.parseInt(numNeurons[i]);
		}

		System.out.println("Please enter the inputs followed by their target output");
		System.out.println("ie. <input> <output> [<input> <output>]");
		System.out.println("ie. .35 .9 .3 .68 .2 .7");
		String[] inputLine = sc.nextLine().trim().split(" ");
		while ((inputLine.length % (numNeuronsAtEachLayer[0] + numNeuronsAtEachLayer[numNeuronsAtEachLayer.length - 1])) != 0) {
			System.out.println("You did not enter enought inputs with target outputs");
			System.out.println("Please enter the inputs followed by their target output");
			System.out.println("ie. <input> <output> [<input> <output>]");
			System.out.println("ie. .35 .9 .3 .68 .2 .7");
			inputLine = sc.nextLine().trim().split(" ");
		}
		int pointer = 0;
		int numTimes = inputLine.length / (numNeuronsAtEachLayer[0] + numNeuronsAtEachLayer[numNeuronsAtEachLayer.length - 1]);
		String[] inputsAndTargets = new String[numTimes];
		for (int init = 0; init < inputsAndTargets.length; init++) {
			inputsAndTargets[init] = "";
		}
		for (int j = 0; j < numTimes; j++) {
			for (int i = 0; i < numNeuronsAtEachLayer[0]; i++) {
				inputsAndTargets[j] += inputLine[pointer++] + " ";
			}
			// get the targets
			for (int i = 0; i < numNeuronsAtEachLayer[numNeuronsAtEachLayer.length - 1]; i++) {
				inputsAndTargets[j] += inputLine[pointer++] + " ";
			}
			inputsAndTargets[j] = inputsAndTargets[j].trim();
		}

		System.out.println("How many times would you like to train?");
		int trainTimes = Integer.parseInt(sc.nextLine());

		// IP Translation stuff here. This should be done outside the scope of
		// this program. The neural net.

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

		NeuralNetwork model = new NeuralNetwork(numNeuronsAtEachLayer);
		SimFramework<BigDecimal, BigDecimal> sf = new SimFramework<BigDecimal, BigDecimal>(model);
		sf.takeInputFunction(new BinaryInput());
		for (int i = 0; i < trainTimes; i++) {
			for (int itemsTrain = 0; itemsTrain < inputsAndTargets.length; itemsTrain++) {
				System.out.println();
				System.out.println("Train iteration: " + i);
				System.out.println("inputting: " + inputsAndTargets[itemsTrain]);
				sf.train(inputsAndTargets[itemsTrain]); // this needs to take in
														// the target
				System.out.println();
			}
		}

		System.out.println("Done training");

		for (;;) {
			System.out.println("Please enter an input to run through the network. Or 'exit' to exit.");
			System.out.println("ie. .35 .9");
			String in = "";
			try {
				in = sc.nextLine();
			} catch (java.util.NoSuchElementException e) {
				System.exit(-1);
			}
			if (in.equals("exit")) {
				break;
			}
			if (in.split(" ").length != numNeuronsAtEachLayer[0]) {
				System.err.println("Not valid input");
				continue;
			}
			sf.tick(in);
		}
		System.out.println("Goodbye.");

	}
}

class BinaryInput implements Inputtable<BigDecimal> {

	@Override
	public ArrayList<BigDecimal> input(String line) {
		// Take each of the inputs in the string and create a collection
		// out of them
		ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
		for (String s : line.split("\\s+")) {
			BigDecimal num = new BigDecimal(Double.parseDouble(s));
			list.add(num);
		}

		return list;
	}
}