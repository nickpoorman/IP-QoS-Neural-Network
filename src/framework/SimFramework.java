package framework;

import java.util.ArrayList;

public class SimFramework<I, O> {
	Model<I, O> root;
	Inputtable<I> inputFunction;

	public SimFramework(Model<I, O> m) {
		root = m;
	}

	public void takeInputFunction(Inputtable<I> i) {
		inputFunction = i;
	}

	public void train(String line) {
		root.train(true);

		ArrayList<I> c = inputFunction.input(line);
		// set the input
		root.takeInput(c);

		// do the delta function
		root.delta();

		// do the lambda function
		ArrayList<O> out = root.lambda();
		for (O o : out) {
			System.out.print(o.toString() + " ");
		}
		System.out.println();

	}

	public void tick(String line) {
		root.train(false);

		ArrayList<I> c = inputFunction.input(line);
		// set the input
		root.takeInput(c);

		// do the delta function
		root.delta();

		// do the lambda function
		System.out.println("TICK OUTPUT IS: ");
		ArrayList<O> out = root.lambda();
		for (O o : out) {
			System.out.print(o.toString() + " ");
		}
		System.out.println();

	}
}
