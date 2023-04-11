import java.io.*;
import java.util.Scanner;

public class Solution {

    public static int n;
    public static int[] heap;
    public static int[] originalHeap;
    public static int runTime;

    public static void main(String[] args) {
        readInput("input.txt");
        long startTime = System.nanoTime();
        minHeapBottomUp();
        for (int i = 1; i < heap.length; i++)
            originalHeap[i] = heap[i];
        // Iteratively remove the root, put it at the end, 
        // and heapify what's left until the list is sorted
        while (n > 1) {
            int temp = heap[1];
            heap[1] = heap[n];
            heap[n] = temp;
            n--;
            minHeapBottomUp();
        }
        long endTime = System.nanoTime();
        runTime = (int)(endTime - startTime);
        createOutput("output.txt");
    }
    
    private static void minHeapBottomUp() {
        for (int i = n / 2; i >= 1; i--) {
            int k = i;
            int v = heap[k];
            boolean isHeap = false;
            while (!isHeap && 2 * k <= n) {
                int j = 2 * k;
                if (j < n && heap[j] > heap[j + 1])
                    j++;
                if (v <= heap[j])
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
            originalHeap = new int[n + 1];
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
            // Print original heap
            for (int k = 1; k < originalHeap.length; k++)
                fileWriter.print(originalHeap[k] + " ");
            if (originalHeap.length > 0)
                fileWriter.println(originalHeap[originalHeap.length - 1]);
            else
                fileWriter.println();
            // Print sorted list
            for (int k = 1; k < heap.length; k++)
                fileWriter.print(heap[k] + " ");
            if (heap.length > 0)
                fileWriter.println(heap[heap.length - 1]);
            else
                fileWriter.println();
            fileWriter.println(runTime + " nanoseconds");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}