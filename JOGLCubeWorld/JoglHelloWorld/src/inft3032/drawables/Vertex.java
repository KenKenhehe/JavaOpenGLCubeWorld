// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import inft3032.math.*;


/**
 * Class represents a Vertex with typical vertex properties.
 * 
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public class Vertex {
	public int index;

	/**
	 * The position of this vertex
	 */
	public Vector3 pos;
	
	/**
	 * The surface normal for this vertex
	 */
	public Vector3 normal;
	
	/**
	 * The Texture coordinate for this vertex.
	 */
	public Vector2 texCoord;
	
	/**
	 * The vertex colour (may or may not be used)
	 * 
	 * Colours are mapped like this:
	 * 
	 * R = colour.getX()
	 * G = colour.getY()
	 * B = colour.getZ()
	 * A = colour.getW()
	 * 
	 * And should be in the range of 0-1.0
	 */
	public Vector3 colour;
}
