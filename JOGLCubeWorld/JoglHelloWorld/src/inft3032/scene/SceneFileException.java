// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.scene;


/**
 * Exception class for use when reading in a scene file.
 * @author a.sobey
 */
public class SceneFileException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1900555675543882212L;

	public SceneFileException(String s) {
        super(s);
    }
}
