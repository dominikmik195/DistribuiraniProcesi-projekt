package synch_bellman_ford;

import java.util.*;
import java.io.*;
public class Linker {
    PrintWriter[] dataOut;
    BufferedReader[] dataIn;
    BufferedReader dIn;
    int myId, N;
    int length;
    int parent;
    Connector connector;
    public IntLinkedList neighbors = new IntLinkedList();
    public HashMap<Integer, Integer> weights = new HashMap<>();
    public Linker(String basename, int id, int numProc,Graph g) throws Exception {
        myId = id;
        N = numProc;
        length = Integer.MAX_VALUE;
        parent = Integer.MIN_VALUE;
        if(g.source == myId) {
            length = 0;
            parent = myId;
        }
        dataIn = new BufferedReader[numProc];
        dataOut = new PrintWriter[numProc];
        neighbors = g.findNeighbours(myId);
        for (Object number : neighbors) {
            System.out.println(number);
        }
        weights = g.setUpWeights(myId);
        connector = new Connector();
        connector.Connect(basename, myId, numProc, dataIn, dataOut);
    }
    public void sendMsg(int destId, String tag, String msg) {
        if(tag.equals("length")){
            System.out.println("Sending: " + myId + " " + destId + " " + tag + " " + length + "#");
            dataOut[destId].println(myId + " " + destId + " " + tag + " " + length + "#");
        }
        else if(tag.equals("parent")) {
            System.out.println("Sending: " + myId + " " + destId + " " + tag + " " + msg + "#");
            dataOut[destId].println(myId + " " + destId + " " + tag + " " + msg + "#");
        }
        else dataOut[destId].println(myId + " " + destId + " " + tag + " " + length + "#");
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
        //Util.println(" received message " + getline);
        StringTokenizer st = new StringTokenizer(getline);
        int srcId = Integer.parseInt(st.nextToken());
        int destId = Integer.parseInt(st.nextToken());
        String tag = st.nextToken();
        String msg = st.nextToken("#");
        return new Msg(srcId, destId, tag, msg);        
    }
    public int getMyId() { return myId; }
    public int getNumProc() { return N; }
    public void close() {connector.closeSockets();}
}

