// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.lighting;

import inft3032.math.Vector3;

/**
 * A class for a point light source.
 * <p/>
 * The light source is specified by its colour, position and constant, linear and quadratic
 * attenuation factors.
 * <p/>
 * The attenuation factors are used to scale the light source with distance from the light.
 * If you want the light source at distance d from the light, the attenuation factor is
 * 1.0(constantAttenuation + d*linearAttenuation + d*d*quadraticAttenuation).
 * <p/>
 * For  light source with no attenuation, use (1.0, 0.0, 0.0) for the attenuation factors.
 *
 * @author a.sobey
 */
public class PointLight extends Light {

    public float constantAttenuation;
    public float linearAttenuation;
    public float quadraticAttenuation;

    /**
     * contruct from a given colour
     *
     * @param colour The light colour
     */
    public PointLight(Vector3 colour, Vector3 location, float ca, float la, float qa) {
        super(colour);
        this.location = location;
        constantAttenuation = ca;
        linearAttenuation = la;
        quadraticAttenuation = qa;
    }
}
