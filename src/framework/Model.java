package framework;

import java.util.ArrayList;

public abstract class Model<I, O> {
	public I input;
	public I state;

	/**
	 * @param c
	 *            Set the input for for the model. This will be used later when
	 *            calling the delta function.
	 */
	public abstract void takeInput(ArrayList<I> input);

	/**
	 * The delta function does the state change on the model.
	 */
	public abstract void delta();

	/**
	 * @return Collection of output type <O>
	 */
	public abstract ArrayList<O> lambda();

}
