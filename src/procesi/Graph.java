package procesi;

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class which represents a <b>directed weighted graph</b>.
 * The graph is created from a <i>.txt</i> file which contains the ID of the source node and a matrix representing the graph. <br>
 * The class can also create all the processes based on the graph.
 */
public class Graph {
    public ArrayList<ArrayList<Integer>> weights;
    String path;
    int numberOfNodes;
    Process[] processes;
    int source;
    
    /**
     * Constructor which <b>only specifies the path</b> to the <i>.txt</i> file.
     * @param _path path to the file containing the graph
     */
    public Graph(String _path) {
        path = _path;
    }
    
    /**
     * Function which reads the file given by the path variable and creates a matrix of all the weights.
     * @throws FileNotFoundException the exception is thrown when the requested file is not found
     */
    public void readFile() throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        weights = new ArrayList<>();
        boolean first = true;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = line.strip();
            if(first) {
                source = Integer.parseInt(line);
                first = false;
                continue;
            }
            ArrayList<Integer> row = new ArrayList<>();
            String[] numbers = line.split(" ");
            for(String n : numbers) {
                row.add(Integer.parseInt(n));
            }
            weights.add(row);
        }
        numberOfNodes = weights.size();
        scanner.close();
    }
    
    /**
     * Function which returns the number of nodes.
     * @return number of nodes in the graph
     */
    public int getNumberOfNodes() {
        return numberOfNodes;
    }
    
    /**
     * Function which creates the processes from the matrix of all the weights.
     * @param leader the process which leads an execution of the algorithm
     */
    public void setUpProcesses(Leader leader) {
        processes = new Process[numberOfNodes];
        for(int i = 0; i < numberOfNodes; i++) {
            processes[i] = new Process(i, setUpWeights(i), leader, source);
        }
        for (int i = 0; i < numberOfNodes; i++) {
            processes[i].setUpNeighbours(findNeighbours(i));
        }
    }
    
    /**
     * Function which returns an array of all the processes.
     * @return an array of all the processes
     */
    public Process[] getProcesses() {
        return processes;
    }
    
    /**
     * Function which finds all the neighbours of a process(node).
     * @param node the ID of the node
     * @return list of all the adjacent processes(nodes)
     */
    private ArrayList<Process> findNeighbours(int node) {
        ArrayList<Process> neighbours = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; i++) {
            if(weights.get(node).get(i) != -1 || weights.get(i).get(node) != -1)
                neighbours.add(processes[i]);
        }
        return neighbours;
    }
    
    /**
     * Function which creates a map of all the weights from all the other processes(nodes) to the given process(node).
     * @param node the ID of the node
     * @return map of all the weights
     */
    private HashMap<Integer, Integer> setUpWeights(int node) {
        HashMap<Integer, Integer> _weights = new HashMap<>();
        for (int i = 0; i < numberOfNodes; i++) {
            _weights.put(i, weights.get(i).get(node));
        }
        return _weights;
    }
    
}
