package lite.flow.example.component;

import lite.flow.api.activity.Entry;

/**
 * Simple component which performs addition of numbers.
 * 
 * Note!!! component is without explicit Output port!
 * Instead @Entry annotation is used to define input and output names.
 * 
 *	@author ToivoAdams
 */
public class Adder2 {

	@Entry(outName="result", inNames={"avalue","bvalue"})
	public int number( int a, int b) {
		return a + b;
	}
}
