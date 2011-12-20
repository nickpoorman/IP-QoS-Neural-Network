package neuralnetwork;

import java.math.BigDecimal;
import java.util.ArrayList;

import framework.Model;

public class NeuralNetwork extends Model<BigDecimal, BigDecimal> {

	public static final boolean DEBUG = false;
	public static final boolean DEBUG_ONE = false;
	// pre defined weights - use only with 2 2 1 network
	public static final boolean DEBUG_TWO = false;

	private boolean init = true;

	private ArrayList<Layer> layers;

	private ArrayList<BigDecimal> input;

	private ArrayList<BigDecimal> state;

	private final int timesToRunNetwork;

	private boolean train;

	/**
	 * target is used to train the network
	 */
	private ArrayList<BigDecimal> target; // don't forget to set this

	public NeuralNetwork(int[] numNeuronsAtLayer) {
		this.timesToRunNetwork = numNeuronsAtLayer.length + (numNeuronsAtLayer.length - 1);

		state = new ArrayList<BigDecimal>();

		this.layers = new ArrayList<Layer>();

		// add all the layers
		for (int i = 0; i < numNeuronsAtLayer.length; i++) {
			if ((i + 1) == numNeuronsAtLayer.length) {
				// we are at the last layer so don't create weights
				this.layers.add(new Layer(numNeuronsAtLayer[i], 0));
			} else {
				this.layers.add(new Layer(numNeuronsAtLayer[i], numNeuronsAtLayer[i + 1]));
			}
		}
	}

