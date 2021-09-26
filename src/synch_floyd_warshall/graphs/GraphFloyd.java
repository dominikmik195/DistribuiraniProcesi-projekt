package graphs;
import processes.ProcessFloyd;
import processes.LeaderFloyd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphFloyd extends Graph {
	
	ProcessFloyd[] processes;
	
    public GraphFloyd(String _path) {
		super(_path);
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
            	if (n.equals("x"))
            		row.add(Integer.MAX_VALUE);
            	else
            		row.add(Integer.parseInt(n));
            }
            weights.add(row);
        }
        numberOfNodes = weights.size();
        scanner.close();
    }
    
    public void setUpProcesses(LeaderFloyd leader) {
        processes = new ProcessFloyd[numberOfNodes];
        
        for(int i = 0; i < numberOfNodes; i++) {
            processes[i] = new ProcessFloyd(i, weights.get(i), leader);
        }

        for (int i = 0; i < numberOfNodes; i++) {
            processes[i].setUpNeighboursAndParents(findNeighbours(i), findParents(i));            
        }
    }
    
    public void printInitialMatrices() {
	    System.out.println("Initial matrices");
		System.out.println("Distance matrix");	    	
	    for(ProcessFloyd p: processes){	        	
	    	System.out.println(p.getProcessLength());
	    }	
		System.out.println("\nPredecessor matrix");	    	
	    for(ProcessFloyd p: processes){	        	
	    	System.out.println(p.getProcessParent());
	    }	
	    System.out.println("");
	}
    
    /**
     * Function which returns an array of all the processes.
     * @return an array of all the processes
     */
    public ProcessFloyd[] getProcesses() {
        return processes;
    }
    
    private ArrayList<ProcessFloyd> findNeighbours(int node) {
        ArrayList<ProcessFloyd> neighbours = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; i++) {
            if(weights.get(node).get(i) != Integer.MAX_VALUE || weights.get(i).get(node) != Integer.MAX_VALUE ) { 
            	if (!(weights.get(node).get(i) == 0 && weights.get(i).get(node) == weights.get(node).get(i))){
            		neighbours.add(processes[i]);
            	}
            }
        }
        return neighbours;
    }
    
    private ArrayList<Integer> findParents(int node) {
    	ArrayList<Integer> parents = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; i++) {
            if(weights.get(node).get(i) != 0 && weights.get(node).get(i) != Integer.MAX_VALUE) {
                parents.add(i);       	
            }
        }
        return parents;
    }

}
