import java.io.*;
import java.util.Scanner;
import java.util.HashMap;

public class Solution {

    public static final char[] ALPHABET = {'A','B','C','D','E','F','G','H',
                                           'I','J','K','L','M','N','O','P',
                                           'Q','R','S','T','U','V','W','X',
                                           'Y','Z',' '}; 
    public static String pattern;
    public static String text;
    public static int indexFound;
    public static int runTime;

    public static void main(String[] args) {
        readInput("input.txt");
        long startTime = System.nanoTime();
        findIndex();
        long endTime = System.nanoTime();
        runTime = (int)(endTime - startTime);
        createOutput("output.txt");
    }
    
    public static void findIndex() {
        // Create bad shift table
        HashMap<Character, Integer> badShiftTable = new HashMap<Character, Integer>();
        int m = pattern.length();
        int n = text.length();
        if (m > n) {
            indexFound = -1;
            return;
        }
        for (char key : ALPHABET) {
            int shift = 1;
            int i = m - 2;
            while (i >= 0 && pattern.charAt(i) != key) {
                shift++;
                i--;
            }
            badShiftTable.put(key, shift);
        }
        // Create good shift table
        int[] goodShiftTable = new int[m];
        for (int k = 1; k < m; k++) {
            String match = pattern.substring(m - k, m);
            char badChar = pattern.charAt(m - k - 1);
            for (int i = m - k; i >= 0; i--) {
                int start = Math.max(0, i - k);
                String potentialMatch = pattern.substring(start, i);
                if (start > 0) {
                    if (match.equals(potentialMatch) && badChar != pattern.charAt(start - 1)) {
                        goodShiftTable[k] = m - i;
                        break;
                    } else
                        continue;
                } else {
                    if (match.substring(k - i, k).equals(potentialMatch)) {
                        goodShiftTable[k] = m - i;
                        break;
                    } else
                        continue;
                }
            }
        }
        // Determine indexFound
        int index = m - 1;
        while (index <= n - 1) {
            int k = 0; // Number of matched characters
            while (k <= m - 1 && pattern.charAt(m - 1 - k) == text.charAt(index - k))
                k++;
            if (k == m) {
                indexFound = index - m + 1;
                return;
            } else
                index += Math.max(badShiftTable.get(text.charAt(index)), goodShiftTable[k]);
        }
        indexFound = -1;
    }
    
    public static void readInput(String fileName) {
        try {
            // Try to open file
            Scanner fileReader = new Scanner(new File(fileName));
            // We will search case-insensitively
            pattern = fileReader.nextLine().toUpperCase();
            text = fileReader.nextLine().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createOutput(String fileName) {
        try {
            PrintWriter fileWriter = new PrintWriter(
                new FileOutputStream(new File(fileName)));
            fileWriter.println(indexFound);
            fileWriter.println(runTime + " nanoseconds");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}