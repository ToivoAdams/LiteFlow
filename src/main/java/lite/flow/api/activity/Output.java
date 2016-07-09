/**
 * 
 */
package lite.flow.api.activity;

/**
 * 	Activity output (port).
 * 
 * 
 * 
 * @author ToivoAdams
 *
 */
public class Output {

	protected Emitter emitter;
	
	public Output() {
		
	}

	public void emit(Object data) {

		emitter.emit(data);
	}

}
