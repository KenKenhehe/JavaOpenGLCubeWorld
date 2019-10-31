// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;



import javax.media.opengl.GL3;

/**
 * A class for a Sphere shape.
 * <p/>
 * Created by a.sobey on 29/03/2004 at 00:32:42
 *
 * @author a.sobey
 * @version 1.0
 */
public class Sphere extends Shape {
    /**
     * The radius of the sphere
     */
    private float radius;

    public Sphere (float r, Material m) {
    	super(m);
    	this.radius = r;
    }

    /**
     * @return The radius of the sphere
     */
    public double radius() {
        return radius;
    }

    /**
     * @return The string name for the shape.
     */
    public String name() {
        return "sphere";
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
