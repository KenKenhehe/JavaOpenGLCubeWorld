// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import inft3032.math.Matrix4;
import inft3032.scene.Camera;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;


public abstract class Shape implements Drawable {
	public Matrix4 transform;
	public Material material;
	public float adjustment = 0;
	Camera camera;
	public Shape(Material m) {
		transform = new Matrix4();
		material = m;
	}
	public void addCamera(Camera camera){
		this.camera = camera;
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){

	}

	public void dispose(GL3 gl){

	}
}
