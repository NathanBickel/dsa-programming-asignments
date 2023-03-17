Preconditions: 
* "input.txt" exists in the current directory
* File contains n rows, where n is some integer > 3
* Each row contains n space-seperated strings, where each
string is exactly "true" or "false"
* The boolean matrix is the adjacency matrix for a simple graph
on n vertices

Postconditions:
* Solution.java will generate a file named "output.txt"
* "output.txt" will contain "ring" if the graph is a cycle,
"star" if the graph is a star, "mesh" if the graph is a
complete graph, and "none" otherwise.
* "output.txt" will also contain the execution time for
running the algorithm, excluding time of input/output.
This time will be in nanoseconds.

To run:
1. Create a valid file "input.txt" in the current directory
2. Run `javac Solution.java`
3. Run `java Solution.java`