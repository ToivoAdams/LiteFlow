package lite.flow.example.component;

import lite.flow.api.activity.Output;

/**
 * Simple component which splits String to 2 parts.
 * 
 * Expected input is 2 numbers separated by semicolon ";"
 * For example:
 * 
 *    278;83
 * 
 *	@author ToivoAdams
 */
public class StringSplitter {

	/** 
	 * declare outputs "outA" and "outB"
	 */ 
	Output<String> outA = new Output<>();
	Output<String> outB = new Output<>();

	public void transform( String str) {
		String[] parts = str.split(";");
		if (parts.length<2)
			throw new IllegalArgumentException("Invalid input, should be 2 numbers separated by semicolon, but is " + str);
		outA.emit(parts[0]);
		outB.emit(parts[1]);
	}
}
