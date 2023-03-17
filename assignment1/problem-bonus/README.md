Preconditions: 
* Folders "inputs" and "outputs" exist in current directory
* Each folder has five folders for each size
* Each test case has doubles separated by spaces

Postconditions:
* For each test case, Solution.java will generate a file in
the corresponding directory
* This file will contain the test cases's space-seperated doubles
in non-decreasing order
* The file will also contain the execution time for
running the algorithm, excluding time of input/output.
This time will be in nanoseconds.
* runTimes.txt will be generated, which contains all runtimes
* runTimesAverage.txt will be generated, which contains the average
runtime for each distinct size

To run:
1. Run `mkdir inputs outputs`
2. Run `mkdir inputs/size10 inputs/size100 inputs/size1000 inputs/size10000 inputs/size100000`
3. Run `mkdir outputs/size10 outputs/size100 outputs/size1000 outputs/size10000 outputs/size100000`
2. Run `javac Solution.java`
3. Run `java Solution.java`