	/**
	 * This method is the state change on the network.
	 * 
	 * setting train to true will train the network using back propagation.
	 * setting train to false will run the input through the already trained
	 * network
	 */
	public void delta() {
		for (int runNum = 0; runNum < this.timesToRunNetwork; runNum++) {
			ArrayList<BigDecimal> newState = new ArrayList<BigDecimal>();
			// start by setting the state of the input neurons to be the input
			// of the network
			if (NeuralNetwork.DEBUG)
				System.out.println("Initalizing input neurons");
			ArrayList<Neuron> localNeurons = this.layers.get(0).getNeurons();
			int ptr = 0;
			for (int i = 0; i < localNeurons.size(); i++) {
				Neuron neuron = localNeurons.get(i);
				neuron.setInputLayer(true);
				// this process will initialize the Neuron
				neuron.clear();
				ArrayList<BigDecimal> tmp = new ArrayList<BigDecimal>();
				tmp.add(input.get(i));
				neuron.takeInput(tmp);
				neuron.delta();
				neuron.clear();
				ptr++;
			}

			this.target = new ArrayList<BigDecimal>();
			// now adjust the targets
			for (int i = ptr; i < this.input.size(); i++) {
				this.target.add(input.get(ptr));
			}

			// we are going to set all the weights statically for testing here
			if (NeuralNetwork.DEBUG_TWO) {
				if (this.init) {
					// first neurons weights
					this.layers.get(0).getWeights().get(0).setWeight(new BigDecimal(.1));
					this.layers.get(0).getWeights().get(2).setWeight(new BigDecimal(.4));

					// second neurons weights
					this.layers.get(0).getWeights().get(1).setWeight(new BigDecimal(.8));
					this.layers.get(0).getWeights().get(3).setWeight(new BigDecimal(.6));

					// third neurons weights
					this.layers.get(1).getWeights().get(0).setWeight(new BigDecimal(.3));

					// fourth neurons weights
					this.layers.get(1).getWeights().get(1).setWeight(new BigDecimal(.9));
					this.init = false;
				}
			}

			// set the input for each of the layers
			for (int layerNum = 0; layerNum < this.layers.size(); layerNum++) {
				Layer prevLayer = null;
				if ((layerNum - 1) >= 0) {
					prevLayer = this.layers.get(layerNum - 1);
				} else {
					// System.out.println("DEBUG at begin");
				}

				// set the input from the given layer to the next layer
				Layer layer = this.layers.get(layerNum);
				ArrayList<Neuron> layerNeurons = layer.getNeurons();
				for (int neuronNum = 0; neuronNum < layerNeurons.size(); neuronNum++) {
					Neuron n = layerNeurons.get(neuronNum);

					// SET NEURONS OF CURRENT LAYER
					// the first layer doesn't take input so we do this manually
					// from the network
					if (layerNum == 0) {
						// do nothing cause we did it up there ^
					} else {
						if (NeuralNetwork.DEBUG)
							System.out.println("Setting neurons of layer: " + layerNum + " neuron: " + neuronNum);
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
						if (NeuralNetwork.DEBUG)
							System.out.println("Setting output to network at layer: " + layerNum);
						// we are at the end so simply output the state to the
						// network

						// I think this needs to be changed
						// we should only make output for the network when it
						// has run through so many iterations
						for (BigDecimal d : n.lambda()) {
							newState.add(d);
						}
					} else {
						if (NeuralNetwork.DEBUG)
							System.out.println("Setting weights of layer: " + layerNum + " for neuron: " + neuronNum);
						// call the lambda function of all the neurons
						BigDecimal output = n.lambda().get(0);
						if (NeuralNetwork.DEBUG)
							System.out.println("layer: " + layerNum + " neuron: " + neuronNum + " neuron output is: " + output);
						if (NeuralNetwork.DEBUG)
							System.out.println("Input layer?: " + n.isInputLayer());

						// then feed that output into the respective weights
						ArrayList<Weight> neuronWeights = layer.getWeightsForNeuron(n);
						for (Weight weight : neuronWeights) {
							ArrayList<BigDecimal> tmpL = new ArrayList<BigDecimal>();
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
					if (NeuralNetwork.DEBUG)
						System.out.println("running delta on weight");
					w.delta();
				}

				// get all the weights
				ArrayList<Neuron> deltaNeurons = l.getNeurons();
				for (Neuron n : deltaNeurons) {
					if (NeuralNetwork.DEBUG)
						System.out.println("running delta on neuron");
					n.delta();
				}
			}
		}

		if (this.train) {
			// TRAINING HERE
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
				BigDecimal out = n.lambda().get(0);
				BigDecimal error = out.multiply((new BigDecimal(1).subtract(out))).multiply((this.target.get(neuronNum).subtract(out)));
				if (NeuralNetwork.DEBUG)
					System.out.println("Target is: " + this.target.get(neuronNum));
				if (NeuralNetwork.DEBUG)
					System.out.println("Calculating ERROR: " + error + " = " + out + " * (1 - " + out + ") * (" + this.target.get(neuronNum) + " - "
							+ out);

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

					BigDecimal totalError = new BigDecimal(0);
					// multiply of the old weight and the error
					// and sum them all up
					for (int weightNum = 0; weightNum < layerWeights.size(); weightNum++) {
						Weight w = layerWeights.get(weightNum);
						totalError = totalError.add((w.getError().multiply(w.getOldWeight())));
					}

					BigDecimal outE = n.lambda().get(0);
					BigDecimal neuronError = outE.multiply((new BigDecimal(1).subtract(outE))).multiply(totalError);

					// now change all the hidden layer weights
					ArrayList<Weight> prevLayerWeights = prevLayer.getWeightsForNeuronPrev(neuronNum);
					for (int weightNum = 0; weightNum < prevLayerWeights.size(); weightNum++) {
						Weight w = prevLayerWeights.get(weightNum);
						w.updateWeight(neuronError);
					}
				}
			}
		}
	}

	@Override
	public void takeInput(ArrayList<BigDecimal> input) {
		this.input = input;
	}

	/**
	 * send output to the simulator
	 */
	public ArrayList<BigDecimal> lambda() {
		return this.state;
	}

	/**
	 * This must be set to true for training to occur in the network.
	 */
	@Override
	public void train(boolean train) {
		this.train = train;
	}
}
