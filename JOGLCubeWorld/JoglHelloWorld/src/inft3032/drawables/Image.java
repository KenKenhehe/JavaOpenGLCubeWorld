// Starting code Copyright 2014 University of South Australia
// Written by Michael Marner <michael.marner@unisa.edu.au>
//
package inft3032.drawables;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * Image class with input and output in Bitmap (BMP) format.
 *
 * @author a.sobey
 * Created 27/03/2004, 22:32:27
 */
public class Image {

    public class ImageError extends RuntimeException {
        /**
		 * 
		 */
		private static final long serialVersionUID = -1962035905996657204L;

		public ImageError(String s) {
            super(s);
        }
    }

    private int height;
    private int width;
    private int[] pixels;

    /**
     * Default constructor Image().
     * <p/>
     * Constructs an image of 512x512 pixels, each pixel black.
     */
    public Image() {
        width = 512;
        height = 512;
        pixels = new int[width * height];
    }

    /**
     * Construct an image of a specfied width and height.  Each pixiel is black.
     *
     * @param height The image height.
     * @param width  The image width.
     */
    public Image(int height, int width) {
        this.height = height;
        this.width = width;
        pixels = new int[this.width * this.height];
    }

    /**
     * Construct an image from an existing image.
     *
     * @param img An existing image to copy.
     */
    public Image(Image img) {
        width = img.width;
        height = img.height;
        pixels = new int[width * height];
        for (int wIndex = 0; wIndex < width * height; wIndex++)
            pixels[wIndex] = img.pixels[wIndex];
    }

    public Image(String fileName, int alpha) throws IOException {
        width = 0;
        height = 0;
        read(fileName, alpha);
    }

    /**
     * @return The image height in pixels.
     */
    public int height() {
        return height;
    }

    /**
     * @return The image width in pixels.
     */
    public int width() {
        return width;
    }

    /**
     * Utility method to check the validity of an image object.
     * <p/>
     * Checks that the width and heights are both positive, the pixel array
     * has been allocated, and is of the correct size.
     *
     * @return true if image object validly constructed.
     */
    public boolean isValid() {
        return (width > 0 && height > 0 && pixels != null && pixels.length == width * height);
    }

    /**
     * Returns the image data (8 bit red, green, blue, alpha components) as a 32 bit integer value.
     * The value returned is<br>
     * <pre>   alpha << 24 | red << 16 | green << 8 | blue </pre><br>
     * where each component is a value in the range 0 .. 128.
     *
     * @param hPos The pixel column (start index is 0)
     * @param vPos The pixel row (start index is 0)
     * @return 32 bit integer with image value.
     */
    public int get(int hPos, int vPos) {
        try {
            return pixels[hPos + vPos * width];
        } catch (Exception e) {
            throw new ImageError("get: index error? hpos="+hPos+", vPos="+vPos + e.toString());
        }
    }

    /**
     * Set the pixel at column hPos, row vPos to the 32 bit value p.  The integer value passed in
     * should consist of 4 8-bit values for the pixel alpha, red, green and blue components. <br>
     * Use the static method colour(red, green, blue, alpha) to construct a valid pixel value.
     *
     * @param hPos The pixel column
     * @param vPos The pixel row
     * @param p    the value to set for the pixel colour.
     */
    public void set(int hPos, int vPos, int p) {
        try {
            pixels[hPos + vPos * width] = p;
        } catch (Exception e) {
            throw new ImageError("set: index error? " + e.toString());
        }
    }

    /**
     * Extract the 8-bit red component of a 32-bit pixel colour.
     *
     * @param colour The pixel colour
     * @return The 8-bit red colour component.
     */
    public static int red(int colour) {
        return (colour >> 16) & 0xff;
    }

    /**
     * Extract the 8-bit green component of a 32-bt pixel colour.
     *
     * @param colour The pixel colour
     * @return The 8-bit green component.
     */
    public static int green(int colour) {
        return (colour >> 8) & 0xff;
    }

    /**
     * Extract the 8-bit blue component of a 32-bit pixel colour.
     *
     * @param colour The pixel colour
     * @return The 8-bit blue component.
     */
    public static int blue(int colour) {
        return (colour & 0xff);
    }

    /**
     * Extract the 8-bit alpha value from a 32-bit pixel colour
     *
     * @param colour The pixel colour
     * @return The 8-bit alpha value.
     */
    public static int alpha(int colour) {
        return (colour >> 24) * 0xff;
    }

