package procesi;

/**
 * Class which represents messages exchanged between processes.
 * Contains the ID of the process which is sending the message and current length of the path from the node represented by the process to the source.
 * 
 */
public class Message {
    int ID;
    int length;
    
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
    
    /**
     *  Function which converts this message into a string.
     * @return string representation of the message
     */
    @Override public String toString() {
        return "Message from process with ID " + ID + "\n"
                + "Current length: " + length + "\n";
    }
}
