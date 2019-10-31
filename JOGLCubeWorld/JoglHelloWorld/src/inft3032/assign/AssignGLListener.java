// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//

package inft3032.assign;

import inft3032.KeyInput;
import inft3032.drawables.Box;
import inft3032.drawables.Material;
import inft3032.drawables.Shape;
import inft3032.drawables.Triangle;
import inft3032.drawables.Vertex;
import inft3032.lighting.PointLight;
import inft3032.math.Matrix4;
import inft3032.math.MatrixFactory;
import inft3032.math.Vector3;
import inft3032.math.Vector4;
import inft3032.scene.Scene;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;


public class AssignGLListener implements GLEventListener, KeyListener {

	Scene scene;
	Shader shader;
	SceneMode sceneMode = SceneMode.LIGHTING;
	KeyInput keyInput;
	public static DrawMode drawMode = DrawMode.TRANSFORM;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		char value = e.getKeyChar();
		if(value == 'a'){
			scene.camera.setPosition(new Vector3(scene.camera.getPosition().getX() - 0.1,
					scene.camera.getPosition().getY(),
					scene.camera.getPosition().getZ()));

		}

		if(value == 'd'){
			scene.camera.setPosition(new Vector3(scene.camera.getPosition().getX() + 0.1,
					scene.camera.getPosition().getY(),
					scene.camera.getPosition().getZ()));
		}

		if(value == 'w'){
			scene.camera.setPosition(new Vector3(scene.camera.getPosition().getX(),
					scene.camera.getPosition().getY(),
					scene.camera.getPosition().getZ() - 0.1f));
		}

		if(value == 's'){
			scene.camera.setPosition(new Vector3(scene.camera.getPosition().getX(),
					scene.camera.getPosition().getY() ,
					scene.camera.getPosition().getZ() + 0.1f));
		}


