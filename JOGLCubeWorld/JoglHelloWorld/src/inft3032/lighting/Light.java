// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.lighting;
import inft3032.math.Vector3;


public abstract class Light {

    /**
     * The colour of the light.
     */
    public Vector3 colour;


    /**
     * contruct from a given colour
     *
     * @param colour The light colour
     */
    public Light(Vector3 colour) {
        this.colour = colour;
    }
    
    public Vector3 location;

}
