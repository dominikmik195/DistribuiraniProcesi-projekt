
package synch_bellman_ford;

import java.io.*;

public interface MsgHandler {
    public void handleMsg(Msg m, int srcId, String tag);
    public Msg receiveMsg(int fromId) throws IOException;
}
