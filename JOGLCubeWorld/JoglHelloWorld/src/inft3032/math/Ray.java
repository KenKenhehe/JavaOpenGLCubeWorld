package inft3032.math;



/**
 * A class for a ray - with a starting point and a direction.
 */
public class Ray {
    private Vector3 start;
    private Vector3 direction;

    /**
     * Default constructor:
     * A ray starting at the origin in the direction of the +z axis.
     */
    public Ray() {
        start = new Vector3(0, 0, 0);
        direction = new Vector3(0, 0, 1);
    }

    /**
     * A constructor for a specified starting position and direction.
     * The direction stored is a unit vector,  and so this constructor will fail
     * if a zero vector is passed in for the direction.
     *
     * @param start     The starting position.
     * @param dir       A vector in the direction of the ray - must not be a zero vector.
     */
    public Ray(Vector3 start, Vector3 dir) {
        this.start = new Vector3(start);    // make a copy of start
        this.direction = dir.unit();  // .unit creates a new Point3D object.
    }
    
    /**
     * @return The starting point for a ray.
     */
    public Vector3 start() {
        return start;
    }

    /**
     * @return The direction of a ray.
     */
    public Vector3 direction() {
        return direction;
    }


    /**
     * Return a ray that is constructed from an existing ray by translation by a displacement.
     *
     * @param displacement The displacement vector.
     * @return The new displaced ray.
     */
    public Ray translate(Vector3 displacement) {
        return new Ray(start.plus(displacement), direction);
    }

    /**
     * Returns a point along the ray at a given distance from the starting point.
     *
     * @param distance The distance of the point from the starting point.
     * @return The point on the ray.
     */
    public Vector3 position(float distance) {
        return start.plus(direction.mult(distance));
    }

    /**
     * Overloading of the toString to generate a string represenation of the ray.
     *
     * @return The string representatoin of the ray.
     */
    public String toString() {
        return "(" + start + ":" + direction + ")";
    }
}
