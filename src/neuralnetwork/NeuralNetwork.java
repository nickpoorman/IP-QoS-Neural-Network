package neuralnetwork;

import java.util.ArrayList;

import framework.Model;

public class NeuralNetwork extends Model<Double, Double> {

	public static final boolean DEBUG = true;

	private ArrayList<Layer> layers;

	private ArrayList<Double> input;

	private ArrayList<Double> state;

	private final int timesToRunNetwork;

	/**
	 * target is used to train the network
	 */
	private double target;

	public NeuralNetwork(int[] numNeuronsAtLayer, double target) {
		this.timesToRunNetwork = numNeuronsAtLayer.length + (numNeuronsAtLayer.length - 1);

		state = new ArrayList<Double>();

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
		for (int runNum = 0; runNum < this.timesToRunNetwork; runNum++) {
			ArrayList<Double> newState = new ArrayList<Double>();
			// start by setting the state of the input neurons to be the input
			// of the network
			if (NeuralNetwork.DEBUG) System.out.println("Initalizing input neurons");
			ArrayList<Neuron> localNeurons = this.layers.get(0).getNeurons();
			for (int i = 0; i < localNeurons.size(); i++) {
				Neuron neuron = localNeurons.get(i);
				// this process will initialize the Neuron
				neuron.clear();
				ArrayList<Double> tmp = new ArrayList<Double>();
				tmp.add(input.get(i));
				neuron.takeInput(tmp);
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
				// System.out.println("DEBUG at end node");
				// }

				Layer prevLayer = null;
				if ((layerNum - 1) >= 0) {
					prevLayer = this.layers.get(layerNum - 1);
				} else {
					// System.out.println("DEBUG at begin");
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
						if (NeuralNetwork.DEBUG) System.out.println("Setting neurons of layer: " + layerNum);
						// take input from the weights
						// should be the first n number of neurons

						// need to start this loop at the neuron number * the
						// number
						// of neurons
						ArrayList<Weight> preWeights = prevLayer.getWeightsForNeuronPrev(neuronNum);
						// clear the neurons current input
						n.clear();
						for (Weight w : preWeights) {
							n.takeInput(w.lambda());
						}
					}

					// SET WEIGHTS OF CURRENT LAYER
					if (layerNum == (this.layers.size() - 1)) { // last layer
						if (NeuralNetwork.DEBUG) System.out.println("Setting output to network at layer: " + layerNum);
						// we are at the end so simply output the state to the
						// network
						for (Double d : n.lambda()) {
							newState.add(d.doubleValue());
						}
					} else {
						if (NeuralNetwork.DEBUG) System.out.println("Setting weights of layer: " + layerNum);
						// call the lambda function of all the neurons
						double output = n.lambda().get(0);

						// then feed that output into the respective weights
						ArrayList<Weight> neuronWeights = layer.getWeightsForNeuron(n);
						for (Weight weight : neuronWeights) {
							ArrayList<Double> tmpL = new ArrayList<Double>();
							tmpL.add(output);
							weight.takeInput(tmpL);
						}
					}
				}
			}
			this.state = newState;

			// now call delta on all the models
			for (Layer l : this.layers) {
				// get all the neurons
				ArrayList<Weight> deltaWeights = l.getWeights();
				for (Weight w : deltaWeights) {
					if (NeuralNetwork.DEBUG) System.out.println("running delta on weight");
					w.delta();
				}

				// get all the weights
				ArrayList<Neuron> deltaNeurons = l.getNeurons();
				for (Neuron n : deltaNeurons) {
					if (NeuralNetwork.DEBUG) System.out.println("running delta on neuron");
					n.delta();
				}
			}
		}

		// ADJUST THE WEIGHTS

		// get the output layer
		Layer lastLayer = this.layers.get(this.layers.size() - 1);

		// get the layer before the last one
		Layer secondToLastLayer = this.layers.get(this.layers.size() - 2);

		// get the output neurons
		ArrayList<Neuron> outputNeurons = lastLayer.getNeurons();

		// now calculate the error for each output neuron
		// for (Neuron n : outputNeurons) {
		for (int neuronNum = 0; neuronNum < outputNeurons.size(); neuronNum++) {
			Neuron n = outputNeurons.get(neuronNum);
			double out = n.lambda().get(0);
			double error = out * (1 - out) * (this.target - out);

			// feed that error back into all its weights and update them
			ArrayList<Weight> preWeights = secondToLastLayer.getWeightsForNeuronPrev(neuronNum);
			for (Weight w : preWeights) {
				w.updateWeight(error);

			}
		}

		// adjust the inner layer weights
		for (int layerNum = this.layers.size() - 2; layerNum > 0; layerNum--) {
			Layer layer = this.layers.get(layerNum);
			Layer prevLayer = this.layers.get(layerNum - 1);

			// we need to get the error of the weights in the previous layer
			// for each neuron in the previous layer get their weights

			// lets start by getting each neuron and setting its error
			ArrayList<Neuron> layerNeurons = layer.getNeurons();
			for (int neuronNum = 0; neuronNum < layerNeurons.size(); neuronNum++) {
				Neuron n = layerNeurons.get(neuronNum);

				// get all of its weights
				ArrayList<Weight> layerWeights = layer.getWeightsForNeuron(n);

				double totalError = 0;
				// multiply of the old weight and the error
				// and sum them all up
				for (int weightNum = 0; weightNum < layerWeights.size(); weightNum++) {
					Weight w = layerWeights.get(weightNum);
					totalError += (w.getError() * w.getOldWeight());
				}

				double outE = n.lambda().get(0);
				double neuronError = outE * (1 - outE) * totalError;
				// n.setError(neuronError);

				// now change all the hidden layer weights
				ArrayList<Weight> prevLayerWeights = prevLayer.getWeightsForNeuronPrev(neuronNum);
				for (int weightNum = 0; weightNum < prevLayerWeights.size(); weightNum++) {
					Weight w = prevLayerWeights.get(weightNum);
					w.updateWeight(neuronError);
				}
			}
		}
	}

	@Override
	public void takeInput(ArrayList<Double> input) {
		this.input = input;
	}

	/**
	 * send output to the simulator
	 */
	public ArrayList<Double> lambda() {
		return this.state;
	}
}
