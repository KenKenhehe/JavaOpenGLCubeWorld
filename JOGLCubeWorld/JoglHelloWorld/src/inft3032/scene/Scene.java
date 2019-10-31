// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.scene;




import inft3032.drawables.*;
import inft3032.lighting.*;
import inft3032.math.*;

import java.io.*;
import java.util.*;


/**
 * The scene class is the majfor data structure used to store information about the
 * scene from which a picture is generated.
 * <p>The scene has an ambient and background light colour, and can contain:
 * <ol><li>A camera
 * <li>lights (positional lights, spot lights, and directed lights)
 * <li>Textures
 * <li>Material properties
 * <li>Vertices from which triangles (and possibly other scene shaps) can be constructed
 * <li>Refereces to other files containing scenes
 * <li>Shapes that can be viewed, including spheres, cylinders, cones, triangles and boxes.
 * <li>Groups, that have an associated local transformation and a list of shapes, including
 * a scene from another scene file, and nested groups.
 * </ol>
 * Typically you create a Scene object and then read the scene in from a scene file.
 * You can also write the scene out to a text file in the scene file format.
 * <p/>
 * Created by a.sobey on 27/03/2004 at 03:15:59
 *
 * @author a.sobey
 * @version 1.0
 */
public class Scene {
    final static int HASH = (int) ('#');
    final static float SMALL = 1.0E-6f;

    public Vertex[] vertices;
    public int vertexNum;
    public Material[] materials;
    public int materialNum;
    public Texture[] textures;
    public int textureNum;

    public Vector3 ambient;
    public Vector3 background;
    public Camera camera;
    public Light[] lights;
    public int lightNum;

    public List<Shape> shapes = new LinkedList<Shape>();


    public Scene() {
        vertices = new Vertex[0];
        vertexNum = -1;
        materials = new Material[0];
        materialNum = -1;
        textures = new Texture[0];
        textureNum = -1;
        ambient = new Vector3(0, 0, 0);
        background = new Vector3(0, 0, 0);
        camera = new Camera();
        lights = new Light[0];
        lightNum = -1;
    }

    public Material getMaterial(int index) {
        if (materials.length > 0 && index >=0 && index < materials.length)
            return materials[index];
        else
            return null;
    }

    //----------------- low level io for scenes --------------------------
    private static final String TABS = "\t\t\t\t\t\t\t\t\t\t";

    public static String tabs(int number) {
        return (number > 0 ? TABS.substring(0, (number <= 10 ? number : 10)) : "");
    }

    public static void write(PrintWriter out, int num, Vector2 p) {
        out.write(tabs(num) + p.getX() + ' ' + p.getY() + '\n');
    }

    public static void write(PrintWriter out, int num, Vector3 p) {
        out.write(tabs(num) + p.getX() + ' ' + p.getY() + ' ' + p.getZ() + '\n');
    }

    public static void write(PrintWriter out, int num, Vector4 p) {
        out.write(tabs(num) + p.getX() + ' ' + p.getY() + ' ' + p.getZ() + ' ' + p.getW() + '\n');
    }
    //------------------------end low level ---------------------------------

    /**
     * Private method to check that a token is read before the first Group is defined (or the first shape
     * object if no group is defined), and that the token being parsed has not already been read.
     * If token is within a group, a SceneFileException is thrown.
     * If the token has already been read, the rest of the token stream associated with this command
     * token is discarded.
     * @param in        The SceneFileStreamTokenizer being processed.
     * @param tokenName The name of the command token being processed.
     * @param inGroup   is True if have already started processing shape objects or a group.
     * @param alreadyRead is True if have already read a command of this type
     * @param discardSize The number of tokens to discard if already read a command of this type.
     * @return True if need to process this command.
     */
    private boolean check(SceneFileStreamTokenizer in, String tokenName, boolean inGroup, boolean alreadyRead, int discardSize) {
        if (inGroup)
            throw new SceneFileException("#" + tokenName + ": can not be specified within a group");
        else if (alreadyRead) {
            in.discard(discardSize, "#" + tokenName + ": Already read " + tokenName + " - ignoring");
            return false;
        } else
            return true;
    }

