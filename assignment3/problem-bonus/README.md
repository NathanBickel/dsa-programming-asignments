Preconditions: 
* "input.txt" exists in the current directory
* Line 1 contains a pattern containing only letters (either
lowercase or uppercase) and spaces
* Line 2 contains text containing only letters (either
lowercase or uppercase) and spaces

Postconditions:
* Solution.java will generate a file named "output.txt"
* Line 1 will contain the index of the first occurence
of the pattern in the text (this match will be case
-insensitive), or -1 if there is no match
* "output.txt" will also contain the execution time for
running the algorithm, excluding time of input/output.
This time will be in nanoseconds.

To run:
1. Create a valid file "input.txt" in the current directory
2. Run `javac Solution.java`
3. Run `java Solution.java`