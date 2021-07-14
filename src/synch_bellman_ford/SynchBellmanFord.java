package synch_bellman_ford;

public class SynchBellmanFord extends Process {
    int parent = -1;
    int level;
    AlphaSynch s;
    public SynchBellmanFord(Linker initComm, 
                        AlphaSynch initS) {
        super(initComm);
        s = initS;
    }
    public void initiate() {
        s.initialize(this);
        for (int pulse = 0; pulse < N; pulse++) {
                for (int i = 0; i < N; i++)
                    if (isNeighbor(i))
                        s.sendMessage(i, "length", 0);
            s.nextPulse();
        }
        parent = s.comm.parent;
        System.out.println();
        System.out.println("Parent " + parent);
        if(parent == myId){
            for (int i = 0; i < N; i++)
                    if (isNeighbor(i))
                        s.sendMessage(i, "parent", parent);
        }
    }
    public void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("length")) {
            if (parent == -1) {
                parent = src;
                level = m.getMessageInt();
                for (int i = 0; i < N; i++)
                    if (isNeighbor(i) && (i != src))
                        s.sendMessage(i, "length", 0);
            }
        }
        else if (tag.equals("parent")) {
            if (parent == Integer.parseInt(m.path.strip().split("/")[m.path.strip().split("/").length - 1])) {
                for (int i = 0; i < N; i++)
                    if (isNeighbor(i) && (i != src))
                        s.sendMessage(i, "parent", 0);
            }
        }
    }
}

