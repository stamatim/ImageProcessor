package tests;

import ImageProcessing.Tuple;
import org.junit.jupiter.api.Test; // JUnit from Maven Repo
import pa2.MatrixCuts;

import java.util.ArrayList;

public class MatrixCutsTest {
    @Test
    public static void main(String[] args) {

        int[][] M = {
                {5,  7,  9,  4,  5},
                {2,  3,  1,  1,  2},
                {2,  0,  49, 46, 8},
                {3,  1,  1,  1,  1},
                {50, 51, 25, 26, 1}
        };
        ArrayList<Tuple> widthCut;
        ArrayList<Tuple> stitchCut;

        // Test widthCut() method
        widthCut = MatrixCuts.widthCut(M);
        System.out.println(widthCut);

        // Test stitchCut() method
        stitchCut = MatrixCuts.stitchCut(M);
        System.out.println(stitchCut);
    }
}
