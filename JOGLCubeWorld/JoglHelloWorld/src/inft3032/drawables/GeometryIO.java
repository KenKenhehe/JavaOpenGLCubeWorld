// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;
import inft3032.drawables.Geometry.Face;
import inft3032.drawables.Geometry.Vertex;
import inft3032.math.Vector3;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class GeometryIO {
	
	public static Geometry loadOBJ(File pathToOBJ) throws IOException{
		
		Geometry g = new Geometry();
		BufferedReader in = new BufferedReader(new FileReader(pathToOBJ));
		
		Material activeMaterial = null;
		
		String line;
		while ((line = in.readLine()) != null) {
			Scanner parts = new Scanner(line);
			
			String type = parts.next();
			if (type.equals("mtllib")) {
				String mtlFile = parts.next();
				loadMaterials(mtlFile, g.materials);
			}
			else if (type.equals("v")) {
				float x = parts.nextFloat();
				float y = parts.nextFloat();
				float z = parts.nextFloat();
				g.verts.add(new Vector3(x,y,z));
			}
			else if (type.equals("vt")) {
				float x = parts.nextFloat();
				float y = parts.nextFloat();
				g.texCoords.add(new Vector3(x,y,0));
			}
			else if (type.equals("vn")) {
				float x = parts.nextFloat();
				float y = parts.nextFloat();
				float z = parts.nextFloat();
				g.normals.add(new Vector3(x,y,z));
			}
			else if (type.equals("f")) {
				Face f = new Face();
				f.mat = activeMaterial;

				// new face;
				String v1 = parts.next();
				String v2 = parts.next();
				String v3 = parts.next();
				
				Scanner vExtractor = new Scanner(v1);
				vExtractor.useDelimiter("/");
				int vIndex = vExtractor.nextInt() -1;
				int tIndex = vExtractor.nextInt() -1;
				int nIndex = vExtractor.nextInt() -1;
				f.v1 = new Vertex();
				f.v1.pos = g.verts.get(vIndex);
				f.v1.texCoord = g.texCoords.get(tIndex);
				f.v1.norm = g.normals.get(nIndex);

				vExtractor = new Scanner(v2);
				vExtractor.useDelimiter("/");
				vIndex = vExtractor.nextInt() -1;
				tIndex = vExtractor.nextInt() -1;
				nIndex = vExtractor.nextInt() -1;
				f.v2 = new Vertex();
				f.v2.pos = g.verts.get(vIndex);
				f.v2.texCoord = g.texCoords.get(tIndex);
				f.v2.norm = g.normals.get(nIndex);

				vExtractor = new Scanner(v3);
				vExtractor.useDelimiter("/");
				vIndex = vExtractor.nextInt() -1;
				tIndex = vExtractor.nextInt() -1;
				nIndex = vExtractor.nextInt() -1;
				f.v3 = new Vertex();
				f.v3.pos = g.verts.get(vIndex);
				f.v3.texCoord = g.texCoords.get(tIndex);
				f.v3.norm = g.normals.get(nIndex);
			}
		}
		
		in.close();
		return g;
	}
	private static Map<String, Material> loadMaterials(String mtlFile, Map<String, Material> materials) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(mtlFile));
		String line;
		Material activeMat = null;
		while ((line = in.readLine()) != null) {
			Scanner parts = new Scanner(line);
			String type = parts.next();
			if (type.equals("newmtl")) {
				String name = parts.next();
				activeMat = new Material();
				materials.put(name, activeMat);
			}
			else if (type.equals("Kd")) {
				activeMat.diffuse = new Vector3(parts.nextFloat(), parts.nextFloat(), parts.nextFloat());
			}
			else if (type.equals("Ka")) {
				activeMat.ambient = new Vector3(parts.nextFloat(), parts.nextFloat(), parts.nextFloat());
			}
			else if (type.equals("Ks")) {
				activeMat.specular = new Vector3(parts.nextFloat(), parts.nextFloat(), parts.nextFloat());
			}
			else if (type.equals("Ni")) {
				activeMat.refractiveIndex = parts.nextFloat();
			}
			else if (type.equals("Ns")) {
				activeMat.specularExponent = parts.nextFloat();
			}
			else if (type.equals("Tr")) {
				activeMat.transparency = parts.nextFloat();
			}
		}
		in.close();
		return materials;
	}

}
