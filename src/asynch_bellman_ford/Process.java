package asynch_bellman_ford;

import java.io.*; import java.lang.*;
public class Process implements MsgHandler {
    int N, myId;
    int length;
    int parent;
    String path;
    Linker comm;
    boolean change;
    public Process(Linker initComm) {
        comm = initComm;
        myId = comm.getMyId();
        N = comm.getNumProc();
        length = comm.length;
        parent = comm.parent;
	change = false;
        if(myId == parent) path = String.valueOf(myId);
        else path = "";
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
    }
    public void sendMsg(int destId, String tag, String msg) {
        if(tag.equals("length") || tag.equals("parent")) Util.println("Sending msg to " + destId + ": " +tag + " " + msg);
        comm.sendMsg(destId, tag, msg);
    }
    public void sendMsg(int destId, String tag, int msg) {
        if(tag.equals("parent")) sendMsg(destId, tag, path);
        else sendMsg(destId, tag, String.valueOf(msg)+" ");
    }
    public void sendMsg(int destId, String tag, int msg1, String msg2) {
        sendMsg(destId,tag,String.valueOf(msg1)
        +"_"+msg2+" ");
    }
    public void sendMsg(int destId, String tag) {
        sendMsg(destId, tag, " 0 ");
    }
    public void broadcastMsg(String tag, int msg) {
        for (int i = 0; i < N; i++)
            if (i != myId) sendMsg(i, tag, msg);
    }
    public void sendToNeighbors(String tag, int msg) {
        for (int i = 0; i < N; i++)
            if (isNeighbor(i)) sendMsg(i, tag, msg);     
    }
    public void sendToNeighbours(String tag, int msg1, String msg2) {
        for (int i = 0; i < N; i++)
            if (isNeighbor(i)) sendMsg(i, tag, msg1, msg2);     
    }
    public boolean isNeighbor(int i) {
        if (comm.neighbors.contains(i)){
            return true;
        }
        else return false;
    }
    public Msg receiveMsg(int fromId) {
        try {
            Msg msg = comm.receiveMsg(fromId);
            if(msg.tag.equals("length")) {
                System.out.println("Receiving from " + fromId + " and processing length....\n" + 
                        "current length: " + length + "    msg.length: " + msg.length + "     weight: " + comm.weights.get(msg.srcId));
                if(length > (msg.length + comm.weights.get(msg.srcId))) {
		    System.out.println("flag");
                    if(msg.length == Integer.MAX_VALUE || comm.weights.get(msg.srcId)==Integer.MAX_VALUE) {
			change = false;
                    }
                    else {
			change = true;
                        length = msg.length + comm.weights.get(msg.srcId);
                        parent = msg.srcId;
                        comm.length = length;
                        comm.parent = parent;
			path = msg.path.strip() + "/" + myId;
			System.out.println("------------------------------------------------");
                	System.out.println("new path: " + path + "   length: " + length);
                	System.out.println("------------------------------------------------");
                    }
                }
            }
            return msg;
        } catch (IOException e){
            System.out.println(e);
            comm.close();
            return null;
        }
    }
    public synchronized void myWait() {
        try {
            wait();
        } catch (InterruptedException e) {System.err.println(e);
        }
    }
    
}

