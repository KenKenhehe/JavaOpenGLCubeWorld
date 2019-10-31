// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.scene;


import inft3032.math.Matrix4;
import inft3032.math.Vector2;
import inft3032.math.Vector3;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

/**
 * An extension to the StreamTokenizer class to help read in scene files
 * for a ray-tracer.
 *
 * The constructor initialises the StreamTokenizer to handle Java style comments,
 * to allow underscore as a word character, to make '#' a token start character,
 * and to allow both " and ! to be seen as characters that start and end strings.
 *
 * @author a.sobey
 */
public class SceneFileStreamTokenizer extends StreamTokenizer {

    /**
     * The only constructor provided - need to pass a Reader object to this constructor. Typically
     * create a buffered reader to pass to this constructor.
     * <p>
     * Sets the StreamTokenizer so that Java style comments are discarded, the underscore character '_'
     * can be part of tokens, tokens can start with a '#' character, strings can be delineated with
     * the characters '"' and '!', numbers can be parsed, and end of line is ignored (white space).
     *
     * @param r A Reader object.
     */
    public SceneFileStreamTokenizer(Reader r) {
        super(r);
        super.slashSlashComments(true);
        super.slashStarComments(true);
        super.wordChars((int) ('_'), (int) ('_'));
        super.ordinaryChar('#');
        super.quoteChar('"');
        super.quoteChar('!');
        super.parseNumbers();
        super.eolIsSignificant(false);
    }

    /**
     * Read the next token as in integer.
     * @return The next token as an integer.
     * @throws SceneFileException if end of file or not an integer
     */
    public int readInt() throws SceneFileException {
        try {
            int result = nextToken();
            if (result == StreamTokenizer.TT_NUMBER)
                return (int) nval;
            else
                throw new SceneFileException("Attempting to read integer");
        } catch (IOException e) {
            throw new SceneFileException("Could not read next token");
        }
    }

    /**
     * Read the next token as an integer, but require  integer read to be
     * at least equal to the min parameter.
     * @param min The minimum value for the integer read
     * @return The integer value
     * @throws SceneFileException if end of file, not an integer, or integer less than min.
     */
    public int readInt(int min) throws SceneFileException {
        try {
            int result = nextToken();
            if (result == StreamTokenizer.TT_NUMBER) {
                int temp = (int) nval;
                if (temp >= min)
                    return temp;
                else
                    throw new SceneFileException("Integer read (" + temp + ") below minimum (" + min + ")");
            } else
                throw new SceneFileException("Attempting to read integer");
        } catch (IOException e) {
            throw new SceneFileException("Could not read next token");
        }
    }

    /**
     * Read the next token as an integer, but require integer read to be
     * between min and max (inclusive)
     * @param min The minimum value for the integer read.
     * @param max The maximum value for the integer read
     * @return The integer read
     * @throws SceneFileException if end of file, not an integer or integer not between min and max (inclusive).
     */
    public int readInt(int min, int max) throws SceneFileException {
        try {
            int result = nextToken();
            if (result == StreamTokenizer.TT_NUMBER) {
                int temp = (int) nval;
                if (temp >= min && temp <= max)
                    return temp;
                else
                    throw new SceneFileException("Integer read (" + temp + ") not in range (" + min + ", " + max + ")");
            } else
                throw new SceneFileException("Attempting to read integer");
        } catch (IOException e) {
            throw new SceneFileException("Could not read next token");
        }
    }

    /**
     * Reads the next token as a double value.
     * @return The double value read.
     * @throws SceneFileException if end of file or next value not a double.
     */
    public float readFloat() throws SceneFileException {
        try {
            int result = nextToken();
            if (result == StreamTokenizer.TT_NUMBER)
                return (float) nval;
            else
                throw new SceneFileException("Attempting to read double");
        } catch (IOException e) {
            throw new SceneFileException("Could not read next token");
        }
    }

