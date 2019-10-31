// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.scene;

/**
 * Class Trace - use to write out messages to standard output while debugging.
 * 
 * Can use the msg() method to write out messages to standard output conditional on the value of
 * the boolean field trace.  This allows you to leave debugging information messages in the code
 * and switch them on or off if you wish.
 * 
 * Really need something that can easily log information only for particualar classes or methods,
 * and should be able to do this using the class information available at run-time in java.
 */
public class Trace {

	/**
	 * Controls whether trace messages are printed.
	 */
    public static boolean trace = true;

    /**
     * Prints a message if trace is enabled, squelches otherwise
     * @param msg
     */
    public static void msg(String msg) {
        if (trace)
            System.out.println(msg);
    }
}
