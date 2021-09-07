package asynch_bellman_ford;

import java.io.FileNotFoundException;

public class AsynchBellmanFordTester {


    public static void main(String[] args) throws FileNotFoundException, Exception {
        Linker comm = null;
        Graph graph = new Graph(new java.io.File(".").getCanonicalPath() + "/asynch_bellman_ford/graph.txt");
        graph.readFile();
        
        String baseName = args[0];
        int myId = Integer.parseInt(args[1]);
        int numProc = Integer.parseInt(args[2]);
        
        comm = new Linker(baseName, myId, numProc, graph);

        AsynchBellmanFord app = new AsynchBellmanFord(comm, myId == graph.source);        
        for (int i = 0; i < numProc; i++)
            if (i != myId) (new ListenerThread(i, app)).start();
        app.initiate();
    }
    
}
