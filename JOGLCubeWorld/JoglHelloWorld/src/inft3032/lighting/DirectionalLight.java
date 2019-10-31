// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.lighting;

import inft3032.math.Vector3;

/**
 * This is effectively a point source at an infinite distance from the scene.
 * There is no attenuation of the light source across the scene.
 *
 * @author a.sobey
 */
public class DirectionalLight extends Light {

    public Vector3 direction;

    public DirectionalLight(Vector3 colour, Vector3 direction) {
        super(colour);
        this.direction = direction.unit();
    }

}
