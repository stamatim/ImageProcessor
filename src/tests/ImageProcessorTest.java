package tests;

import ImageProcessing.Picture;
import pa2.ImageProcessor;

import java.awt.*;

public class ImageProcessorTest {
    public static void main(String[] args) {
        Picture p1 = new Picture(4, 3);
        p1.set(0,0, new Color(98, 251, 246));
        p1.set(1,0, new Color(34, 0, 246));
        p1.set(2,0, new Color(255, 246, 127));
        p1.set(3,0, new Color(21, 0, 231));
        p1.set(0,1, new Color(25, 186, 221));
        p1.set(1,1, new Color(43, 9, 127));
        p1.set(2,1, new Color(128, 174, 100));
        p1.set(3,1, new Color(88, 1, 143));
        p1.set(0,2, new Color(46, 201, 132));
        p1.set(1,2, new Color(23, 5, 217));
        p1.set(2,2, new Color(186, 165, 147));
        p1.set(3,2, new Color(31, 8, 251));

        ImageProcessor.reduceWidth(1000, "/home/stamati/Desktop/test.png").save("car.png");

//        Picture p2 = new Picture("/Users/PrimaryFolder/Desktop/1.jpg");
//        ImageProcessor ip2 = new ImageProcessor(p2);
//        ip2.reduceWidth(50);
    }
}
