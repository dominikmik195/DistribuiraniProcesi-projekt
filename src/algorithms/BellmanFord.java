package algorithms;
import java.io.FileNotFoundException;
import procesi.Graph;
import procesi.Leader;

/**
 * Class which represents the execution of the Bellman-Ford algorithm.
 */
public class BellmanFord {

    /**
     * Main function which executes the Bellman-Ford algorithm.
     * @param args the command line arguments
     * @throws InterruptedException rown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity
     * @throws  FileNotFoundException thrown when a file is not found
     */
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        Graph graph = new Graph("E:\\Dominik\\Documents\\NetBeansProjects\\NajkraćiPutevi\\src\\algorithms\\graph.txt");
        graph.readFile();
        
        Leader leader = new Leader(graph.getNumberOfNodes());
        graph.setUpProcesses(leader);
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
        for(int i = 0; i < graph.getNumberOfNodes(); i++) {
            System.out.println(leader.processes[i]);
        }
    }
    
}
