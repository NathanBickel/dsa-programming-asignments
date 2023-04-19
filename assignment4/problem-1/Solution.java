import java.io.*;
import java.util.Scanner;

public class Solution {

    public static int n;
    public static double[][] adjMatrix;
    public static int runTime;

    public static void main(String[] args) {
        readInput("input.txt");
        long startTime = System.nanoTime();
        runFloyd();
        long endTime = System.nanoTime();
        runTime = (int)(endTime - startTime);
        createOutput("output.txt");
    }

    public static void readInput(String fileName) {
        try {
            Scanner fileReader = new Scanner(
                new File(fileName)); // Try to open file
            // Determine n
            n = 0;
            while (fileReader.hasNext()) {
                n++;
                fileReader.nextLine();
            }
            adjMatrix = new double[n][n];
            fileReader = new Scanner(new File(fileName)); // Open file again
            for (int r = 0; r < n; r++) {
                String[] numbers = fileReader.nextLine().split(" ");
                for (int c = 0; c < n; c++)
                    adjMatrix[r][c] = numbers[c].equals("0") && r != c
                                        ? Double.MAX_VALUE
                                        : Double.parseDouble(numbers[c]);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runFloyd() {
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (adjMatrix[i][k] < Double.MAX_VALUE &&
                        adjMatrix[k][j] < Double.MAX_VALUE) // Prevent overflow
                            adjMatrix[i][j] = Double.min(adjMatrix[i][j], 
                                        adjMatrix[i][k] + adjMatrix[k][j]);
    }

    public static void createOutput(String fileName) {
        try {
            PrintWriter fileWriter = new PrintWriter(
                new FileOutputStream(new File(fileName)));
            for (double[] row : adjMatrix) {
                for (double dist : row)
                    fileWriter.print((dist == Double.MAX_VALUE 
                                        ? "inf" : dist) + " ");
                fileWriter.println();
            }
            fileWriter.println(runTime + " nanoseconds");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
}