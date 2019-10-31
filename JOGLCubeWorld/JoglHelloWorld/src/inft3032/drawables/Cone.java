// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import javax.media.opengl.GL3;


/**
 * Class for a cone with its central axis parallel to the y-axis, and defined by the
 * centre of the cone,  and the height of the code and the radius of the base of the
 * the cone.
 * <p/>
 *
 * @author a.sobey
 */
public class Cone extends Shape {
    private float height;
    private float radius;

    
    public Cone(float radius, float height, Material m) {
    	super(m);
    	this.radius = radius;
    	this.height = height;
    }


    /**
     * @return The height of the cone (axis of the cone is parallel to the y-axis)
     */
    public float height() {
        return height;
    }

    /**
     * @return The radius of the base of the cone (parallel to the x-z plane
     */
    public float radius() {
        return radius;
    }

    /**
     * @return The string name for the shape.
     */
    public String name() {
        return "cone";
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
