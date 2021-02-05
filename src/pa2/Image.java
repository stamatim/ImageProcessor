package pa2;

public class Image {
    private int W; // Width of the image - in pixels
    private int H; // Height of the image - in pixels
    private Pixel[][] image; // The image - represented by a 2-D array of pixels

    public static class Pixel {
        private int r;
        private int g;
        private int b;
        public Pixel(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
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
    }

    public Image(int W, int H) {
        this.W = W;
        this.H = H;

    }


}
