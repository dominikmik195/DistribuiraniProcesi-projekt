package procesi;

/**
 * Class which represents messages about the status of a round.
 * Contains a string variable which specifies the status.
 */
public class RoundMessage {
    String roundStatus;
    
    /**
     * A default constructor which creates a message with the status 'Begin'.
     */
    public RoundMessage() {
        roundStatus = "Begin";
    }
    
    /**
     * Constructor which creates a round message specifying the status.
     * 
     * @param status status of current round
     */
    public RoundMessage(String status) {
        roundStatus = status;
    }
    
    /**
     * Function which returns the current status.
     * 
     * @return current status of the round
     */
    public String getStatus() {
        return roundStatus;
    }
    
    /**
     * Function which converts a round message to a string.
     * 
     * @return a string representation of the round message
     */
    @Override public String toString() {
        return "Round status: " + roundStatus;
    }
}
