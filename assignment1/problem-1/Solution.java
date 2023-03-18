import java.io.*;
import java.util.Scanner;

import java.util.ArrayList;

public class Solution {

    public static int n;
    public static boolean[][] matrix;
    public static int runTime;

    public static void main(String[] args) {
        populateMatrix("input.txt");
        long startTime = System.nanoTime();
        String topology = determineTopology();
        long endTime = System.nanoTime();
        runTime = (int)(endTime - startTime);
        outputTopology("output.txt", topology);
    }

    private static void populateMatrix(String fileName) {
        try {
            Scanner fileReader = new Scanner(new File(fileName)); // Try to open file
            // Determine n
            n = 0;
            while (fileReader.hasNextLine()) {
                n++;
                fileReader.nextLine();
            }
            matrix = new boolean[n][n]; // Initialize matrix
            fileReader = new Scanner(new File(fileName)); // Open file again
            for (int r = 0; r < n; r++)
                for (int c = 0; c < n; c++)
                    // Populate matrix
                    matrix[r][c] = fileReader.nextInt() == 1;
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkRing() {
        ArrayList<Integer> visited = new ArrayList<Integer>();
        visited.add(0);
        int next = -1; // Should go forward through this vertex
        int end = -1; // Should return back to 0 through this vertex
        for (int c = 1; c < n; c++) {
            if (matrix[0][c]) {
                if (next == -1) {
                    next = c;
                } else if (end == -1) {
                    end = c;
                } else {
                    // Too many connections
                    return false;
                }
            }
        }
        if (next == -1 || end == -1) {
            // We didn't find a suitable start and end
            return false;
        }
        visited.add(next);
        while (true) {
            boolean foundNext = false;
            boolean foundPrev = false;
            int current = visited.get(visited.size() - 1);
            int prev = visited.get(visited.size() - 2);
            next = -1;
            for (int c = 0; c < n; c++) {
                if (matrix[current][c]) {
                    if (c == prev) {
                        // Check if this is where we came from
                        foundPrev = true;
                    } else if (!foundNext) {
                        next = c;
                        foundNext = true;
                    } else {
                        // Too many connections
                        return false;   
                    }
                }
            }
            if (!foundNext || !foundPrev) {
                // Didn't find somewhere to go, or somewhere to come from
                return false;
            } else if (next == end) {
                // We made it to the end!
                return true;
            } else if (visited.contains(next)) {
                // We've already been here, and we're not back to the start
                return false;
            } else {
                // We didn't run into any problems, so let's keep going
                visited.add(next);
            }   
        }
    }

    private static boolean checkStar() {
        int center = -1; // Need to find vertex conected to everything else
        if (matrix[0][1] && matrix[0][2])
            // There are two edges connected to 0, so 0 must be center
            center = 0;
        else
            // There is an edge not connected to 0 (1 or 2), so 0 isn't center
            for (int c = 1; c < n; c++)
                if (matrix[0][c])
                    // We found the center
                    center = c;
        if (center == -1)
            // No center, so can't be a star
            return false;
        // Now, check that edge uv is present iff u xor v is the center 
        for (int r = 0; r < n - 1; r++) {
            for (int c = r + 1; c < n; c++) {
                if ((r == center || c == center) && !matrix[r][c])
                    // Missing edge from center
                    return false;
                else if (r != center && c != center && matrix[r][c])
                    // Edge not adjancent to center is present
                    return false;
            }
        }
        return true; // Everything checks out
    }

    private static boolean checkMesh() {
        for (int r = 0; r < n - 1; r++)
            for (int c = r + 1; c < n; c++)
                if (!matrix[r][c])
                    return false; // Not complete: edge rc is not present
        return true;
    }

    private static String determineTopology() {
        if (checkRing())
            return "ring";
        else if (checkStar())
            return "star";
        else if (checkMesh())
            return "mesh";
        else
            return "none";
    }

    private static void outputTopology(String fileName, String topology) {
        try {
            PrintWriter fileWriter = new PrintWriter(new FileOutputStream(new File(fileName)));
            fileWriter.println(topology);
            fileWriter.println(runTime + " nanoseconds");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}