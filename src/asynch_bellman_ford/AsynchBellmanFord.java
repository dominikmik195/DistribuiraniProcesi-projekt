package asynch_bellman_ford;

public class AsynchBellmanFord extends Process {
    boolean root;
    int level;
    public AsynchBellmanFord(Linker initComm, boolean _root) {
        super(initComm);
	root = _root;
    }
    public void initiate() {
        if (root) {
	    path = String.valueOf(myId);
	    sendToNeighbours("length", 0, path);
	}
    }
    public void handleMsg(Msg m, int src, String tag) {
        System.out.println(m);
	if (tag.equals("length")) {
                for (int i = 0; i < N; i++) {
                    if (isNeighbor(i) && (i != src) && comm.weights.get(m.srcId)!=Integer.MAX_VALUE && change) {
                        sendMsg(i, "length", length, path);
			change = false;
		    }
		}
        }
    }
}

