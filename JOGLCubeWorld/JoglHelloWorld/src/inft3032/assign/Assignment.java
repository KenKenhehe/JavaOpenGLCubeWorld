// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
// Adapted 2015/16 Game Engines and Graphics

package inft3032.assign;

import inft3032.scene.Scene;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

public class Assignment {
	static GLCanvas  canvas;
	public static void main(String[] args) {

		String sceneFile = null;
		int width = 500;
		int height = 500;

		for (int i=0; i < args.length; i++) {
			if (args[i].equals("-scene")) {
				sceneFile = args[++i];
			}
			else if (args[i].equals("-width")) {
				width = Integer.parseInt(args[++i]);
			}
			else if (args[i].equals("-height")) {
				height = Integer.parseInt(args[++i]);
			}
		}
		
		System.out.println("Game Engines and Graphics Assignment");
		if (sceneFile == null || width <= 0 || height <= 0) {
			System.out.println("Usage: java Assignment -scene [scene file] -width [width] -h [height]");
			System.exit(1);
		}

		Scene scene = new Scene();
		scene.read(sceneFile);
		
		GLProfile profile = GLProfile.get(GLProfile.GL3);
		GLCapabilities caps = new GLCapabilities(profile);
		caps.setDoubleBuffered(true);
		caps.setDepthBits(24);
		canvas = new GLCanvas(caps);
		AssignGLListener listener = new AssignGLListener(scene);
		canvas.addGLEventListener(listener);
		canvas.addKeyListener(listener);
		
		JFrame frame = new JFrame("COMP4015 Assignment 1");
		frame.setSize(500,500);
		frame.add(canvas);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}



	public static GLCanvas getGLcanvas() {
		return canvas;
	}
}
