import java.io.*;
import java.util.Scanner;

public class Solution {

    public static int n;
    public static double[] list;
    public static int runTime;
    public static void main(String[] args) {
        readInput("input.txt");
        long startTime = System.nanoTime();
        quickSort(list, 0, n-1); // Entire list
        long endTime = System.nanoTime();
        runTime = (int)(endTime - startTime);
        createOutput("output.txt");
    }

    private static void readInput(String fileName) {
        try {
            Scanner fileReader = new Scanner(
                new File(fileName)); // Try to open file
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

    private static void createOutput(String fileName) {
        try {
            PrintWriter fileWriter = new PrintWriter(
                new FileOutputStream(new File(fileName)));
            for (int k = 0; k < n - 1; k++)
                fileWriter.print(list[k] + " ");
            if (list.length > 0)
                fileWriter.println(list[n - 1]);
            else
                fileWriter.println();
            fileWriter.println(runTime + " nanoseconds");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}