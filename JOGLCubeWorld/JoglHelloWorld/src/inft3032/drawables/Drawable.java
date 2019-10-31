// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import javax.media.opengl.GL3;


/**
 * An object that can be drawn to our OpenGL canvas.
 * 
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public interface Drawable {
	
	/**
	 * Completes any OpenGL initialisation required to draw this object.
	 * 
	 * For example, builds vertex buffers, etc.
	 * 
	 * @param gl
	 */
	public void init(GL3 gl);
	
	/**
	 * Draws the object to the OpenGL canvas.
	 * 
	 * @param gl
	 */
	public void draw(GL3 gl);

}
