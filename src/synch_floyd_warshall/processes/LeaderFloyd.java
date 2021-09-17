package processes;

import messages.RoundMessage;

public class LeaderFloyd extends Leader implements Runnable {

	public ProcessFloyd[] processes;
	int pivot;
	boolean withSteps;
	
	public LeaderFloyd(int _numberOfProcesses, boolean... _withSteps) {
		super(_numberOfProcesses);
		withSteps = (_withSteps.length >= 1) ? _withSteps[0] : false;
		pivot = -1;
	}
	
    public ProcessFloyd getProcess(int i) {
    	return processes[i];
    }

    /**
     * Function which begins a new round.
     */
    public void beginRound(String s) {
        RoundMessage r_msg = new RoundMessage(s);
        for(ProcessFloyd p : processes) {
            p.status = r_msg;
        }
    }
    
    @Override public void run() {
    	
    	for(pivot = 0; pivot < numberOfProcesses; pivot++) {

	         beginRound("SendIsInTreeMessages");	         
	         while(!isRoundOver()) { }
	         restartRound();
   	         
	         beginRound("ReceivePivotLengthMessages");
	         while(!isRoundOver()) { }	         
	         restartRound();
	         	         
	         beginRound("SendPivotLengthMessages");
	         while(!isRoundOver()) { }	         
	         restartRound();

	         beginRound("UpdateLengthsAndParents");
	         while(!isRoundOver()) { }
	         restartRound();
	         
	         if(withSteps == true)
	        	 printStep();
    	}    	
        pathFound = true;     
    } 
    
    public void printStep() {
    	if(pivot != numberOfProcesses - 1) {   		

			System.out.println("STEP " + pivot);
    		
    		System.out.println("Distance matrix");	    	
	        for(ProcessFloyd p: processes){	        	
	        	System.out.println(p.length);
	        }	
    		System.out.println("\nPredecessor matrix");	    	
	        for(ProcessFloyd p: processes){	        	
	        	System.out.println(p.parent);
	        }	
	        
	       System.out.println("\n");
    	}        
    }
    

    @Override 
    public String toString() {    	
    	
        String result = "-----------------------------------------\n\n";
        result += "Distance matrix         Predecessor matrix\n\n";
        for(ProcessFloyd p: processes){
        	result += p.length + "          " + p.parent + "\n";
        }	
        
        return result;
    }
    
}

