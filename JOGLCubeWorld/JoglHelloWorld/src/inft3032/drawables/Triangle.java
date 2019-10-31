// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import inft3032.assign.AssignGLListener;
import inft3032.assign.Assignment;
import inft3032.assign.Shader;
import inft3032.math.Matrix4;
import inft3032.math.MatrixFactory;
import inft3032.math.Vector3;
import inft3032.math.Vector4;
import inft3032.scene.Camera;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.*;
import java.util.Scanner;

/**
 * This class represents a Triangle polygon.
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public class Triangle extends Shape {
	
	Vertex v1;
	Vertex v2;
	Vertex v3;
	
	Shader shader;
	int vao = 0;
	int vbo = 0;

	/**
	 * Constructs a new Triangle object using the vertices specified.
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 */
	public Triangle(Vertex v1, Vertex v2, Vertex v3, Material m) {
		super(m);
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;

	}



	/**
	 * OpenGL initialisation for the Triangle.
	 */
	public void init(GL3 gl) {
		if(AssignGLListener.drawMode == AssignGLListener.DrawMode.SIMPLE)
			simpleTriangleInit(gl);
		else if(AssignGLListener.drawMode == AssignGLListener.DrawMode.VERTEX)
			VertextTriangleInit(gl);
		else if(AssignGLListener.drawMode == AssignGLListener.DrawMode.TRANSFORM)
			TransformInit(gl);
	}
	

	/**
	 * Renders the triangle to the current GL context.
	 */
	public void draw(GL3 gl) {
		if(AssignGLListener.drawMode == AssignGLListener.DrawMode.SIMPLE)
			simpleTriangleDraw(gl);
		else if(AssignGLListener.drawMode == AssignGLListener.DrawMode.VERTEX)
			VertextTriangleDraw(gl);
		else if(AssignGLListener.drawMode == AssignGLListener.DrawMode.TRANSFORM)
			TransformDraw(gl);
	}


	public void drawPrimitive(GL3 gl, int primitiveType, float[] vertices, int numberOfVertex){
		gl.glBufferData(GL3.GL_ARRAY_BUFFER,  vertices.length*4,  FloatBuffer.wrap(vertices),  GL3.GL_STATIC_DRAW);
		gl.glDrawArrays(primitiveType,  0,  numberOfVertex);
	}

	public void simpleTriangleInit(GL3 gl){
		float[] vertices = {v1.pos.getX(), v1.pos.getY(), v1.pos.getZ(),
				v2.pos.getX(), v2.pos.getY(), v2.pos.getZ(),
				v3.pos.getX(), v3.pos.getY(), v3.pos.getZ()};
		//passShaderByString("shaders/SimpleShader.vert", "shaders/SimpleShader.frag", gl);
		int[] temp = new int[]{1};
		gl.glGenVertexArrays(1, IntBuffer.wrap(temp));
		int vao = temp[0];
		gl.glBindVertexArray(vao);

		gl.glGenBuffers(1, IntBuffer.wrap(temp));
		int vbo = temp[0];
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo);

		gl.glVertexAttribPointer(0,3,GL3.GL_FLOAT, false, 3*4, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glCullFace(GL.GL_BACK);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER,  vertices.length*4,  FloatBuffer.wrap(vertices),  GL3.GL_STATIC_DRAW);
	}

	public void simpleTriangleDraw(GL3 gl){
		gl.glDrawArrays(GL.GL_TRIANGLES,  0,  3);
	}

	public void VertextTriangleInit(GL3 gl){
		float[] vertices = {v1.pos.getX(), v1.pos.getY(), v1.pos.getZ(),
				v1.colour.getX(), v1.colour.getY(), v1.colour.getZ(),

				v2.pos.getX(), v2.pos.getY(), v2.pos.getZ(),
				v2.colour.getX(), v2.colour.getY(), v2.colour.getZ(),

				v3.pos.getX(), v3.pos.getY(), v3.pos.getZ(),
				v3.colour.getX(), v3.colour.getY(), v3.colour.getZ()
		};

		int[] temp = new int[]{1};
		gl.glGenVertexArrays(1, IntBuffer.wrap(temp));
		int vao = temp[0];
		gl.glGenBuffers(1, IntBuffer.wrap(temp));
		int vbo = temp[0];
		gl.glBindVertexArray(vao);
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo);
		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6 * 4, 0);
		gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6 * 4, 3 * 4);
		gl.glCullFace(GL.GL_BACK);
		gl.glEnableVertexAttribArray(0);
		gl.glEnableVertexAttribArray(1);

		gl.glBufferData(GL3.GL_ARRAY_BUFFER,  vertices.length*4,  FloatBuffer.wrap(vertices),  GL3.GL_STATIC_DRAW);
	}

	public void VertextTriangleDraw(GL3 gl){
		gl.glDrawArrays(GL.GL_TRIANGLES,  0,  3);
	}

	public void TransformInit(GL3 gl){
		float[] vertices = {v1.pos.getX(), v1.pos.getY(), v1.pos.getZ(),
				v1.colour.getX(), v1.colour.getY(), v1.colour.getZ(),

				v2.pos.getX(), v2.pos.getY(), v2.pos.getZ(),
				v2.colour.getX(), v2.colour.getY(), v2.colour.getZ(),

				v3.pos.getX(), v3.pos.getY(), v3.pos.getZ(),
				v3.colour.getX(), v3.colour.getY(), v3.colour.getZ()
		};

		int[] temp = new int[]{1};

		gl.glGenVertexArrays(1, IntBuffer.wrap(temp));
		vao = temp[0];
		gl.glBindVertexArray(vao);

		gl.glGenBuffers(1, IntBuffer.wrap(temp));
		vbo = temp[0];
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo);

		gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6 * 4, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6 * 4, 3 * 4);
		gl.glEnableVertexAttribArray(1);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER,  vertices.length*4,  FloatBuffer.wrap(vertices),  GL3.GL_STATIC_DRAW);
	}

	int increment = 0;
	boolean canRotateLeft = false;
	public void TransformDraw(GL3 gl){
		gl.glBindVertexArray(vao);
		gl.glDrawArrays(GL.GL_TRIANGLES,  0,  3);
	}

}
