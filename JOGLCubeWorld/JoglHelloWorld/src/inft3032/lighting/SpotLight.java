// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.lighting;
import inft3032.math.Vector3;

/**
 * A class for a spot light source.
 * <p/>
 * The light source is specified by its colour, position and constant, direction, linear
 * and quadratic attenuation factors, a cut-off angle and a drop-off rate.
 * <p/>
 * The attenuation factors are used to scale the light source with distance from the light.
 * If you want the light source at distance d from the light, the attenuation factor is
 * 1.0(constantAttenuation + d*linearAttenuation + d*d*quadraticAttenuation).
 * <p/>
 * If theta is the angle between a vector to point in the scene where you are
 * looking at the light, and the direction of the light,  then the cut-off angle
 * is the value theta beyond which the light does not contribute.
 * <p/>
 * The drop-off rate is a value between 0.0 and 1.0 to determine how quickly the
 * light is attenuated with angle theta between a ray to the scene and the direction
 * of the light source.  The drop-off is equal to cos(theta)**(dropOffRate).
 * <p/>
 * This attenuation is in addition to the attenuation with distance.
 */
public class SpotLight extends Light {

    public Vector3 direction;
    public float cutOffAngle;
    public float cosCutOffAngle;
    public float dropOffRate;
    public float constantAttenuation;
    public float linearAttenuation;
    public float quadraticAttenuation;

    /**
     * Constructor given everything.
     *
     * @param colour
     * @param location
     * @param direction
     * @param ca
     * @param la
     * @param qa
     * @param cutOffAngle
     * @param dropOffRate
     */
    public SpotLight(Vector3 colour, Vector3 location, Vector3 direction,
                     float ca, float la, float qa, float cutOffAngle,
                     float dropOffRate) {
        super(colour);
        this.location = location;
        this.direction = direction.unit();
        constantAttenuation = ca;
        linearAttenuation = la;
        quadraticAttenuation = qa;
        this.cutOffAngle = cutOffAngle;
        cosCutOffAngle = (float) Math.cos(cutOffAngle);
        this.dropOffRate = dropOffRate;
    }
}
