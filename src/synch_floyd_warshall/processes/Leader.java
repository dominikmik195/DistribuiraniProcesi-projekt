package processes;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Class which represents the leading process which manages all the other processes. <br>
 * The leader informs all the other processes about the current status of the round,
 * begins a new round and ends the execution when necessary.
 * 
 */
public class Leader {
    public int numberOfProcesses;
    volatile ArrayList<Boolean> roundStatus;
    volatile boolean pathFound;
    
    /**
     * Constructor which creates the leader based on the number of processes.
     * @param _numberOfProcesses number of processes in the graph
     */
    public Leader(int _numberOfProcesses) {       
        numberOfProcesses = _numberOfProcesses;
        roundStatus = new ArrayList<>(numberOfProcesses);
        pathFound = false;
    }
    
    /**
     * Function which signals that a certain process ended a round.
     * 
     * @param ID ID of the process which ended a round
     */   
	public synchronized void roundOver(int ID) {
	    roundStatus.set(ID, true);
	}
     
	/**
	 * Function which restarts a round.
	 */
    public synchronized void restartRound() {
         roundStatus.clear();
         for(int i = 0; i < numberOfProcesses; i++) {
             roundStatus.add(i, false);
         }
     }
     
     /**
      * Function which checks wether a round is over.
      * @return true if the round is over, false otherwise
      */
     public synchronized boolean isRoundOver() {
         Iterator<Boolean> it = roundStatus.iterator();
         while(it.hasNext()){
             boolean val = it.next();
             if(!val) return false;
         }
         return true;
     }   
}