package procesi;

/**
 * Class which represents messages exchanged between processes.
 * 
 */
public class Message {
    private int ID;
    private int length;
    
    /**
     * Constructor which creates a message specifiyng the ID of the process and current length.
     *
     * @param _ID ID of the process sending the message
     * @param _length length from source to the process
     */
    public Message(int _ID, int _length) {
        ID = _ID;
        length = _length;
    }
    
    /**
     * Function which returns the ID of the process.
     * 
     * @return ID of the process sending the message
     */
    public int getProcessID() {
        return ID;
    }
    
    /**
     * Function which returns the length.
     * 
     * @return length between the source and process
     */
    public int getLength() {
        return length;
    }
    
    @Override public String toString() {
        return "Message from process with ID " + ID + "\n"
                + "Current length: " + length + "\n";
    }
}
