package najkraćiputevi;
import java.util.ArrayList;
import java.util.HashMap;
import procesi.Leader;
import procesi.Process;
import procesi.Message;

/**
 *
 * @author Dominik
 */
public class NajkraćiPutevi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Process p1 = new Process(0);
        Process p2 = new Process(1);
        Process p3 = new Process(2);
        Process[] ps = {p1, p2, p3};
        
        Process[] ps1 = {p2, p3};
        p1.setUpNeighbours(ps1);
        
        Process[] ps2 = {p1};
        p2.setUpNeighbours(ps2);
        
        Process[] ps3 = {p1};
        p2.setUpNeighbours(ps3);
        
        // ArrayList<ArrayList<Integer>> matrica = new ArrayList<>(3);
        
        HashMap<Integer, Integer> al1 = new HashMap<>(3);
        al1.put(0, -1); al1.put(0, 5); al1.put(0, -1);
        HashMap<Integer, Integer> al2 = new HashMap<>(3);
        al2.put(0, 3); al2.put(0, -1); al2.put(0, -1);
        HashMap<Integer, Integer> al3 = new HashMap<>(3);
        al3.put(0, 2); al3.put(0, -1); al3.put(0, -1);
        // matrica.add(0, al1); matrica.add(0, al2); matrica.add(0, al3);
        p1.weights = al1; p2.weights = al2; p3.weights = al3;
        
        Leader l = new Leader(3);
        l.processes = ps;
        p1.leaderProcess = l; p2.leaderProcess = l; p3.leaderProcess = l;
        l.restartRound();
        
        Thread leader = new Thread(l); leader.start();
        Thread t1 = new Thread(p1); t1.start();
        Thread t2 = new Thread(p2); t2.start();
        Thread t3 = new Thread(p3); t3.start();
        leader.join();
        t1.join(); t2.join(); t3.join();
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
    }
    
}
