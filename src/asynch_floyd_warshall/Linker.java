import java.util.*;
import java.io.*;
public class Linker {
    PrintWriter[] dataOut;
    BufferedReader[] dataIn;
    BufferedReader dIn;
    int myId, N;
    Connector connector;    
    
    public ArrayList<Integer> parent = new ArrayList<>();
	public ArrayList<Integer> length = new ArrayList<>(); 
	public IntLinkedList neighbours = new IntLinkedList();
	public HashMap<Integer, Integer> weights = new HashMap<>();
    
    public Linker(String basename, int id, int numProc, Graph g) throws Exception {
        myId = id;
        N = numProc;
        dataIn = new BufferedReader[numProc];
        dataOut = new PrintWriter[numProc];    
        
        // inicijalizacija       
        // parent[j] = roditelj èvora j na najkraæem poznatom putu od procesa myId do procesa j
        for(int i = 0; i < N; i++) 
        	parent.add(-1);   
        for(Integer p: g.findParents(myId)) {
        	parent.set(p, myId);
        }           
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ PROCES " + myId + " ~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.println("PARENT: " + parent);
        
        // svi susjedi procesa myId bez obzira na usmjerenost brida
        neighbours = g.findNeighbours(myId);
        System.out.println("NEIGHBOURS: " + neighbours);
        
        // length[j] = najkraæi poznati put od procesa myId do procesa j
        weights = g.setUpWeights(myId);
        for(int i = 0; i < N; ++i) {
    		if (i == myId) 
    			length.add(0);
    		else if (neighbours.contains(i))
    			length.add(weights.get(i));
    		else
    			length.add(Integer.MAX_VALUE);
        }
        
        System.out.println("LENGTH: " + length);

        connector = new Connector();
        connector.Connect(basename, myId, numProc, dataIn, dataOut);
    }
    public void sendMsg(int destId, String tag, String msg) {     
    	System.out.println("Sending: proces " + myId + " šalje procesu " + destId + " poruku s tagom " + tag + " sadržaja " + msg + "#");
        dataOut[destId].println(myId + " " + destId + " " + tag + " " + msg + "#");
        dataOut[destId].flush();
    }
    public void sendMsg(int destId, String tag) {
        sendMsg(destId, tag, " 0 ");
    }
    public void multicast(IntLinkedList destIds, String tag, String msg){
        for (int i=0; i<destIds.size(); i++) {
            sendMsg(destIds.getEntry(i), tag, msg);
        }
    }
    public Msg receiveMsg(int fromId) throws IOException  { 

		String getline = dataIn[fromId].readLine();

        Util.println(myId + " received message " + getline);
        StringTokenizer st = new StringTokenizer(getline);
        int srcId = Integer.parseInt(st.nextToken());
        int destId = Integer.parseInt(st.nextToken());
        String tag = st.nextToken();

        if (tag.equals("pivotLength")) {
        	String msg = st.nextToken(); 
        	String strPivotLength = st.nextToken("]");
        	String strPivotParent = st.nextToken("#");
        	
        	int[] arrPivotLength = Arrays.stream(strPivotLength.substring(2, strPivotLength.length()).split(", "))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();
        	
        	int[] arrPivotParent = Arrays.stream(strPivotParent.substring(3, strPivotParent.length() - 1).split(", "))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();
        	
        	ArrayList<Integer> listPivotLength = new ArrayList<Integer>(arrPivotLength.length);
        	ArrayList<Integer> listPivotParent = new ArrayList<Integer>(arrPivotParent.length);
        	
        	for (int i : arrPivotLength)
        		listPivotLength.add(i);

        	for (int i : arrPivotParent)
        		listPivotParent.add(i);
        	
        	return new Msg(srcId, destId, tag, msg, listPivotLength, listPivotParent);
        }
        else { // tag = "inTree" || tag = "notInTree"
        	String msg = st.nextToken("#"); 
        	return new Msg(srcId, destId, tag, msg, null, null);
        }          
    }

    public int getMyId() { return myId; }
    public int getNumProc() { return N; }
    public void close() {connector.closeSockets();}
}
