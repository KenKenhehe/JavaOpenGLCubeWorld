// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import inft3032.math.*;

/**
 * Material Class.
 * 
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public class Material {
	public int index;
	
	/**
	 * Ambient reflection colour
	 */
	public Vector3 ambient;
	
	/**
	 * Diffuse reflection colour
	 */
	public Vector3 diffuse;
	
	/**
	 * Specular reflection colour
	 */
	public Vector3 specular;
	
	/**
	 * Emmissive colour
	 */
	public Vector3 emissive;
	
	/**
	 * Specular exponent
	 */
	public float specularExponent;
	
	/**
	 * Transparency (0-1)
	 */
	public float transparency;
	
	/**
	 * Refractive Index
	 */
	public float refractiveIndex;
	
	/**
	 * Diffuse texture image
	 */
	public Texture diffuseMap;
	
	public String extraInfo;
}
