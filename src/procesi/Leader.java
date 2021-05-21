package procesi;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class which represents the leading process.
 * 
 */
public class Leader implements Runnable{
    int numberOfProcesses;
    public Process processes[];
    volatile ArrayList<Boolean> roundStatus;
    public volatile boolean pathFound;
    
    public Leader(int _numberOfProcesses) {
        numberOfProcesses = _numberOfProcesses;
        roundStatus = new ArrayList<>(numberOfProcesses);
        //restartRound();
        pathFound = false;
    }
    
    public synchronized void roundOver(int ID) {
        System.out.println("ID: " + ID);
        roundStatus.set(ID, true);
    }
    
    public void restartRound() {
        roundStatus.clear();
        System.out.println("________________________________________");
        for(int i = 0; i < numberOfProcesses; i++) {
            roundStatus.add(i, false);
        }
    }
    
    public synchronized boolean isRoundOver() {
        System.out.println("---------------------" + roundStatus.size());
        Iterator<Boolean> it = roundStatus.iterator();
        while(it.hasNext()){
            boolean val = it.next();
            if(!val) return false;
        }
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        return true;
    }
    
    public void beginRound() {
        RoundMessage r_msg = new RoundMessage("Round");
        for(Process p : processes) {
            System.out.println("Hm");
            p.status = r_msg;
        }
        //System.out.println(processes[0]);
    }
    
    public void messages() {
        RoundMessage r_msg = new RoundMessage("Messages");
        for(Process p : processes) {
            p.status = r_msg;
        }
    }
    
    @Override public void run() {
        beginRound();
        while(!isRoundOver()) { }
        System.out.println("Yaaay");
        messages();
        System.out.println("Ovo?");
        pathFound = true;
    }
}
