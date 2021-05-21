package procesi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class which represents a process.
 * 
 */
public class Process implements Runnable{
    public Leader leaderProcess;
    RoundMessage status;
    int ID;
    int length;
    int parent;
    int numberOfProcesses;
    ArrayList<Process> Neighbours = new ArrayList<>();
    volatile public HashMap<Integer, Integer> weights = new HashMap<>();
    volatile LinkedList<Message> messages = new LinkedList<>();
    
    public Process(int _ID) {
        this.length = Integer.MAX_VALUE;
        this.parent = Integer.MIN_VALUE;
        this.ID = _ID;
        status = new RoundMessage("");
    }
    
    public Process(int _ID, HashMap<Integer, Integer> _toThis, Leader _leaderProcess, int sourceID) {
        this.length = Integer.MAX_VALUE;
        this.parent = Integer.MIN_VALUE;
        this.ID = _ID;
        if(ID == sourceID) length = 0;
    }
    
    public void setUpNeighbours(Process[] _neighbours) {
        for(Process p : _neighbours) Neighbours.add(p);
    }
    
    public void informNeighbours() {
        Message msg = new Message(ID, length);
        for(int i = 0; i < Neighbours.size(); i++) {
            Neighbours.get(i).updateMessages(msg);
        }
    }
    
    public synchronized void updateMessages(Message msg) {
        messages.add(msg);
    }
    
    public void processMessages() {
        Message msg = messages.poll();
        while(msg != null) {
            if(length > (msg.getLength() + weights.get(msg.getProcessID()))) {
                length = msg.getLength() + weights.get(msg.getProcessID());
                parent = msg.getProcessID();
            }
            msg = messages.poll();
        }
    }
    
    public void printMessages() {
        System.out.println("Messages received by the process with ID: " + ID);
        for(Message msg : messages) {
            System.out.println("From process with ID: " + msg.getProcessID());
            System.out.println("Length: " + msg.getLength());
        }
    }
    
    @Override public void run() {
        while(!leaderProcess.pathFound) {
            if(status.getStatus().equals("Round")) {
                informNeighbours();
                status = new RoundMessage("");
                leaderProcess.roundOver(ID);
            }
        }
        System.out.println("Over at " + ID);
    }
    
    @Override public String toString() {
        String result = "Process with ID: " + ID + "\n\n";
        result += "Current messages:\n";
        for(Iterator<Message> it = messages.iterator(); it.hasNext();) {
            result += it.next();     
        }
        result += "\nCurrent status:\n" + status + "\n\n";
        result += "Length: " + length + "\n\n";
        return result;
    }
}
