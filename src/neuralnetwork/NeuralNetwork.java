package neuralnetwork;

import java.util.ArrayList;

public class NeuralNetwork {

	private ArrayList<Layer> layers;

	private ArrayList<Double> input;

	private double state;

	public NeuralNetwork(int[] numNeuronsAtLayer) {

		this.layers = new ArrayList<Layer>();

		// add all the layers
		for (int i = 0; i < numNeuronsAtLayer.length; i++) {
			if ((i + 1) == numNeuronsAtLayer.length) {
				// we are at the last layer so dont create weights
				this.layers.add(new Layer(numNeuronsAtLayer[i], 0));
			} else {
				this.layers.add(new Layer(numNeuronsAtLayer[i], numNeuronsAtLayer[i + 1]));
			}
		}
	}

	public void delta() {
		// start by setting the state of the input neurons to be the input of
		// the network
		ArrayList<Neuron> neurons = this.layers.get(0).getNeurons();
		for (int i = 0; i < neurons.size(); i++) {
			Neuron neuron = neurons.get(i);
			// this process will initialize the Neuron
			neuron.takeInput(input.get(i));
			neuron.delta();
			neuron.clear();
		}

		// set the input for each of the layers
		for (int layerNum = 0; layerNum < this.layers.size(); layerNum++) {
			// get the next layer
			// Layer nextLayer = null;
			// if ((layerNum + 1) < this.layers.size()) {
			// nextLayer = this.layers.get(layerNum + 1);
			// } else {
			// System.out.println("DEBUG TODO: at end node");
			// }

			Layer prevLayer = null;
			if ((layerNum - 1) < 0) {
				prevLayer = this.layers.get(layerNum - 1);
			} else {
				System.out.println("DEBUG TODO: at begin");
			}

			// set the input from the given layer to the next layer
			Layer layer = this.layers.get(layerNum);
			for (int neuronNum = 0; neuronNum < layer.getNeurons().size(); neuronNum++) {
				Neuron n = layer.getNeurons().get(neuronNum);

				// SET NEURONS OF CURRENT LAYER
				// the first layer doesn't take input so we do this manually
				// from the network
				if (layerNum == 0) {
					// do nothing cause we did it up there ^?
				} else {

					// take input from the weights
					// should be the first n number of neurons

					// need to start this loop at the neuron number * the number
					// of neurons
					ArrayList<Weight> preWeights = prevLayer.getWeightsForNeuronPrev(neuronNum);
					for (Weight w : preWeights) {
						n.takeInput(w.lambda());
					}
				}

				// SET WEIGHTS OF CURRENT LAYER
				// call the lambda function of all the neurons
				double output = n.lambda();

				// then feed that output into the respective weights
				ArrayList<Weight> neuronWeights = layer.getWeightsForNeuron(n);
				for (Weight weight : neuronWeights) {
					weight.takeInput(output);
				}
			}
		}

	}

	public void takeInput(ArrayList<Double> input) {
		this.input = input;
	}

	/**
	 * send output to the simulator
	 */
	public double lambda() {
		return this.state;
	}

	public void run() {

	}

}