		if(value == 'x'){
			for (Shape s : scene.shapes) {
				s.adjustment += 0.01f;
				System.out.println(s.adjustment);
			}
		}
		if(value == 'z'){
			for (Shape s : scene.shapes) {
				s.adjustment -= 0.01f;
				System.out.println(s.adjustment);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Released");
	}

	public enum  SceneMode{
		SIMPLETRIANGLE,
		VERTEXTRIANGLE,
		PERSPECTIVE,
		SIMPLEBOX,
		BOXWORLD,
		TEXTUREDBOX,
		LIGHTING,
		INTERACTIVE
	}

	public enum DrawMode{
		SIMPLE, VERTEX, TRANSFORM
	}
	public AssignGLListener(Scene s) {
		keyInput = new KeyInput();
		this.scene = s;
	}
	
	// Called once at the start. Initialisation code goes here
	public void init(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		if(sceneMode == SceneMode.SIMPLETRIANGLE){
			passShaderByString("shaders/SimpleShader.vert", "shaders/SimpleShader.frag", gl);
			shader.setUniform("u_Color", new Vector4(1f,0,0,1), gl);
		}
		else if(sceneMode == SceneMode.VERTEXTRIANGLE){
			passShaderByString("shaders/VertexColour.vert", "shaders/VertexColour.frag", gl);
		}
		else if(sceneMode == SceneMode.PERSPECTIVE){
			passShaderByString("shaders/Transform.vert", "shaders/VertexColour.frag", gl);
		}
		else if(sceneMode == SceneMode.SIMPLEBOX){
			passShaderByString("shaders/Transform.vert", "shaders/Transform.frag", gl);
		}
		else if(sceneMode == SceneMode.BOXWORLD){
			passShaderByString("shaders/Transform.vert", "shaders/Transform.frag", gl);
		}
		else if(sceneMode == SceneMode.TEXTUREDBOX){
			passShaderByString("shaders/Transform.vert", "shaders/TexturedTransform.frag", gl);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnable(GL.GL_BLEND);
			for(int i = 0; i < scene.textures.length; i++){
				scene.textures[i].init(gl);
			}
			shader.setUniform("u_Texture", 0, gl);
		}
		else if(sceneMode == SceneMode.INTERACTIVE){
			passShaderByString("shaders/Transform.vert", "shaders/Transform.frag", gl);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnable(GL.GL_BLEND);
			for(int i = 0; i < scene.textures.length; i++){
				scene.textures[i].init(gl);
			}
			shader.setUniform("u_Texture", 0, gl);

		}
		else if(sceneMode == SceneMode.LIGHTING){
			passShaderByString("shaders/Transform.vert", "shaders/LightedTransform.frag", gl);
			shader.setUniform("lightPos", scene.lights[0].location, gl);
			shader.setUniform("lightColour", scene.lights[0].colour, gl);
			PointLight light = (PointLight)(scene.lights[0]);
			shader.setUniform("constantAtten", light.constantAttenuation, gl);
			shader.setUniform("linearAtten", light.linearAttenuation, gl);
			shader.setUniform("quadraticAtten", light.quadraticAttenuation, gl);
			shader.setUniform("viewPos", scene.camera.getPosition(), gl);
		}

		gl.glCullFace(GL.GL_BACK);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		for (Shape s : scene.shapes) {
			s.init(gl);
			shader.setUniform("u_diffuse", s.material.diffuse,gl);
			shader.setUniform("u_ambient", s.material.ambient, gl);
			shader.setUniform("u_specular", s.material.specular, gl);
		}

	}
	
	// Called every frame. You should have your update and render code here
	public void display(GLAutoDrawable drawable) {


		GL3 gl = drawable.getGL().getGL3();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);


		Matrix4 projection =  MatrixFactory.perspective(
				scene.camera.getHeightAngle(), scene.camera.getAspectRatio(), 0.1f, 80);
		shader.setUniform("projection",projection, gl);

		Matrix4 view = MatrixFactory.lookInDirection(scene.camera.getPosition(),
				scene.camera.getDirection(),
				scene.camera.getUp());
		shader.setUniform("view",
				view, gl);

		for (Shape s : scene.shapes) {
			shader.setUniform("model", s.transform, gl);
			shader.setUniform("u_diffuse", s.material.diffuse,gl);
			shader.setUniform("u_ambient", s.material.ambient, gl);
			shader.setUniform("u_specular", s.material.specular, gl);
			s.draw(gl);
			if(sceneMode == SceneMode.LIGHTING){
				shader.setUniform("lightPos", scene.lights[0].location, gl);
				shader.setUniform("lightColour", scene.lights[0].colour, gl);
				PointLight light = (PointLight)(scene.lights[0]);
				shader.setUniform("constantAtten", light.constantAttenuation, gl);
				shader.setUniform("linearAtten", light.linearAttenuation, gl);
				shader.setUniform("quadraticAtten", light.quadraticAttenuation, gl);
				shader.setUniform("adjustment", new Vector3(s.adjustment, s.adjustment, s.adjustment), gl);
			}
		}
		if(sceneMode == SceneMode.INTERACTIVE){
			if(keyInput.keys[0]){
				System.out.println("Left");
			}
		}
		Assignment.getGLcanvas().repaint();

	}

	// Called once at the end. You should clean up any resources here
	public void dispose(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		for (Shape s : scene.shapes) {
			s.dispose(gl);
		}
	}

	// Called when the window is resized. You should update your projection matrix here.
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL3 gl = drawable.getGL().getGL3();
		scene.camera.setAspectRatio((float) width / (float)height);
		shader.setUniform("projection", MatrixFactory.perspective(
				scene.camera.getHeightAngle(),(float) width / (float)height, 0, 80), gl);
	}

	public void passShaderByString(String vertexPath, String fragmentPath, GL3 gl){
		String vertShader = null;
		try {
			vertShader = new Scanner(new File(vertexPath)).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String fragShader = null;
		try {
			fragShader = new Scanner(new File(fragmentPath)).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		shader = new Shader(vertShader, fragShader);
		shader.compile(gl);
		shader.enable(gl);
	}
}
