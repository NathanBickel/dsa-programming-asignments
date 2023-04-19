Preconditions: 
* "input.txt" exists in the current directory
* File contains n rows, where each row contains
n space-separated doubles
* These doubles form an adjacency matrix of a digraph
with no loops, where an edge not being present between
two vertices is denoted with a 0

Postconditions:
* Solution.java will generate a file named "output.txt"
* File will contain the adjacency matrix of a complete digraph
where the weight of an edge uv is the minimum distance in the
given graph from u to v
* If there is no path from u to v, the entry uv will have value "inf"
* "output.txt" will also contain the execution time for
running the algorithm, excluding time of input/output.
This time will be in nanoseconds.

To run:
1. Create a valid file "input.txt" in the current directory
2. Run `javac Solution.java`
3. Run `java Solution.java`