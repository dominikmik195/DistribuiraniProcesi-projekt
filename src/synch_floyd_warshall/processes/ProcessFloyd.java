package processes;

import java.util.ArrayList;

import messages.MessageIsInTree;
import messages.MessagePivotLength;
import messages.RoundMessage;


public class ProcessFloyd implements Runnable {
	
	int ID, numberOfProcesses;
	int pivot, nbh;
	volatile ArrayList<Integer> parent = new ArrayList<>();
	volatile ArrayList<Integer> length = new ArrayList<>(); // udaljenost najkraæeg poznatog puta do svakog èvora
	ArrayList<Integer> weights = new ArrayList<>(); // length[j - najkraæi poznati put od ovog procesa - ID do ostalih procesa
	ArrayList<ProcessFloyd> neighbours = new ArrayList<ProcessFloyd>();
	volatile ArrayList<MessageIsInTree> messagesIsInTree = new ArrayList<>();
	volatile ArrayList<MessagePivotLength> messagesPivotLength = new ArrayList<>();
	RoundMessage status;
	LeaderFloyd leader;
		
    public ProcessFloyd(int _ID, ArrayList<Integer> _weights, LeaderFloyd _leader) {
    	
    	this.leader = _leader;
    	this.ID = _ID;
    	this.numberOfProcesses = _leader.numberOfProcesses;
    	this.weights = _weights;
    	this.pivot = -1;
    	this.nbh = -1;
    	
    	for(int i = 0; i < numberOfProcesses; ++i) {
    		if (i==ID) 
    			length.add(0);
    		else length.add(Integer.MAX_VALUE);
    	}  

    	// inicijalizacija liste roditelja na sink tree stablu optimiziranom
    	// postavlja se za SUSJEDE da su oni sami sebi roditelji, za nesusjede -1
    	for(int i = 0; i < numberOfProcesses; ++i) {
    		parent.add(-1);
    	}  	 	
    	
    	status = new RoundMessage("");
    }
	
    public int getProcessID() {
        return ID;
    }
    
    public ArrayList<Integer> getProcessLength(){
        return length;
    }
    
    public ArrayList<Integer> getProcessParent(){
        return parent;
    }
    
    public void setUpNeighboursAndParents(ArrayList<ProcessFloyd> _neighbours, ArrayList<Integer> _parents) {
        for(ProcessFloyd neighbour : _neighbours) {
        	neighbours.add(neighbour);
        	length.set(neighbour.ID, weights.get(neighbour.ID));
        }        
        for(Integer p: _parents) {
        	parent.set(p, ID);
        }
    }
    
    public synchronized void updateMessagesIsInTree(MessageIsInTree msg) {
    	messagesIsInTree.add(msg);
    }
    
    public synchronized void updateMessagesPivotLength(MessagePivotLength msg) {
        messagesPivotLength.add(msg);
    }
    
    public synchronized void updateNeighbourPivotLengthMessages(ProcessFloyd nbh, MessagePivotLength msg) {
        nbh.messagesPivotLength.add(msg);
    }
    
    public synchronized void setLengthAndParent(int pivot, int t) {
    	length.set(t, length.get(pivot) + leader.getProcess(pivot).length.get(t));
    	parent.set(t, leader.getProcess(pivot).parent.get(t));
    }
    
    public void sendIsInTreeMessages() {    	
    	for (ProcessFloyd nbh : neighbours) { 
			if (parent.get(pivot) == nbh.ID){ 
				nbh.updateMessagesIsInTree(new MessageIsInTree(pivot, true));
			}
			else { 
				nbh.updateMessagesIsInTree(new MessageIsInTree(pivot, false));					
			}
		}
    }    
    
    public void receivePivotLengthMessages() {
    	if (pivot != ID && length.get(pivot) != Integer.MAX_VALUE) { 
    		updateMessagesPivotLength(new MessagePivotLength(pivot, leader.getProcess(pivot).length));
    	}
    }
    
    public void updateLengthsAndParents() {
    	if(length.get(pivot) != Integer.MAX_VALUE) {   		
			for (int t = 0; t < numberOfProcesses; ++t) {			
				if (leader.getProcess(pivot).length.get(t) != Integer.MAX_VALUE 
						&& length.get(pivot) + leader.getProcess(pivot).length.get(t) < length.get(t)) {
					setLengthAndParent(pivot, t);					
				}    					
			} 		
    	}
    }
    
    public void sendPivotLengthMessages() {
    	if(length.get(pivot) != Integer.MAX_VALUE) {   		
	    	for (ProcessFloyd nbh : neighbours) {      				
	    		for (MessageIsInTree msg: messagesIsInTree)
	    			if (nbh.getProcessID() == msg.getProcessID() && msg.getIsInTree() == true) {
	    				if (pivot == ID)
	    					updateNeighbourPivotLengthMessages(nbh, new MessagePivotLength(pivot, length));
	    				else updateNeighbourPivotLengthMessages(nbh, new MessagePivotLength(pivot, leader.getProcess(pivot).length));  
	    			}    						
	    	}
    	}
    }
    
	public void completeRound() {
		status = new RoundMessage("");
		leader.roundOver(ID);
	}          

    @Override 
    public void run() {    	
    	while(!leader.pathFound) {      		
    		pivot = leader.pivot;
    	
   		 	// each node awaits a IN_TREE or NOT_IN_TREE message from each of its neighbors to identify it children
			if(status.getStatus().equals("SendIsInTreeMessages")) {  
				sendIsInTreeMessages(); 
				completeRound();
        	}

			// receive PIVOT_ROW from the parent
			else if(status.getStatus().equals("ReceivePivotLengthMessages")) {       				
				receivePivotLengthMessages();
				completeRound();
			}
		
			// send PIVOT_ROW to the children
			else if(status.getStatus().equals("SendPivotLengthMessages")) {       				
				sendPivotLengthMessages();		
				completeRound();
			}        			       		
			
			else if(status.getStatus().equals("UpdateLengthsAndParents")) {	       				        				
				updateLengthsAndParents();  
				completeRound();
			}   			     			
		}
	}
    
}
