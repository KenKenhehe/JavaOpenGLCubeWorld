// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import inft3032.assign.AssignGLListener;
import inft3032.assign.Assignment;
import inft3032.assign.Shader;
import inft3032.math.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Scanner;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;


public class Box extends Shape {
	private float width;
	private float height;
	private float depth;
	boolean TEXTERED = false;
	int vao;
	int vbo;
	int ibo;
	int[] temp = new int[]{1};
	public Box(float width, float height, float depth, Material m) {
		super(m);
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public void init(GL3 gl) {
		float[] vertices = VertextData();
		float[] normals = {
				0.0f, 1.0f, 0.0f,// top
				0.0f, 1.0f, 0.0f,// top

				0.0f, -1, 0.0f, //bottom
				0.0f, -1, 0.0f, //bottom

				1, 0.0f, 0.0f, // right
				1, 0.0f, 0.0f, // right

				-1, 0.0f, 0f, // left
				-1, 0.0f, 0f, // left

				0.0f, 0.0f,-1f,// front
				0.0f, 0.0f,-1f,// front

				0.0f, 0.0f, 1f, //back
				0.0f, 0.0f, 1f, //back

		};
		float[] texCoords = {

				//top
				0, 0,
				1, 0,
				1, 1,
				0, 1,

				//bottom
				0, 0,
				1, 0,
				1, 1,
				0, 1,
				//right
				0, 0,
				1, 0,
				1, 1,
				0, 1,

				//left
				0, 0,
				1, 0,
				1, 1,
				0, 1,

				//front
				0, 0,
				1, 0,
				1, 1,
				0, 1,

				//back
				0, 0,
				1, 0,
				1, 1,
				0, 1
		};
		int[] indices = {

				0, 4, 5,
				5, 1, 0, // top

				2, 6, 7,
				7, 3, 2, //bottom

				1, 5, 6,
				6, 2, 1, // right

				0, 4, 7,
				7, 3, 0, // left

				0, 1, 2,
				2, 3, 0, // front

				4, 5, 6,
				6, 7, 4  //back
		};
//		front1.pos = new Vector3(0, height, depth); 0: front top left
//		front2.pos = new Vector3(width, height, depth); 1 front top right
//		front3.pos = new Vector3(width, 0, depth); 2 front bottom right
//		front4.pos = new Vector3(0, 0,depth); 3 front bottom left
//
//		back1.pos = new Vector3(0, height, 0); 4 back top left
//		back2.pos = new Vector3(width, height, 0); 5 back top right
//		back3.pos = new Vector3(width, 0, 0); 6 back bottom right
//		back4.pos = new Vector3(0, 0, 0); 7 back bottom left

		//int[] temp = new int[]{1};
		gl.glGenVertexArrays(1, IntBuffer.wrap(temp));
		vao = temp[0];
		gl.glBindVertexArray(vao);
		gl.glGenBuffers(1, IntBuffer.wrap(temp));
		vbo = temp[0];

		int[] tempIndex = new int[1];
		gl.glGenBuffers(1, IntBuffer.wrap(tempIndex));
		ibo = tempIndex[0];

		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER,  vertices.length * 4,  FloatBuffer.wrap(vertices),  GL3.GL_STATIC_DRAW);

		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, ibo);
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER,  indices.length*4,  IntBuffer.wrap(indices),  GL3.GL_STATIC_DRAW);

		gl.glVertexAttribPointer(0,3,GL3.GL_FLOAT, false, 6*4, 0);
		gl.glVertexAttribPointer(1, 3, GL3.GL_FLOAT, false, 6*4, 3 * 4);
		//gl.glVertexAttribPointer(2, 3, GL3.GL_FLOAT, false, 9*4, 6 * 4);
		//gl.glVertexAttribPointer(3, 2, GL3.GL_FLOAT, false, 11*4, 9 * 4);
		gl.glEnableVertexAttribArray(0);
		gl.glEnableVertexAttribArray(1);
		//gl.glEnableVertexAttribArray(2);
		//gl.glEnableVertexAttribArray(3);

		gl.glGenBuffers(1, IntBuffer.wrap(temp));
		int texVbo = temp[0];
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, texVbo);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER,  texCoords.length*4,  FloatBuffer.wrap(texCoords),  GL3.GL_STATIC_DRAW);

		gl.glVertexAttribPointer(3, 2, GL3.GL_FLOAT, false, 2*4, 0);
		gl.glEnableVertexAttribArray(3);

		gl.glGenBuffers(1, IntBuffer.wrap(temp));
		int normalVbo = temp[0];
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, normalVbo);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER,  normals.length*4,  FloatBuffer.wrap(normals),  GL3.GL_STATIC_DRAW);

		gl.glVertexAttribPointer(2, 3, GL3.GL_FLOAT, false, 3*4, 0);
		gl.glEnableVertexAttribArray(2);
	}

	public void draw(GL3 gl) {
		gl.glBindVertexArray(vao);
		gl.glDrawElements(GL3.GL_TRIANGLES, 36, GL3.GL_UNSIGNED_INT, 0);
		Assignment.getGLcanvas().repaint();
	}

	public float[] VertextData(){
		Vertex front1 = new Vertex();
		Vertex front2 = new Vertex();
		Vertex front3 = new Vertex();
		Vertex front4 = new Vertex();

		Vertex back1 = new Vertex();
		Vertex back2 = new Vertex();
		Vertex back3 = new Vertex();
		Vertex back4 = new Vertex();

		front1.pos = new Vector3(-width/2, height/2, depth/2);
		front2.pos = new Vector3(width/2, height/2, depth/2);
		front3.pos = new Vector3(width/2, -height/2, depth/2);
		front4.pos = new Vector3(-width/2, -height/2, depth/2);

		back1.pos = new Vector3(-width/2, height/2, -depth/2);
		back2.pos = new Vector3(width/2, height/2, -depth/2);
		back3.pos = new Vector3(width/2, -height/2, -depth/2);
		back4.pos = new Vector3(-width/2, -height/2, -depth/2);

		front1.normal = new Vector3(-1 , 1 , -1).unit();
		front2.normal = new Vector3(1 , 1 , -1).unit();
		front3.normal = new Vector3(1 , -1 , -1).unit();
		front4.normal = new Vector3(-1 , -1 , -1).unit();

		back1.normal = new Vector3(-1 , 1, 1).unit();
		back2.normal = new Vector3(1 , 1, 1).unit();
		back3.normal = new Vector3(1 , -1, 1).unit();
		back4.normal = new Vector3(-1 , -1, 1).unit();
        //		front1.pos = new Vector3(0, height, depth); 0: front top left
//		front2.pos = new Vector3(width, height, depth); 1 front top right
//		front3.pos = new Vector3(width, 0, depth); 2 front bottom right
//		front4.pos = new Vector3(0, 0,depth); 3 front bottom left
//
//		back1.pos = new Vector3(0, height, 0); 4 back top left
//		back2.pos = new Vector3(width, height, 0); 5 back top right
//		back3.pos = new Vector3(width, 0, 0); 6 back bottom right
//		back4.pos = new Vector3(0, 0, 0); 7 back bottom left


        front1.colour = new Vector3(material.diffuse.getX(), material.diffuse.getY(), material.diffuse.getZ());
		front2.colour = new Vector3(material.diffuse.getX(), material.diffuse.getY(), material.diffuse.getZ());
		front3.colour = new Vector3(material.diffuse.getX(), material.diffuse.getY(), material.diffuse.getZ());
		front4.colour = new Vector3(material.diffuse.getX(), material.diffuse.getY(), material.diffuse.getZ());

		back1.colour = new Vector3(material.diffuse.getX(), material.diffuse.getY(), material.diffuse.getZ());
		back2.colour = new Vector3(material.diffuse.getX(), material.diffuse.getY(), material.diffuse.getZ());
		back3.colour = new Vector3(material.diffuse.getX(), material.diffuse.getY(), material.diffuse.getZ());
		back4.colour = new Vector3(material.diffuse.getX(), material.diffuse.getY(), material.diffuse.getZ());

//		front1.texCoord = new Vector2(0, 0);
//		front2.texCoord = new Vector2(1, 0);
//		front3.texCoord = new Vector2(1, 1);
//		front4.texCoord = new Vector2(0, 1);
//
//		back1.texCoord = new Vector2(0, 0);
//		back2.texCoord = new Vector2(1, 0);
//		back3.texCoord = new Vector2(1, 1);
//		back4.texCoord = new Vector2(0, 1);



		float[] vertexData = {
			front1.pos.getX(), front1.pos.getY(), front1.pos.getZ(),
			front1.colour.getX(), front1.colour.getY(), front1.colour.getZ(),
			//front1.normal.getX(), front1.normal.getY(), front1.normal.getZ(),
			//front1.texCoord.getX(), front1.texCoord.getY(),

			front2.pos.getX(), front2.pos.getY(), front2.pos.getZ(),
			front2.colour.getX(), front2.colour.getY(), front2.colour.getZ(),
			//front2.normal.getX(), front2.normal.getY(), front2.normal.getZ(),
			//front2.texCoord.getX(), front2.texCoord.getY(),

			front3.pos.getX(), front3.pos.getY(), front3.pos.getZ(),
			front3.colour.getX(), front3.colour.getY(), front3.colour.getZ(),
			//front3.normal.getX(), front3.normal.getY(), front3.normal.getZ(),
			//front3.texCoord.getX(), front3.texCoord.getY(),

			front4.pos.getX(), front4.pos.getY(), front4.pos.getZ(),
			front4.colour.getX(), front4.colour.getY(), front4.colour.getZ(),
			//front4.normal.getX(), front4.normal.getY(), front4.normal.getZ(),
			//front4.texCoord.getX(), front4.texCoord.getY(),

			back1.pos.getX(), back1.pos.getY(),	back1.pos.getZ(),
			back1.colour.getX(), back1.colour.getY(), back1.colour.getZ(),
			//back1.normal.getX(), back1.normal.getY(), back1.normal.getZ(),
			//back1.texCoord.getX(), back1.texCoord.getY(),

			back2.pos.getX(), back2.pos.getY(),	back2.pos.getZ(),
			back2.colour.getX(), back2.colour.getY(), back2.colour.getZ(),
			//back2.normal.getX(), back2.normal.getY(), back2.normal.getZ(),
			//back2.texCoord.getX(), back2.texCoord.getY(),

			back3.pos.getX(), back3.pos.getY(),	back3.pos.getZ(),
			back3.colour.getX(), back3.colour.getY(), back3.colour.getZ(),
			//back3.normal.getX(), back3.normal.getY(), back3.normal.getZ(),
			//back3.texCoord.getX(), back3.texCoord.getY(),

			back4.pos.getX(), back4.pos.getY(),	back4.pos.getZ(),
			back4.colour.getX(), back4.colour.getY(), back4.colour.getZ(),
			//back4.normal.getX(), back4.normal.getY(), back4.normal.getZ(),
			//back4.texCoord.getX(), back4.texCoord.getY(),
		};
		//top2.pos = new Vector3()
		return vertexData;
	}

	@Override
	public void dispose(GL3 gl){
		gl.glDeleteVertexArrays(1, IntBuffer.wrap(temp));
		gl.glDeleteBuffers(1, IntBuffer.wrap(temp));
	}
}
