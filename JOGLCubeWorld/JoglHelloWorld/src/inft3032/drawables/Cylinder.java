// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;


import javax.media.opengl.GL3;


/**
 * A cylinder shape class.
 * <p/>
 * The cylinder has its central axis parallel to the y-axis, and is specified by its centre,
 * the total height of the cylinder, and the radius of the cylinder.
 * <p/>
 * @author a.sobey
 */
public class Cylinder extends Shape {
    private float radius;
    private float height;


    public Cylinder(float radius, float height, Material m) {
    	super(m);
    	this.radius = radius;
    	this.height = height;
    }

    /**
     * @return The radius of the cylinder
     */
    public float radius() {
        return radius;
    }

    /**
     * @return The height of the cylinder (in the y-direction)
     */
    public float height() {
        return height;
    }

    /**
     * @return The string name for the shape.
     */
    public String name() {
        return "cylinder";
    }

	@Override
	public void init(GL3 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GL3 gl) {
		// TODO Auto-generated method stub
		
	}
}
