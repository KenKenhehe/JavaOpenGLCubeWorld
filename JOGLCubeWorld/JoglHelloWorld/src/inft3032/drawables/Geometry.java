// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;
import inft3032.math.Vector3;

import java.util.ArrayList;
import java.util.Map;

public class Geometry {
	
	public static class Vertex {
		Vector3 pos;
		Vector3 norm;
		Vector3 texCoord;
	}
	
	public static class Face {
		Material mat;
		Vertex v1;
		Vertex v2;
		Vertex v3;
	}
	
	public ArrayList<Vector3> verts;
	public ArrayList<Vector3> normals;
	public ArrayList<Vector3> texCoords;
	public ArrayList<Face>	faces;
	
	public Map<String, Material> materials;
	
	public Geometry() {}

	
	


}
