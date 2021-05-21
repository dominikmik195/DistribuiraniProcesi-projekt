package procesi;

/**
 * Class which represents the leading process.
 * 
 */
public class Leader implements Runnable{
    int numberOfProcesses;
    Process processes[];
    
    public Leader(int _numberOfProcesses) {
        numberOfProcesses = _numberOfProcesses;
    }
    
    @Override public void run() {
        
    }
}
