import java.util.ArrayList;

public class ProcessFloydWarshall extends Process{
	
	ArrayList<Integer> parent = new ArrayList<>();
	ArrayList<Integer> length = new ArrayList<>();
	
	public ArrayList<ArrayList<Msg>> isInTreeMsgs;
	Msg pivotLengthMsg;
	boolean pivotLengthMsgRecieved;


	public ProcessFloydWarshall(Linker initComm)
	{
		super(initComm);		
		comm = initComm;
        myId = comm.getMyId();
        N = comm.getNumProc();
        length = comm.length;
        parent = comm.parent;

        isInTreeMsgs = new ArrayList<ArrayList<Msg>>(N);        
        for(int i = 0; i < N; i++)
        {      
        	ArrayList<Msg> pom = new ArrayList<Msg>(N);  
        	isInTreeMsgs.add(pom);        	
        }
        pivotLengthMsg = null;
        pivotLengthMsgRecieved = false;
	}
	

	public void initiate() {

		for(int pivot = 0; pivot < N; ++pivot)
		{
			System.out.println("\n----------------------- PIVOT" + " " + pivot + " --------------------------");

			for (int i = 0; i < N; i++) {
	            if (isNeighbor(i)) {	 
	            	if (parent.get(pivot) == i)
	            		sendMsg(i, "inTree", pivot);
	            	else
	            		sendMsg(i, "notInTree", pivot);
	            }		            
			}

			while(isInTreeMsgs.get(pivot).size() < comm.neighbours.size()) {
				if (isInTreeMsgs.get(pivot).size() == comm.neighbours.size())				
					break;
			}

			if(length.get(pivot) != Integer.MAX_VALUE) {

				if(pivot != myId) {
					pivotLengthMsgRecieved = false;
					sendMsg(pivot, "pivotLengthREQ");

					while(pivotLengthMsgRecieved == false) {					
						if (pivotLengthMsgRecieved)
							break;
					}						
				}

            	for (Msg m: isInTreeMsgs.get(pivot)) {	            	
            		if(m.tag == "inTree") {
            			if(pivot == myId)
            				sendMsg(m.srcId, "pivotLength", pivot, length, parent);
            			else
            				sendMsg(m.srcId, "pivotLength", pivot, pivotLengthMsg.pivotLength, pivotLengthMsg.pivotParent);
            		}
            	}

				for(int t = 0; t<N; ++t) {					
					if(pivot != myId && pivotLengthMsg.pivotLength.get(t) != Integer.MAX_VALUE 
							&& length.get(pivot) + pivotLengthMsg.pivotLength.get(t) < length.get(t)) 
					{
						length.set(t, length.get(pivot)+pivotLengthMsg.pivotLength.get(t));
						parent.set(t, pivotLengthMsg.pivotParent.get(t));
					}
				}	
			}
			
			System.out.println("PARENT: " + parent);
			System.out.println("LENGTH: " + length);
		}
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ PROCES " + myId + " ~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("PARENT: " + parent);
		System.out.println("LENGTH: " + length);
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

	
	}

	public synchronized void handleMsg(Msg m, int src, String tag) {
		
		if (tag.equals("inTree") || tag.equals("notInTree"))
		{      
			isInTreeMsgs.get(Integer.parseInt(m.msgBuf.replaceAll("\\s+",""))).add(m);
        }
        else if (tag.equals("pivotLength"))
        {
        	pivotLengthMsgRecieved = true;
        	pivotLengthMsg = m;
        }
        else if (tag.equals("pivotLengthREQ"))
        {
        	sendMsg(m.srcId, "pivotLength", myId, length, parent); 
        }
	}
}