    /**
     * Read the next token as a double value that is at least equal to min.
     * @param min The minimum value for the double value read.
     * @return The double value read.
     * @throws SceneFileException if end of file or next token is not a double value at least equal to min.
     */
    public float readFloat(float min) throws SceneFileException {
        try {
            int result = nextToken();
            if (result == StreamTokenizer.TT_NUMBER)
                if (nval >= min)
                    return (float) nval;
                else
                    throw new SceneFileException("Double read (" + nval + ") less than minimum (" + min + ")");
            else
                throw new SceneFileException("Attempting to read double");
        } catch (IOException e) {
            throw new SceneFileException("Could not read next token");
        }
    }

    /**
     * Read the next token as a double value that is between min and max
     * @param min The minimum value for the double read
     * @param max The maximum value for the double read
     * @return The double read from the StreamTokenizer
     * @throws SceneFileException if at end of file, token is not a double, or outside range min to max.
     */
    public float readFloat(float min, float max) throws SceneFileException {
        try {
            int result = nextToken();
            if (result == StreamTokenizer.TT_NUMBER)
                if (nval >= min && nval <= max)
                    return (float) nval;
                else
                    throw new SceneFileException("Doube read (" + nval + ") not in range (" + min + ", " + max + ")");
            else
                throw new SceneFileException("Attempting to read double");
        } catch (IOException e) {
            throw new SceneFileException("Could not read next token");
        }
    }

    /**
     * Reads the next token as a string.
     * @return Thes string read.
     * @throws SceneFileException if at end of file or next token  not a string.
     */
    public String readString() throws SceneFileException {
        try {
            int result = nextToken();
            if (result == StreamTokenizer.TT_WORD || result == (int) ('"') || result == (int) ('!'))
                return sval;
            else
                throw new SceneFileException("Attempting to read string");
        } catch (IOException e) {
            throw new SceneFileException("Could not read next token");
        }
    }

    /**
     * Reads a Point2D value (two doubles) from the StreamTokenizer
     * @return The Point2D value read
     * @throws SceneFileException if at end of file, or if can't read two double values.
     */
    public Vector2 readVector2() throws SceneFileException {
        return new Vector2(readFloat(), readFloat());
    }

    /**
     * Reads a Point3D value (three double values) from the StreamTokenizer
     * @return The Point3D value read.
     * @throws SceneFileException if at end of file or if can't read three double values.
     */
    public Vector3 readVector3() throws SceneFileException {
        return new Vector3(readFloat(), readFloat(), readFloat());
    }

    /**
     * Reads a Point3D value (three double values) from the StreamTokenizer, but all double
     * values must correspond to valid red/gree/blue colour values (be between 0.0 and 1.0.
     * @return The Point3D read.
     * @throws SceneFileException if at end of file, or can't read three doubles between 0 and 1.
     */
    public Vector3 readColour() throws SceneFileException {
        return new Vector3(readFloat(0, 1), readFloat(0, 1), readFloat(0, 1));
    }
    
    public Matrix4 readMatrix() throws SceneFileException {
    	return new Matrix4(readFloat(), readFloat(), readFloat(), readFloat(),
                readFloat(), readFloat(), readFloat(), readFloat(),
                readFloat(), readFloat(), readFloat(), readFloat(),
                readFloat(), readFloat(), readFloat(), readFloat());
    }

    /**
     * Discards the next 'number' tokens.
     * @param number The number of tokens discarded.
     */
    public void discard(int number) {
        try {
            for (int i = 0; i < number; i++)
                nextToken();
        } catch (IOException e) {
            throw new SceneFileException("Error while discarding tokens");
        }
    }

    /**
     * Discards tokens and prints a string to standard output.
     * @param tokens The number of tokens to discard.
     * @param msg The string to write to standard output.
     */
    public void discard(int tokens, String msg) {
        System.out.println(msg);
        discard(tokens);
    }
}
