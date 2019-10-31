// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//

package inft3032.assign;

import inft3032.math.Matrix4;
import inft3032.math.Vector3;
import inft3032.math.Vector4;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import javax.media.opengl.*;


/**
 * A class representing an OpenGL shader!
 * 
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public class Shader {
	
	/**
	 * The source code for our vertex shader
	 */
	private String vertSource;

	/**
	 * The source code for our fragment shader
	 */
	private String fragSource;
	
	/**
	 * The ID of our shader program
	 */
	private int shaderID;
	
	/**
	 * The ID of our vertex shader
	 */
	private int vs;
	
	/**
	 * The ID of our fragment shader
	 */
	private int fs;

	/**
	 * Constructs a shader from strings containing source code.
	 * 
	 * This is useful if you are, for example, creating a code editor with preview window.
	 * 
	 * @param vertexProgram   The source code for the vertex shader
	 * @param fragmentProgram The source code for teh fragment shader
	 */
	public Shader(String vertexProgram, String fragmentProgram) {
		this.vertSource = vertexProgram;
		this.fragSource = fragmentProgram;
	}
	
	
	/**
	 * Constructs a shader object from files containing the shader source code.
	 * 
	 * @param vertexProgram The file containing the vertex shader (.vert)
	 * @param fragmentProgram The file containing the fragment shader (.frag)
	 * 
	 * @throws FileNotFoundException If the files can't be opened.
	 */
	public Shader(File vertexProgram, File fragmentProgram) throws FileNotFoundException {
		this.vertSource = new Scanner(vertexProgram).useDelimiter("\\A").next();
		this.fragSource = new Scanner(fragmentProgram).useDelimiter("\\A").next();
	}
	
	
	/**
	 * Compiles and links the shader ready for use.
	 * 
	 * @param gl Our lovely GL context
	 */
	@SuppressWarnings("static-access")
	public void compile(GL3 gl) {
		String[] sourceString = new String[1];
		int[] sourceLength = new int[1];
		int[] result = new int[1];
		int[] logSize = new int[1];

		vs = gl.glCreateShader(gl.GL_VERTEX_SHADER);
		fs = gl.glCreateShader(gl.GL_FRAGMENT_SHADER);
		
		sourceString[0] = this.vertSource;
		sourceLength[0] = this.vertSource.length();
		gl.glShaderSource(vs, 1, sourceString, sourceLength, 0);
		gl.glCompileShader(vs);
		
		// Get the log and throw an exception if the shader didn't compile
		gl.glGetShaderiv(vs, gl.GL_COMPILE_STATUS, result, 0);
		if (result[0] == gl.GL_FALSE) {
			gl.glGetShaderiv(vs, gl.GL_INFO_LOG_LENGTH, IntBuffer.wrap(logSize));

			byte[] buffer = new byte[logSize[0]];
			gl.glGetShaderInfoLog(vs, logSize[0], null, ByteBuffer.wrap(buffer));
			String log = new String(buffer);
			throw new RuntimeException("Could not compile vertex shader!\n" + log);	
		}

		sourceString[0] = this.fragSource;
		sourceLength[0] = this.fragSource.length();
		gl.glShaderSource(fs, 1, sourceString, sourceLength, 0);
		gl.glCompileShader(fs);
		
		// Get the log and throw an exception if the shader didn't compile
		gl.glGetShaderiv(fs, gl.GL_COMPILE_STATUS, result, 0);
		if (result[0] == gl.GL_FALSE) {
			gl.glGetShaderiv(fs, gl.GL_INFO_LOG_LENGTH, IntBuffer.wrap(logSize));

			byte[] buffer = new byte[logSize[0]];
			gl.glGetShaderInfoLog(fs, logSize[0], null, ByteBuffer.wrap(buffer));
			String log = new String(buffer);
			throw new RuntimeException("Could not compile fragment shader!\n" + log);	
		}
		

		// Great, now we have two working shaders, now lets link them to our program
		shaderID = gl.glCreateProgram();
		gl.glAttachShader(shaderID, vs);
		gl.glAttachShader(shaderID, fs);
		gl.glLinkProgram(shaderID);
		
		// find out whether linking worked
		gl.glGetProgramiv(shaderID, gl.GL_LINK_STATUS, IntBuffer.wrap(result));

		// Get the log to find out the link error
		if (result[0] == gl.GL_FALSE) {
			gl.glGetProgramiv(shaderID, gl.GL_INFO_LOG_LENGTH, IntBuffer.wrap(logSize));

			byte[] buffer = new byte[logSize[0]];
			gl.glGetProgramInfoLog(shaderID, logSize[0], null, ByteBuffer.wrap(buffer));
			String log = new String(buffer);
			throw new RuntimeException("Could not link shader!\n" + log);
		}
	}
	
	/**
	 * Enables this shader, ready for drawing.
	 * @param gl
	 */
	public void enable(GL3 gl) {
		gl.glUseProgram(shaderID);
	}
	
	/**
	 * Disables this shader.
	 * @param gl
	 */
	public void disable(GL3 gl) {
		gl.glUseProgram(0);
	}
	
	public void setUniform(String name, Matrix4 matrix, GL3 gl) {
		int location = gl.glGetUniformLocation(shaderID, name);
		if (location != -1)
			gl.glUniformMatrix4fv(location, 1, false, FloatBuffer.wrap(matrix.toOpenGL()));
	}
	
	public void setUniform(String name, float value, GL3 gl) {
		int location = gl.glGetUniformLocation(shaderID, name);
		if (location != -1)
			gl.glUniform1f(location, value);
	}
	
	public void setUniform(String name, Vector3 vector, GL3 gl) {
		int location = gl.glGetUniformLocation(shaderID, name);
		if (location != -1)
			gl.glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
	}

	public void setUniform(String name, Vector4 vector, GL3 gl) {
		int location = gl.glGetUniformLocation(shaderID, name);
		if (location != -1)
			gl.glUniform4f(location, vector.getX(), vector.getY(), vector.getZ(), vector.getW());
	}

	public int GetShaderID(){
		return shaderID;
	}
}
