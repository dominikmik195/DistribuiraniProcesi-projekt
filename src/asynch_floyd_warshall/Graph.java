import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {
    public ArrayList<ArrayList<Integer>> weights;
    String path;
    int numberOfNodes;
    Process[] processes;
    int source;
    
    public Graph(String _path) {
        path = _path;
    }
    
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
                try {
                    row.add(Integer.parseInt(n));
                }
                catch(NumberFormatException ex) {
                    row.add(Integer.MAX_VALUE);
                }
            }
            weights.add(row);
        }
        numberOfNodes = weights.size();
        scanner.close();
    }    
    
    public int getNumberOfNodes() {
        return numberOfNodes;
    }
    
    public IntLinkedList findNeighbours(int node) {
        IntLinkedList neighbours = new IntLinkedList();
        for(int i = 0; i < numberOfNodes; i++) {
            if(weights.get(node).get(i) != Integer.MAX_VALUE || weights.get(i).get(node) != Integer.MAX_VALUE)
            	if (!(weights.get(node).get(i) == 0 && weights.get(i).get(node) == weights.get(node).get(i)))
            		neighbours.add(i);
        }
        return neighbours;
    }
    
    public HashMap<Integer, Integer> setUpWeights(int node) {
        HashMap<Integer, Integer> _weights = new HashMap<>();
        for (int i = 0; i < numberOfNodes; i++) {
            _weights.put(i, weights.get(node).get(i));           
        }
        return _weights;
    }
    
    public ArrayList<Integer> findParents(int node) {
    	ArrayList<Integer> parents = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; i++) {
            if(weights.get(node).get(i) != 0 && weights.get(node).get(i) != Integer.MAX_VALUE) {
                parents.add(i);       	
            }
        }
        return parents;
    }  
}