package synch_bellman_ford;

import java.io.FileNotFoundException;

public class SynchBellmanFordTester {


    public static void main(String[] args) throws FileNotFoundException, Exception {
        Linker comm = null;
        Graph graph = new Graph("E:\\Dominik\\Documents\\NetBeansProjects\\Projekt_DP\\src\\synch_bellman_ford\\graph.txt");
        graph.readFile();
        
        String baseName = args[0];
        int myId = Integer.parseInt(args[1]);
        int numProc = Integer.parseInt(args[2]);
        
        comm = new Linker(baseName, myId, numProc, graph);
        AlphaSynch pulser = new AlphaSynch(comm);
        SynchBellmanFord app = new SynchBellmanFord(comm, pulser);        
        for (int i = 0; i < numProc; i++)
            if (i != myId) (new ListenerThread(i, pulser)).start();
        app.initiate();
    }
    
}
