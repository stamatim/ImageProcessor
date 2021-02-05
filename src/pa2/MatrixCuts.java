package pa2;

import ImageProcessing.Tuple;

import java.util.ArrayList;

/* COM S 311 - Programming Assignment 2
* Matrix Processor
*       @author Stamati Morellas
*       @author Jack Creighton
*/
public class MatrixCuts {

    private Tuple current; // the current tuple
    private Tuple previous; // the previous tuple
    private ArrayList<Tuple> sequenceWC; // width cut sequence
    private ArrayList<Tuple> sequenceSC; // stitch cut sequence

    // Constructor
    public MatrixCuts() {
        sequenceWC = new ArrayList<>();
        sequenceSC = new ArrayList<>();
    }

    /*
     * Returns the min-cost width cut of M and its cost. The return type is an arraylist of Tuples.
     * First entry of this list is a tuple of the form [x − 1], where x is the cost of the min-cost width cut.
     * Rest of the tuples represent the min cost width cut.
     *
     *     @return
     */
    public static ArrayList<Tuple> widthCut(int[][] M) {
        int[][] spath = new int[M.length][M[0].length]; // matrix with shortest path in each block
        ArrayList<Tuple> reverseList = new ArrayList<>();

        // Initialize first row with values from given array
        for (int j = 0; j < M.length; j++)
            spath[0][j] = M[0][j];

        for (int i = 1; i < M.length; i++)
            for (int j = 0; j < M[0].length; j++)
                // check for left column
                if (j == 0)
                    spath[i][j] = M[i][j] + Math.min(spath[i - 1][j], spath[i - 1][j + 1]);
                // check for right column
                else if (j == M[0].length - 1)
                    spath[i][j] = M[i][j] + Math.min(spath[i - 1][j], spath[i - 1][j - 1]);
                // otherwise
                else
                    spath[i][j] = M[i][j] + Math.min(spath[i - 1][j - 1], Math.min(spath[i - 1][j], spath[i - 1][j + 1]));


        // Iterate through last row to find minimum element
        int mincost = spath[M.length - 1][0];
        int minindex = 0;
        int wclast;
        for (int j = 1; j < M[0].length; j++) {
            int current = spath[M.length - 1][j];
            if (current < mincost) {
                minindex = j;
                mincost = current;
            }
        }
        reverseList.add(new Tuple(M.length - 1, minindex));
        Tuple last = new Tuple(mincost, -1); // make tuple of last tuple

        for (int i = M.length - 1; i > 0; i--) {

            // If index is in the last column
            if (minindex == M[0].length - 1) {
                if (spath[i - 1][minindex - 1] <= spath[i - 1][minindex]) {
                    minindex--;
                    reverseList.add(new Tuple(i - 1, minindex));
                } else {
                    reverseList.add(new Tuple(i - 1, minindex));
                }
            }
            // If index is in the first column
            else if (minindex == 0) {
                if (spath[i - 1][minindex] <= spath[i - 1][minindex + 1]) {
                    reverseList.add(new Tuple(i - 1, minindex));
                } else {
                    minindex++;
                    reverseList.add(new Tuple(i - 1, minindex));
                }
            }
            // Otherwise
            else {
                if (spath[i - 1][minindex - 1] <= spath[i - 1][minindex] && spath[i - 1][minindex - 1] <= spath[i - 1][minindex + 1]) {
                    minindex--;
                    reverseList.add(new Tuple(i - 1, minindex));
                } else if (spath[i - 1][minindex] <= spath[i - 1][minindex - 1] && spath[i - 1][minindex] <= spath[i - 1][minindex + 1]) {
                    reverseList.add(new Tuple(i - 1, minindex));
                } else if (spath[i - 1][minindex + 1] <= spath[i - 1][minindex - 1] && spath[i - 1][minindex + 1] <= spath[i - 1][minindex]) {
                    minindex++;
                    reverseList.add(new Tuple(i - 1, minindex));
                }
            }
        }
        reverseList.add(last);

        // Reverse the ArrayList
        ArrayList<Tuple> V = new ArrayList<>();
        for (int i = reverseList.size() - 1; i >= 0; i--) {
            V.add(reverseList.get(i));
        }

        return V;
    }
    /*
    * Returns the
    */
    public static ArrayList<Tuple> stitchCut(int[][] M) {
        int[][] spath = new int[M.length][M[0].length]; // matrix with shortest path in each block
        ArrayList<Tuple> reverseList = new ArrayList<>();

        // Initialize first row with values from given array
        for (int j = 0; j < M.length; j++)
            spath[0][j] = M[0][j];

        for (int i = 1; i < M.length; i++)
            for (int j = 0; j < M[0].length; j++)
                // check for left column
                if (j == 0)
                    spath[i][j] = M[i][j] + spath[i - 1][j];
                // otherwise
                else
                    spath[i][j] = M[i][j] + Math.min(spath[i - 1][j], Math.min(spath[i - 1][j - 1], spath[i][j - 1]));

        // Iterate through last row to find minimum element
        int mincost = spath[M.length - 1][0];
        int minindex = 0;
        for (int j = 1; j < M[0].length; j++) {
            int current = spath[M.length - 1][j];
            if (current < mincost) {
                minindex = j;
                mincost = current;
            }
        }

        reverseList.add(new Tuple(M.length - 1, minindex));
        Tuple last = new Tuple(mincost, -1); // make tuple of last tuple

        int layer = M.length - 1;
        while (layer != 0) {
            // If index is in first column
            if (minindex == 0) {
                layer--;
                reverseList.add(new Tuple(layer, minindex));
            }
            // Otherwise
            else {
                if (spath[layer][minindex - 1] <= spath[layer - 1][minindex - 1] && spath[layer][minindex - 1] <= spath[layer - 1][minindex]) {
                    minindex--;
                    reverseList.add(new Tuple(layer, minindex));
                } else if (spath[layer - 1][minindex - 1] <= spath[layer][minindex - 1] && spath[layer - 1][minindex - 1] <= spath[layer - 1][minindex]) {
                    layer--;
                    minindex--;
                    reverseList.add(new Tuple(layer, minindex));
                } else if (spath[layer - 1][minindex] <= spath[layer - 1][minindex - 1] && spath[layer - 1][minindex] <= spath[layer][minindex - 1]) {
                    layer--;
                    reverseList.add(new Tuple(layer, minindex));
                }
            }
        }

        reverseList.add(last);

        // Reverse the ArrayList
        ArrayList<Tuple> S = new ArrayList<>();
        for (int i = reverseList.size() - 1; i >= 0; i--) {
            S.add(reverseList.get(i));
        }

        return S;
    }
}

