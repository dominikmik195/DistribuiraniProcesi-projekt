package messages;


public class Message {
	
	int ID;	
	
    /**
     * Constructor which creates a message specifiyng the ID of the process.
     *
     * @param _ID ID of the process sending the message
     */
	public Message(int _ID) {
		ID = _ID;
	}	
	
    /**
     * Function which returns the ID of the process.
     * 
     * @return ID of the process sending the message
     */
    public int getProcessID() {
        return ID;
    }
           
}
