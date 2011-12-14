package neuralnetwork;

import java.util.ArrayList;

public class Layer {

	private ArrayList<Weight> weights;
	private ArrayList<Neuron> neurons;

	private final int numNeurons;
	private final int numNeuronsNextLayer;

	public Layer(int numNeurons, int numNeuronsNextLayer) {
		this.numNeurons = numNeurons;
		this.numNeuronsNextLayer = numNeuronsNextLayer;

		this.weights = new ArrayList<Weight>();
		this.neurons = new ArrayList<Neuron>();

		// create all the neurons
		for (int i = 0; i < numNeurons; i++) {
			this.addNeuron(new Neuron());
		}

		// create all the weights
		// this should produce a weights size of the number of neurons in this
		// layer times the number of neurons in the next layer
		// for each neuron in this layer
		for (int i = 0; i < neurons.size(); i++) {
			// create a weight for each neuron in the next layer
			for (int j = 0; j < numNeuronsNextLayer; j++) {
				this.addWeight(new Weight());
			}
		}
	}

	public int getNumNeurons() {
		return this.numNeurons;
	}

	public int getNumNeuronsNextLayer() {
		return this.numNeuronsNextLayer;
	}

	private void addNeuron(Neuron n) {
		this.neurons.add(n);
	}

	public ArrayList<Neuron> getNeurons() {
		return this.neurons;
	}

	private void addWeight(Weight w) {
		this.weights.add(w);
	}

	public ArrayList<Weight> getWeights() {
		return this.weights;
	}

	public ArrayList<Weight> getWeightsForNeuron(Neuron n) {
		int pos = 0;
		for (; pos < this.neurons.size(); pos++) {
			if (this.neurons.get(pos).equals(n)) {
				break;
			}
		}
		ArrayList<Weight> tmpWeights = new ArrayList<Weight>();
		for (int weightNode = pos; weightNode < this.getWeights().size(); weightNode += this.getNeurons().size()) {
			tmpWeights.add(this.weights.get(weightNode));
		}
		return tmpWeights;
	}

	public ArrayList<Weight> getWeightsForNeuronPrev(int neuronPos) {
		ArrayList<Weight> tmpWeights = new ArrayList<Weight>();

		int startPos = neuronPos * this.neurons.size();
		int endPos = startPos + this.neurons.size();

		for (int i = startPos; i < endPos; i++) {
			tmpWeights.add(this.weights.get(i));
		}
		return tmpWeights;
	}
}
