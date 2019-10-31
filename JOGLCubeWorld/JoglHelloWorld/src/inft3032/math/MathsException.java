package inft3032.math;


/**
 * Exception class thrown when an invalid operation is performed on
 * a maths class. For example, attempting to access an invalid matrix element.
 * 
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public class MathsException extends RuntimeException {

	private static final long serialVersionUID = -5919342362564836789L;

	public MathsException(String error) {
		super(error);
	}
}
