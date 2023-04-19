import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    public static int[] V; // First set in bipartition
    public static int[] U; // Second set in bipartition
    public static HashMap<Integer, LinkedList<Integer>> adjList;
    public static HashMap<Integer, Integer> matching; // Current matching
    public static HashMap<Integer, Integer> labels; // Labelling
    public static Queue<Integer> queue; // Free vertices in V
    public static int runTime;

    public static void main(String[] args) {
        readInput("input.txt");
        long startTime = System.nanoTime();
        findMaxMatching();
        long endTime = System.nanoTime();
        runTime = (int)(endTime - startTime);
        createOutput("output.txt");
    }
    
    public static void readInput(String fileName) {
        try {
            // Try to open file
            Scanner fileReader = new Scanner(new File(fileName));
            // Determine lengths
            try {
                V = Arrays.stream(fileReader.nextLine().split(" "))
                            .mapToInt(Integer::parseInt).toArray();
            } catch (Exception e) {
                // String is empty or unparsable, we assume nothing is in it
                V = new int[0];
            }
            try {
                U = Arrays.stream(fileReader.nextLine().split(" "))
                            .mapToInt(Integer::parseInt).toArray();
            } catch (Exception e) {
                // String is empty or unparsable, we assume nothing is in it
                U = new int[0];
            }
            int n = V.length + U.length;
            // Create adjacency matrix
            boolean[][] adjMatrix = new boolean[n][n];
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++)
                    adjMatrix[r][c] = fileReader.next().equals("1");
                if (r < n - 1)
                    fileReader.nextLine();
            }
            // Create adjacency linked list
            adjList = new HashMap<Integer, LinkedList<Integer>>();
            for (int r = 0; r < n; r++) {
                LinkedList<Integer> neighborhood = new LinkedList<Integer>();
                for (int c = 0; c < n; c++)
                    if (adjMatrix[r][c])
                        neighborhood.add(c);
                adjList.put(r, neighborhood);
            }
            // Create matching, empty for now
            matching = new HashMap<Integer, Integer>();
            labels = new HashMap<Integer, Integer>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void findMaxMatching() {
        initializeQueue();
        while (!queue.isEmpty()) {
            int w = queue.poll();
            if (partitionHasElement(V, w)) {
                for (int u : adjList.get(w)) {
                    if (!matching.containsKey(u)) {
                        // augment
                        addEdgeToMatching(w, u);
                        int v = w;
                        while (labels.containsKey(v)) {
                            u = labels.get(v);
                            removeEdgeFromMatching(v, u);
                            v = labels.get(u);
                            addEdgeToMatching(v, u);
                        }
                        labels.clear(); // Remove labels
                        initializeQueue();
                        break;
                    } else {
                        // u is matched
                        if (!edgeInMatching(w, u) && !labels.containsKey(u)) {
                            labels.put(u, w);
                            queue.add(u);
                        }
                    }
                }
            } else {
                // w is in U and matched
                int v = matching.get(w);
                labels.put(v, w);
                queue.add(v);
            }
        }
    }

    public static void createOutput(String fileName) {
        try {
            PrintWriter fileWriter = new PrintWriter(
                new FileOutputStream(new File(fileName)));
            String matchedFromV = "";
            String matchedFromU = "";
            for (int v : V) {
                if (matching.containsKey(v)) {
                    matchedFromV += v + " ";
                    matchedFromU += matching.get(v) + " ";
                }
            }
            fileWriter.println(matchedFromV);
            fileWriter.println(matchedFromU);
            fileWriter.println(runTime + " nanoseconds");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper methods
    private static void initializeQueue() {
        // Create queue with unsaturated vertices in V
        queue = new LinkedList<Integer>();
        for (int v : V)
            if (!matching.containsKey(v))
                queue.add(v);
    }

    private static void addEdgeToMatching(int u, int v) {
        // We only add an edge to the matching if the vertices are unsaturated,
        // so we should remove any edges in the matching that saturate u or v.
        // This prevents any issues with overwriting
        if (matching.containsKey(u))
            removeEdgeFromMatching(u, matching.get(u));
        if (matching.containsKey(v))
            removeEdgeFromMatching(v, matching.get(v));
        matching.put(u, v);
        matching.put(v, u);
    }

    private static void removeEdgeFromMatching(int u, int v) {
        // Should only remove the edge if it's there.
        // This prevents any issues with overwriting
        if (edgeInMatching(u, v)) {
            matching.remove(u);
            matching.remove(v);
        }
    }

    private static boolean edgeInMatching(int u, int v) {
        return matching.containsKey(u) && matching.containsKey(v)
            && matching.get(u) == v && matching.get(v) == u;
    }

    private static boolean partitionHasElement(int[] partition, int element) {
        for (int vertex : partition)
            if (vertex == element)
                return true;
        return false;
    }

}