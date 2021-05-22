package procesi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class which represents a process. <br>
 * The process knows the number of all the processes and weights of all of its edges.
 * It can send messages only to its neighbours.
 * Also it can only receive messages from its neighbours and save them for later processing, depending on the status of the round.
 * <br>
 * Tha class implements the <i>run</i> method from the <i>Runnable</i> interface.
 * 
 */
public class Process implements Runnable{
    Leader leaderProcess;
    RoundMessage status;
    int ID;
    int length;
    int parent;
    int numberOfProcesses;
    ArrayList<Process> Neighbours = new ArrayList<>();
    volatile HashMap<Integer, Integer> weights = new HashMap<>();
    volatile LinkedList<Message> messages = new LinkedList<>();
    
    /**
     * Counstructor which creates a process specifying its ID, weights and the leader process.
     * 
     * @param _ID ID of the process(node)
     * @param _weights a map of all the weights of edges from the other processes to the current
     * @param _leaderProcess the leader process
     * @param sourceID the ID of the source process(node)
     */
    public Process(int _ID, HashMap<Integer, Integer> _weights, Leader _leaderProcess, int sourceID) {
        this.length = Integer.MAX_VALUE;
        this.parent = Integer.MIN_VALUE;
        this.ID = _ID;
        if(ID == sourceID) length = 0;
        status = new RoundMessage("");
        weights = _weights;
        leaderProcess = _leaderProcess;
    }
    
    /**
     * Function which saves all the adjacent processes.
     * @param _neighbours list of all the neighbours
     */
    public void setUpNeighbours(ArrayList<Process> _neighbours) {
        Neighbours = _neighbours;
    }
    
    /**
     * Function which sends messages to all the neighbours containing the 
     * ID of the sender and the length of a path from the source to the current process.
     */
    public void informNeighbours() {
        Message msg = new Message(ID, length);
        for(int i = 0; i < Neighbours.size(); i++) {
            Neighbours.get(i).updateMessages(msg);
        }
    }
    
    /**
     * Function which adds a message to the list of messages.
     * @param msg the message to be added to the list
     */
    public synchronized void updateMessages(Message msg) {
        messages.add(msg);
    }
    
    /**
     * Function which reads all the messages and updates the variables.
     */
    public void processMessages() {
        Message msg = messages.poll();
        while(msg != null) {
            if(length > (msg.getLength() + weights.get(msg.getProcessID()))) {
                if(msg.getLength() == Integer.MAX_VALUE || weights.get(msg.getProcessID())==-1) {
                    msg = messages.poll();
                    continue;
                }
                length = msg.getLength() + weights.get(msg.getProcessID());
                parent = msg.getProcessID();
            }
            msg = messages.poll();
        }
    }
    
    /**
     * When a thread is created by an object of this class,
     * starting the thread causes this method to be calles in the executing thread. <br>
     * This function is used for waiting for incoming messages, starting the process
     * of reading them and sending new messages.
     */
    @Override public void run() {
        while(!leaderProcess.pathFound) {
            if(status.getStatus().equals("Round")) {
                informNeighbours();
                status = new RoundMessage("");
                leaderProcess.roundOver(ID);
            }
            else if (status.getStatus().equals("Messages")) {
                processMessages();
                status = new RoundMessage("");
                leaderProcess.roundOver(ID);
            }
        }
    }
    
    /**
     * Function which converts this process into a string.
     * @return a string representing the process
     */
    @Override public String toString() {
        String result = "-----------------------------------\n";
        result += "Process with ID: " + ID + "\n\n";
        result += "Current messages:\n";
        for(Iterator<Message> it = messages.iterator(); it.hasNext();) {
            result += it.next();     
        }
        result += "\n" + status + "\n";
        result += "Length: " + length + "\n";
        return result;
    }
}
