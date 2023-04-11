import java.io.*;
import java.util.Scanner;

public class SolutionMax {

    public static int n;
    public static int[] heap;
    public static int runTime;

    public static void main(String[] args) {
        readInput("input.txt");
        long startTime = System.nanoTime();
        maxHeapBottomUp();
        long endTime = System.nanoTime();
        runTime = (int)(endTime - startTime);
        createOutput("outputMax.txt");
    }

    private static void maxHeapBottomUp() {
        // Create max heap
        for (int i = n / 2; i >= 1; i--) {
            int k = i;
            int v = heap[k];
            boolean isHeap = false;
            while (!isHeap && 2 * k <= n) {
                int j = 2 * k;
                if (j < n && heap[j] < heap[j + 1])
                    j++;
                if (v >= heap[j])
                    isHeap = true;
                else {
                    heap[k] = heap[j];
                    k = j;
                }
            }
            heap[k] = v;
        }
    }
    
    private static void readInput(String fileName) {
        try {
            // Try to open file
            Scanner fileReader = new Scanner(new File(fileName));
            n = fileReader.nextInt();
            fileReader.nextLine();
            heap = new int[n + 1]; // Initialize matrix
            for (int k = 1; k <= n; k++)
                heap[k] = fileReader.nextInt(); // Populate matrix
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createOutput(String fileName) {
        try {
            PrintWriter fileWriter = new PrintWriter(
                new FileOutputStream(new File(fileName)));
            for (int k = 1; k < n; k++)
                fileWriter.print(heap[k] + " ");
            if (heap.length > 0)
                fileWriter.println(heap[n]);
            else
                fileWriter.println();
            fileWriter.println(runTime + " nanoseconds");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}