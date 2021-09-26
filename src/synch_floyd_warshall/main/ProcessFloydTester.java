package main;

import processes.LeaderFloyd;
import graphs.GraphFloyd;

import java.io.IOException;

public class ProcessFloydTester {

    public static void main(String[] args) throws InterruptedException, IOException {
    	
        GraphFloyd graph = new GraphFloyd(new java.io.File(".").getCanonicalPath() + "/src/graphs/graphFloyd_positiveWeights.txt");
        graph.readFile();       
                
        LeaderFloyd leader = new LeaderFloyd(graph.getNumberOfNodes(), true);
        graph.setUpProcesses(leader);  
        graph.printInitialMatrices();
        
        leader.processes = graph.getProcesses();
        leader.restartRound();
        
        Thread lt = new Thread(leader); lt.start();
        Thread[] threads = new Thread[graph.getNumberOfNodes()];
        for(int i = 0; i < graph.getNumberOfNodes(); i++) {
            threads[i] = new Thread(leader.processes[i]);
            threads[i].start();
        }
        
        lt.join();
        
        for(int i = 0; i < graph.getNumberOfNodes(); i++) {
            threads[i].join();
        }      

        System.out.println(leader);
    }
}