    /**
     * Read in the scene details from a scene file.
     * @param fileName The scene file name.
     */
    public void read(String fileName) {
        boolean cameraRead = false;
        boolean backgroundRead = false;
        boolean ambientRead = false;
        boolean lightNumRead = false;
        boolean textureNumRead = false;
        boolean materialNumRead = false;
        boolean vertexNumRead = false;
        boolean beginGroupRead = false;
        int currentTextureIndex = -1;
        int currentMaterialIndex = -1;
        int currentVertexIndex = -1;
        int currentLightIndex = -1;
        Trace.msg("Reading scene file" + fileName);
        try {
            // Create the SceneFileStreamBuffer for input from the scene file.
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            SceneFileStreamTokenizer tokens = new SceneFileStreamTokenizer(in);
            // Now loop to process tokens until end of file or an error occurs
            while (tokens.nextToken() != StreamTokenizer.TT_EOF) {
                if (tokens.ttype != HASH)
                    throw new SceneFileException("Command does not start with " + '#');
                String token = tokens.readString();
                System.out.println("Processing token "+token);
                // token now contains a scene file command name, so massive if-statement to process it
                /**
                 * #camera
                 */
                if (token.compareToIgnoreCase("camera") == 0) {
                    if (check(tokens, token, beginGroupRead, cameraRead, 10)) {
                    	Vector3 p = tokens.readVector3();
                        Vector3 d = tokens.readVector3();
                        Vector3 u = tokens.readVector3();
                        float ha = tokens.readFloat(SMALL, 90.0f);
                        // now need to set up camera
                        if (d.length() < SMALL || u.length() < SMALL) {
                            throw new SceneFileException("Camera: Zero length vector or bad half-angle when reading scene file");
                        } else if (d.cross(u).length() < SMALL) {
                            throw new SceneFileException("Camera: up vector parallel to direction vector in scene file");
                        }
                        Vector3 position = p;
                        Vector3 direction = d.unit();
                        Vector3 right = u.cross(direction).unit();
                        Vector3 up = direction.cross(right).unit();
                        float heightAngle = ha;
                        float aspectRatio = 1.0f;
                        camera = new Camera(position, direction, up, aspectRatio, heightAngle);
                        cameraRead = true;
                    }
                    /**
                     * #background
                     */
                } else if (token.compareToIgnoreCase("background") == 0) {
                    if (check(tokens, token, beginGroupRead, backgroundRead, 3)) {
                        background = tokens.readColour();
                        backgroundRead = true;
                    }
                    /**
                     * #Ambient
                     */
                } else if (token.compareToIgnoreCase("ambient") == 0){
                    if (check(tokens, token, beginGroupRead, ambientRead, 3)){
                        ambient = tokens.readColour();
                        ambientRead = true;
                    }
                /**
                 * #light_num
                 */
                }else if (token.compareToIgnoreCase("light_num") == 0){
                    if (check(tokens, token, beginGroupRead, lightNumRead, 1)){
                        lightNum = tokens.readInt(0);
                        lightNumRead = true;
                        lights = new Light[lightNum];
                    }
                /**
                 * #texture_num
                 */
                }else if (token.compareToIgnoreCase("texture_num") == 0){
                    if (check(tokens, token, beginGroupRead, textureNumRead, 1)){
                        textureNum = tokens.readInt(0);
                        textureNumRead = true;
                        textures = new Texture[textureNum];
                    }
                /**
                 * material_num
                 */
                }else if (token.compareToIgnoreCase("material_num") == 0){
                    if (check(tokens, token, beginGroupRead, materialNumRead, 1)){
                        materialNum = tokens.readInt(0);
                        materialNumRead = true;
                        materials = new Material[materialNum];
                    }
                /**
                 * #vertext_num
                 */
                }else if (token.compareToIgnoreCase("vertex_num") == 0){
                    if (check(tokens, token, beginGroupRead, vertexNumRead, 1)){
                        vertexNum = tokens.readInt(0);
                        vertexNumRead = true;
                        vertices = new Vertex[vertexNum];
                    }
                /**
                 * #texture
                 */
                }else if (token.compareToIgnoreCase("texture") == 0)
                    if (beginGroupRead)
                        throw new SceneFileException("#texture: can not be specified within a group");
                    else if (!textureNumRead)
                        throw new SceneFileException("#texture: must be after a #texture_num command");
                    else {
                        currentTextureIndex++;
                        if (currentTextureIndex >= textureNum)
                            throw new SceneFileException("#texture: more texture defined than in #texture_num command");
                        try {
                        	String f = tokens.readString();
                        	Image image = new Image(f, 0);
                        	textures[currentTextureIndex] = new Texture(currentTextureIndex, f, image);
                        } catch (Exception e) {
                            throw new SceneFileException("Error ("+e+") reading texture image file: " + fileName);
                        }
                    }
                /**
                 * #material
                 */
                else if (token.compareToIgnoreCase("material") == 0)
                    if (beginGroupRead)
                        throw new SceneFileException("#material: can not be specified within a group");
                    else if (!materialNumRead)
                        throw new SceneFileException("#material: must be defined after #material_num");
                    else {
                        currentMaterialIndex++;
                        if (currentMaterialIndex >= materialNum)
                            throw new SceneFileException("#material: more materials defined than in #material_num");
                        //@todo - should put in checks to ensure textures required for materials are read in before the material.
                        Material m = new Material();
                        
                        m.index            = currentMaterialIndex;
                        m.ambient          = tokens.readColour();
                        m.diffuse          = tokens.readColour();
                        m.specular         = tokens.readColour();
                        m.emissive         = tokens.readColour();
                        m.specularExponent = tokens.readFloat(0.0f);
                        m.transparency = tokens.readFloat(0.0f, 1.0f);
                        m.refractiveIndex = tokens.readFloat(0.0f);
                        int textureIndex = tokens.readInt(-1, textures.length-1);
                        if (textures.length != 0){ // texture index must be -1
                        	m.diffuseMap = textures[textureIndex];
                        }
                        m.extraInfo = tokens.readString();

                        materials[currentMaterialIndex] = m;
                    }
                /**
                 * #vertex
                 */
                else if (token.compareToIgnoreCase("vertex") == 0)
                    if (beginGroupRead)
                        throw new SceneFileException("#vertex: can not be specified within a group");
                    else if (!vertexNumRead)
                        throw new SceneFileException("#vertex: must be defined after #vertex_num");
                    else {
                        currentVertexIndex++;
                        if (currentVertexIndex >= vertexNum)
                            throw new SceneFileException("#vertex: more vertices defined than in #vertex_num");
                        Vertex v = new Vertex();
                       v.index = currentVertexIndex;
                       v.pos = tokens.readVector3();
                       v.normal = tokens.readVector3().unit();
                       v.texCoord = tokens.readVector2();
                       v.colour = tokens.readColour();
                        vertices[currentVertexIndex] = v;
                    }
                else if (token.compareToIgnoreCase("light_point") == 0)
                    if (beginGroupRead)
                        throw new SceneFileException("#light_point: can not be specified within a group");
                    else if (!lightNumRead)
                        throw new SceneFileException("#light_point: must be defined after #light_num");
                    else {
                        currentLightIndex++;
                        if (currentLightIndex >= lightNum)
                            throw new SceneFileException("#light_point: more lights defined than in #light_num");
                        
                        Vector3 colour = tokens.readColour();
                        Vector3 location = tokens.readVector3();
                        float constantAttenuation = tokens.readFloat(0.0f);
                        float linearAttenuation = tokens.readFloat(0.0f);
                        float quadraticAttenuation = tokens.readFloat(0.0f);
                        
                        lights[currentLightIndex] = new PointLight(colour, location, constantAttenuation, linearAttenuation, quadraticAttenuation);
                    }
                else if (token.compareToIgnoreCase("light_dir") == 0)
                    if (beginGroupRead)
                        throw new SceneFileException("#light_dir: can not be specified within a group");
                    else if (!lightNumRead)
                        throw new SceneFileException("#light_dir: must be defined after #light_num");
                    else {
                        currentLightIndex++;
                        if (currentLightIndex >= lightNum)
                            throw new SceneFileException("#light_dir: more lights defined than in #light_num");
                        
                        Vector3 colour = tokens.readColour();
                        Vector3 direction = tokens.readVector3().unit();
                        lights[currentLightIndex] = new DirectionalLight(colour, direction);
                    }
                else if (token.compareToIgnoreCase("light_spot") == 0)
                    if (beginGroupRead)
                        throw new SceneFileException("#light_spot: can not be specified within a group");
                    else if (!lightNumRead)
                        throw new SceneFileException("#light_spot: must be defined after #light_num");
                    else {
                        currentLightIndex++;
                        if (currentLightIndex >= lightNum)
                            throw new SceneFileException("#light_spot: more lights defined than in #light_num");
                        
                        Vector3 colour = tokens.readColour();
                        Vector3 location = tokens.readVector3();
                        Vector3 direction = tokens.readVector3().unit();
                        float constantAttenuation = tokens.readFloat(0.0f);
                        float linearAttenuation = tokens.readFloat(0.0f);
                        float quadraticAttenuation = tokens.readFloat(0.0f);
                        float cutOffAngle = tokens.readFloat(0.0f, 90.0f);
                        float cosCutOffAngle = (float) Math.cos(Math.toRadians(cutOffAngle));
                        float dropOffRate = tokens.readFloat(0.0f);
                        
                        lights[currentLightIndex] = new SpotLight(colour, location, direction, constantAttenuation, linearAttenuation, quadraticAttenuation, cosCutOffAngle, dropOffRate);
                    }
                else {         // must be a shape or an error - errors detected in parseShapes
                    beginGroupRead = true;  // only allowed shapes and groups now
                    parseShapes(tokens, token);
                }
            }
            in.close();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new SceneFileException("Exception: " + e);
        }
        if (!cameraRead)
            throw new SceneFileException("#camera command not found in scene file");

        if (!backgroundRead)
            background = new Vector3(0, 0, 0);

        if (!ambientRead)
            ambient = new Vector3(0, 0, 0);

        if (!lightNumRead){
            lightNum = 0;
            lights = new Light[lightNum];
        }else if (currentLightIndex < lightNum - 1)
            throw new SceneFileException("Less lights defined than declared in #light_num command");

        if (!textureNumRead){
            textureNum = 0;
            textures = new Texture[textureNum];
        }else if (currentTextureIndex < textureNum - 1)
            throw new SceneFileException("Less textures defined than declared in #texture_num command");

        if (!materialNumRead){
            materialNum = 0;
            materials = new Material[materialNum];
        } else if (currentMaterialIndex < materialNum - 1)
            throw new SceneFileException("Less materials defined than declared in #material_num command");

        if (!vertexNumRead){
            vertexNum = 0;
            vertices = new Vertex[vertexNum];
        } else if (currentVertexIndex < vertexNum - 1)
            throw new SceneFileException("Less vertices defined than declared in #vertex_num command");

    }

