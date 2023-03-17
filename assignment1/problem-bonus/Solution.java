import java.io.*;
import java.util.Scanner;

public class Solution {

    public static final String INPUTS_FILE_PATH = "inputs/size";
    public static final String OUTPUTS_FILE_PATH = "outputs/size";
    public static final int[] SIZES = {10, 100, 1000, 10000, 100000};
    public static final int NUM_TESTS = 100;
    public static int n;
    public static double[] list;
    public static int[][] runTimes = new int[SIZES.length][NUM_TESTS];

    public static void main(String[] args) {
        createTestInputs();
        for (int sizeNum = 0; sizeNum < SIZES.length; sizeNum++) {
            for (int testNum = 0; testNum < NUM_TESTS; testNum++) {
                readInput(INPUTS_FILE_PATH + SIZES[sizeNum] 
                    + "/test" + testNum + ".txt");
                long startTime = System.nanoTime();
                quickSort(list, 0, n-1); // Entire list
                long endTime = System.nanoTime();
                runTimes[sizeNum][testNum] = (int)(endTime - startTime);
                createOutput(OUTPUTS_FILE_PATH + SIZES[sizeNum] 
                    + "/test" + testNum + ".txt", sizeNum, testNum);
            }
        }
        createTimeOutputs("runTimes.txt", "runTimesAverage.txt");
    }

    private static void createTestInputs() {
        for (int size : SIZES) {
            for (int i = 0; i < NUM_TESTS; i++) {
                try {
                    PrintWriter fileWriter = new PrintWriter(
                        new FileOutputStream(
                        new File(
                            INPUTS_FILE_PATH + size + "/test" + i + ".txt")));
                    for (int k = 0; k < size; k++)
                        fileWriter.print(Math.random() + " ");
                    fileWriter.print(Math.random());
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static void readInput(String fileName) {
        try {
            // Try to open file
            Scanner fileReader = new Scanner(new File(fileName));
            // Determine n
            n = 0;
            while (fileReader.hasNext()) {
                n++;
                fileReader.next();
            }
            list = new double[n]; // Initialize matrix
            fileReader = new Scanner(new File(fileName)); // Open file again
            for (int k = 0; k < n; k++)
                list[k] = fileReader.nextDouble(); // Populate matrix
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Watched https://www.youtube.com/watch?v=XE4VP_8Y0BU for explanation
    // Sorts `list` from l to r, inclusive
    private static void quickSort(double[] list, int l, int r) {
        if (r - l <= 0)
            // We're done
            return;
        // Apply Hoare's algorithm, page 178 of textbook
        double p = list[l];
        int i = l + 1;
        int j = r;
        while (true) {
            while (list[i] < p) {
                i++;
                if (i >= r)
                    break;
            } while (list[j] > p)
                j--;
            if (i >= j)
                break;
            // Swap list[i], list[j]
            double temp = list[j];
            list[j] = list[i];
            list[i] = temp;
        }
        // Swap list[l], list[j]
        double temp = list[j];
        list[j] = list[l];
        list[l] = temp;
        // Divide and conquer--partition is now at index j
        quickSort(list, l, j - 1);
        quickSort(list, j + 1, r);
    }

    private static void createOutput(String fileName, 
                                     int sizeNum, int testNum) {
        try {
            PrintWriter fileWriter = new PrintWriter(
                new FileOutputStream(new File(fileName)));
            for (int k = 0; k < n - 1; k++)
                fileWriter.print(list[k] + " ");
            if (list.length > 0)
                fileWriter.println(list[n - 1]);
            else
                fileWriter.println();
            fileWriter.println(runTimes[sizeNum][testNum] + " nanoseconds");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTimeOutputs(String fileNameAll, String fileNameAverage) {
        // Make file with all run times
        try {
            PrintWriter fileWriter = new PrintWriter(
                new FileOutputStream(new File(fileNameAll)));
            for (int r = 0; r < SIZES.length; r++) {
                for (int c = 0; c < NUM_TESTS - 1; c++) {
                    fileWriter.print(runTimes[r][c] + " ");
                }
                if (r == SIZES.length - 1)
                    fileWriter.print(runTimes[r][NUM_TESTS - 1]);
                else
                    fileWriter.println(runTimes[r][NUM_TESTS - 1]);
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Make file with average run times
        try {
            PrintWriter fileWriter = new PrintWriter(
                new FileOutputStream(new File(fileNameAverage)));
            for (int r = 0; r < SIZES.length; r++) {
                int total = 0;
                for (int c = 0; c < NUM_TESTS; c++)
                    total += runTimes[r][c];
                total /= NUM_TESTS;
                fileWriter.println("Size " + SIZES[r] + ": " 
                    + total + " nanoseconds");
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}