    /**
     * Construct a 32-bit pixel colour value from red, green, blue and alpha value components.
     *
     * @param red   an integer with lower 8 bits a red colour component value.
     * @param green an integer with lower 8 bits a green colour component value
     * @param blue  an integer with lower 8 bits a blue colour component value
     * @param alpha an integer with lower 8 bits an alpha value component.
     * @return The 32 bit pixel colour value.
     */
    public static int colour(int red, int green, int blue, int alpha) {
        return ((alpha & 0xff) << 24) | ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);
    }

    /**
     * Clamp an integer value to the range 0 .. 255 (unsigned 8 bit value).
     * If less than zero, set to zero, if greater than 255, set to 255.
     *
     * @param byteValue An integer value
     * @return an integer value in the range 0 .. 255.
     */
    public static int clamp(int byteValue) {
        return (byteValue < 0 ? 0 : (byteValue > 255 ? 255 : byteValue));
    }

    /**
     * Write an image to a file using the bitmap (BMP) format.
     *
     * @param fileName The output file name.  Overwrites without warningn if file already exists.
     * @throws IOException If an error occours during output.
     */
    public void write(String fileName) throws IOException {
        // File Header - Actual contents (14 bytes):
        short fileType = 0x4d42;// always "BM"
        int fileSize;           // size of file in bytes
        short reserved1 = 0;    // always 0
        short reserved2 = 0;    // always 0
        int bitmapOffset = 54;  // starting byte position of image data
        // BMP Image Header - Actual conents (40 bytes):
        int size = 40;          // size of this header in bytes
        //int width;              // image width in pixels
        //int height;             // image height in pixels (if < 0, "top-down")
        short planes = 1;       // no. of color planes: always 1
        short bitsPerPixel = 24;// number of bits per pixel: 1, 4, 8, or 24 (no color map)
        int compression = 0;    // compression methods used: 0 (none), 1 (8-bit RLE), or 2 (4-bit RLE)
        int sizeOfBitmap;       // size of bitmap in bytes (may be 0: if so, calculate)
        int horzResolution = 0;     // horizontal resolution, pixels/meter (may be 0)
        int vertResolution = 0;     // vertical resolution, pixels/meter (may be 0)
        int colorsUsed = 0;         // no. of colors in palette (if 0, calculate)
        int colorsImportant = 0;    // no. of important colors (appear first in palette) (0 means all are important)
        // local variable
        int iScanLineSize;          // The scan line size in bytes
        if (!isValid())
            throw new IOException("Image is not valid.");
        // now open the file
        FileOutputStream fos = new FileOutputStream(fileName);
        FileChannel fc = fos.getChannel();
        // write the "header"
        boolean padded = false;
        // first calculate the scanline size
        iScanLineSize = 3 * width;
        if (iScanLineSize % 2 != 0) {
            iScanLineSize++;
            padded = true;
        }
        // now, calculate the file size
        fileSize = 14 + 40 + (iScanLineSize * height);
        sizeOfBitmap = iScanLineSize * height;
        // create a ByteBuffer
        ByteBuffer bbuf = ByteBuffer.allocate(fileSize);
        bbuf.order(ByteOrder.LITTLE_ENDIAN);
        bbuf.clear();
        // now put out file header
        bbuf.putShort(fileType);
        bbuf.putInt(fileSize);
        bbuf.putShort(reserved1);
        bbuf.putShort(reserved2);
        bbuf.putInt(bitmapOffset);
        bbuf.putInt(size);
        bbuf.putInt(width);
        bbuf.putInt(height);
        bbuf.putShort(planes);
        bbuf.putShort(bitsPerPixel);
        bbuf.putInt(compression);
        bbuf.putInt(sizeOfBitmap);
        bbuf.putInt(horzResolution);
        bbuf.putInt(vertResolution);
        bbuf.putInt(colorsUsed);
        bbuf.putInt(colorsImportant);
        // put the pixels
        byte pad = 0;
        for (int row = (height - 1); row >= 0; row--) {
            for (int column = 0; column < width; column++) {
                int pixel = pixels[column + row * width];
                // put them in order blue, green, red - don't put alpha value
                bbuf.put((byte) blue(pixel));
                bbuf.put((byte) green(pixel));
                bbuf.put((byte) red(pixel));
            }
            if (padded)
                bbuf.put(pad);
        }
        bbuf.flip();
        fc.write(bbuf);
        fos.close();
    }

    /**
     * Read an image from a bitmap (.bmp) file.
     * <p/>
     * Reads the red-gree-blue values for each pixel from an image file in the bitmap format.
     *
     * @param fileName The bitmap image file name.
     * @param alpha    The alpha value to use when constructing each pixel in the bitmap.
     * @throws IOException When an error occurs in either reading the file or in the format of the bitmap file.
     */
    @SuppressWarnings({ "resource", "unused" })
	public void read(String fileName, int alpha) throws IOException {
        // some constants
        final int BMP_BF_TYPE = 0x4D42;   // "BM"
        final int BMP_BF_OFF_BITS = 54;   // 14 for file header + 40 for info header
        final int BMP_BI_SIZE = 40;
        // now open the file
        FileInputStream fos = new FileInputStream(fileName);
        FileChannel fc = fos.getChannel();
        // create a ByteBuffer
        int realFileSize = (int) (fc.size());
        ByteBuffer bbuf = ByteBuffer.allocate(realFileSize);
        fc.read(bbuf);
        bbuf.order(ByteOrder.LITTLE_ENDIAN);
        bbuf.rewind();
        // File Header - Actual contents (14 bytes):
        short fileType = bbuf.getShort();       // always "BM"
        if (fileType != BMP_BF_TYPE)
            throw new IOException("Wrong header for bitmap");
        int fileSize = bbuf.getInt();           // size of file in bytes
        if (fileSize != realFileSize)
            throw new IOException("Wrong header for bitmap - filesize wrong");
        short reserved1 = bbuf.getShort();    // always 0
        short reserved2 = bbuf.getShort();    // always 0
        int bitmapOffset = bbuf.getInt();  // starting byte position of image data
        if (bitmapOffset != BMP_BF_OFF_BITS)
            throw new IOException("Corrupted bitmap header - bit map offset");
        // BMP Image Header - Actual conents (40 bytes):
        int size = bbuf.getInt();               // size of this header in bytes
        if (size != BMP_BI_SIZE)
            throw new IOException("Corrupted bitmap header - bi size");
        int imgWidth = bbuf.getInt();           // image width in pixels
        if (imgWidth < 0)
            throw new IOException("Corrupted bitmap header - image size negative");
        int imgHeight = bbuf.getInt();          // image height in pixels (if < 0, "top-down")
        if (imgHeight < 0)
            throw new IOException("Bitmap has negative height (top-down) - can't handle this bitmap");
        short planes = bbuf.getShort();           // no. of color planes: always 1
        if (planes != 1)
            throw new IOException("Bitmap has planes set to other than 1 - can't handle");
        short bitsPerPixel = bbuf.getShort();     // number of bits per pixel: 1, 4, 8, or 24 (no color map)
        if (bitsPerPixel != 24)
            throw new IOException("Bitmap not RGB - can't handle");
        int compression = bbuf.getInt();        // compression methods used: 0 (none), 1 (8-bit RLE), or 2 (4-bit RLE)
        if (compression != 0)
            throw new IOException("Bitmap compressed - can't handle");
        int sizeOfBitmap = bbuf.getInt();       // size of bitmap in bytes (may be 0: if so, calculate)
        int horzResolution = bbuf.getInt();     // horizontal resolution, pixels/meter (may be 0)
        int vertResolution = bbuf.getInt();     // vertical resolution, pixels/meter (may be 0)
        int colorsUsed = bbuf.getInt();         // no. of colors in palette (if 0, calculate)
        int colorsImportant = bbuf.getInt();     // no. of important colors (appear first in palette) (0 means all are important)

        int iScanLineSize;

        height = imgHeight;
        width = imgWidth;
        boolean padded = false;
        // first calculate the scanline size
        iScanLineSize = 3 * width;
        if (iScanLineSize % 2 != 0) {
            iScanLineSize++;
            padded = true;
        }
//        if (iScanLineSize * height != sizeOfBitmap) {
//            throw new IOException("Bitmap image size wrong");
//        }
        pixels = new int[height * width];
        // get the pixels
        int imageAlpah = clamp(alpha);
        byte pad;
        for (int row = (height - 1); row >= 0; row--) {
            for (int column = 0; column < width; column++) {
                byte blue = bbuf.get();
                byte green = bbuf.get();
                byte red = bbuf.get();
                pixels[column + row * width] = colour(red, green, blue, imageAlpah);
            }
            if (padded)
                pad = bbuf.get();
        }
        fos.close();
    }

    public int[] getPixels(){
        return pixels;
    }
}
