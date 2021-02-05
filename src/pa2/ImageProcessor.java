package pa2;

import ImageProcessing.Picture;
import ImageProcessing.Tuple;

import java.awt.*;
import java.util.ArrayList;

/* COM S 311 - Programming Assignment 2
 * Image Processing
 *       @author Stamati Morellas
 *       @author Jack Creighton
 */
public class ImageProcessor {

    public static class Pixel {
        private int r;
        private int g;
        private int b;
        private boolean isRemoved;

        public Pixel(int r, int g, int b) {
            this.setRed(r);
            this.setGreen(g);
            this.setBlue(b);
            isRemoved = false;
        }
        public int[] getRGB() {
            int[] rgb = new int[3];
            rgb[0] = r;
            rgb[1] = g;
            rgb[2] = b;
            return rgb;
        }
        public int getRed() {
            return r;
        }
        public int getGreen() {
            return g;
        }
        public int getBlue() {
            return b;
        }
        public boolean isRemoved() {
            return isRemoved;
        }
        public void setRed(int red) { r = red; }
        public void setGreen(int green) { g = green; }
        public void setBlue(int blue) { b = blue; }
        public void setRemoved(boolean b) {
            isRemoved = b;
        }
    }

    /**
     * Constructor
     */
    public ImageProcessor() {
        // empty
    }

    /**
     * Reduce the width of the image to W - x and return a new picture
     * @param x
     * @param inputImage
     */
    public static Picture reduceWidth(int x, String inputImage) {
        int imp;
        int tmp = x;
        ArrayList<Tuple> mincostwidthcut;

        Picture image = new Picture(inputImage);
        int W = image.width();
        int H = image.height();
        Pixel[][] pixels = new Pixel[H][W];; // Each pixel in the image
        Pixel[][] newPixels = new Pixel[H][W]; // Pixels for the new image - to be updated
        int[][] importance = new int[H][W];; // The importance for each pixel

        // Initialize pixel array
        for (int i = 0; i < H; i++) { // step through rows of picture
            for (int j = 0; j < W; j++) { // step through columns of picture
                pixels[i][j] = new Pixel(image.get(j, i).getRed(), image.get(j, i).getGreen(), image.get(j, i).getBlue()); // make a new pixel at pixels[i][j]
            }
        }


        // Iterate x times
        while (tmp != 0) {
            int newWidth = W - 1;

            // Update importance of pixels
            for (int i = 0; i < H; i++) { // step through rows
                for (int j = 0; j < W; j++) {
                    if (0 < j && j < newWidth) {
                        imp = distance(pixels[i][j - 1], pixels[i][j + 1]);
                        importance[i][j] = imp;
                    }
                    else if (j == 0) {
                        imp = distance(pixels[i][j], pixels[i][j + 1]);
                        importance[i][j] = imp;
                    }
                    else if (j == W - 1) {
                        imp = distance(pixels[i][j], pixels[i][j - 1]);
                        importance[i][j] = imp;
                    }
                }
            }

            // Compute Min-Cost width cut of importance matrix
            mincostwidthcut = MatrixCuts.widthCut(importance);
            for (int i = 1; i < mincostwidthcut.size() - 1; i++) {
                Tuple t = mincostwidthcut.get(i);
                pixels[t.getX()][t.getY()].setRemoved(true);
            }

            newPixels = new Pixel[H][W - 1];
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W - 1; j++) {
                    if (!pixels[i][j].isRemoved()) { // If the pixel is not marked as removed
                        newPixels[i][j] = pixels[i][j];
                    } else {
                        newPixels[i][j] = pixels[i][j + 1];
                    }
                }
            }
            W--; // Decrement width
            tmp--; // Decrement x
        }

        // Create a new picture and set the pixels for each cell
        Picture newImage = new Picture(W, H);
        for (int i = 0; i < newImage.height(); i++) { // step through rows (height)
            for (int j = 0; j < newImage.width(); j++) { // step through columns (width)
                Pixel current = newPixels[i][j]; // get the current pixel
                newImage.set(j, i, new Color(current.getRed(), current.getGreen(), current.getBlue())); //
            }
        }
        return newImage;
    }

    /**
     * Calculate distance given 2 pixels
     */
    public static int distance(Pixel p1, Pixel p2) {


        return (p1.getRed() - p2.getRed()) * (p1.getRed() - p2.getRed())
                + (p1.getGreen() - p2.getGreen()) * (p1.getGreen() - p2.getGreen())
                + (p1.getBlue() - p2.getBlue()) * (p1.getBlue() - p2.getBlue());
    }
}
