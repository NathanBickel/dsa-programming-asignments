Preconditions: 
* "input.txt" exists in the current directory
* Line 1 contains s space separated non-negative integers
* Line 2 contains t space separate non-negative integers
* Next, s+t rows follow, where each row contains
s+t space-separated instances of "0" or "1"
* This array is an adjacency matrix for a simple bipartite graph,
where the vertices on line 1 and the vertices on line 2 form a
bipartition and correspond to the indices in the adjacency matrix

Postconditions:
* Solution.java will generate a file named "output.txt"
* Line 1 will contain all the vertices from Line 1 in the input that
are the endpoints in a maximum matching in the graph, separated by spaces
* Line 2 will contain the vertices from Line 2 in the input that are
the endpoints in the same maximum matching in the graph, separated by spaces
* If a vertex u appears at position i on line 1 and v appears at position i
on line 2, then uv is an edge in the maximum matching (for example, if
0 appears fourth on line 1 and 6 appears fourth on line 2, then the edge
between 0 and 6 is in the maximum matching)
* "output.txt" will also contain the execution time for
running the algorithm, excluding time of input/output.
This time will be in nanoseconds.

To run:
1. Create a valid file "input.txt" in the current directory
2. Run `javac Solution.java`
3. Run `java Solution.java`