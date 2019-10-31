// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import java.nio.IntBuffer;

/**
 * A class to represent a Texture.
 * <p/>
 * A texture contains an index, the filename of the .bmp texture file, and the bitmap
 * loaded into a local Image object.
 * Created by Tony Sobey on 27/03/2004 at 03:09:41
 *
 * @author a.sobey
 * @version 1.0
 */
public class Texture {
    public int index;
    public String fileName;
    public Image image;
    
    public Texture(int index, String filename, Image image) {
    	this.index = index;
    	this.fileName = filename;
    	this.image = image;

    }
    
    public void init(GL3 gl) {
        try{
    	    image.read(fileName, 1);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        int[] temp = new int[]{1};
        int textureID = temp[0];
        gl.glGenTextures(1, IntBuffer.wrap(temp));
        gl.glBindTexture(GL3.GL_TEXTURE_2D, textureID);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_BORDER);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_BORDER);

        gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, GL.GL_RGB,
                image.width(), image.height(), 0, GL.GL_BGRA,
                GL3.GL_UNSIGNED_BYTE, IntBuffer.wrap(image.getPixels()));

        gl.glActiveTexture(GL3.GL_TEXTURE0);
        gl.glBindTexture(GL3.GL_TEXTURE_2D, textureID);
    }
}