    /**
     * Private method to process shapes from the Scene file.
     * If you need to add  new shape object to the ray-tracer, this is a method you will need to modify.
     * In general, to add a new shape, create subclass of Shape that has a constructor requiring two
     * parameters, the ShapeFileStreamTokenizer, and the material index for the shape object.
     * Add another "else if" to the list below to create the new shape object and add it to the
     * shapes list for the current group.
     *
     * @param tokens  The current SceneFileSreamTokenizer object for reading from a scene file.
     * @param token   The last token read - should be the scene file command string for a shape.
     *
     */
    private void parseShapes(SceneFileStreamTokenizer tokens, String token) {
    	Material material;
    	 int index = tokens.readInt(-1, materials.length -1);
         if (index >= 0)
             material = materials[index];
         else
             material = null;

        if (token.compareToIgnoreCase("shape_sphere") == 0) {
        	Vector3 t = tokens.readVector3();
        	Matrix4 tm = MatrixFactory.translate(t);

        	float radius = tokens.readFloat();
        	Shape s = new Sphere(radius, material);
        	s.transform = tm;
            shapes.add(s);
        } else if (token.compareToIgnoreCase("shape_box") == 0) {
        	Vector3 t = tokens.readVector3();
        	Matrix4 tm = MatrixFactory.translate(t);
            Vector3 length = tokens.readVector3();
            Box b = new Box(length.getX(), length.getY(), length.getZ(), material);
        	b.transform = tm;
            shapes.add(b);
        } else if (token.compareToIgnoreCase("shape_cone") == 0) {
        	Vector3 t = tokens.readVector3();
        	Matrix4 tm = MatrixFactory.translate(t);
        	float radius = tokens.readFloat(0.0f);
        	float height = tokens.readFloat(0.0f);
        	Cone c = new Cone(radius, height, material);
        	c.transform = tm;
            shapes.add(c);
        } else if (token.compareToIgnoreCase("shape_cylinder") == 0) {
        	Vector3 t = tokens.readVector3();
        	Matrix4 tm = MatrixFactory.translate(t);
            float radius = tokens.readFloat(0.0f);
            float height = tokens.readFloat(0.0f);
            Cylinder c = new Cylinder(radius, height, material);
            c.transform = tm;
            shapes.add(c);
        } else if (token.compareToIgnoreCase("shape_triangle") == 0) {
            int v1 = tokens.readInt(0, vertexNum - 1);    // each vertex index must be in the range 0 .. s.vertexNum-1
            int v2 = tokens.readInt(0, vertexNum - 1);
            int v3 = tokens.readInt(0, vertexNum - 1);
            if (v1 == v2 || v1 == v3 || v2 == v3) // check for invalid list of vertices
                throw new SceneFileException("shape_triangle: at least two vertices the same");
            
            Triangle tri = new Triangle(vertices[v1], vertices[v2], vertices[v3], material);
            shapes.add(tri);
        } else
            throw new SceneFileException("parseShape error: unknown token");
    }
}
