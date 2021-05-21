/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procesi;

/**
 *
 * @author Dominik
 */
public class RoundMessage {
    String roundStatus;
    
    public RoundMessage() {
        roundStatus = "Begin";
    }
    
    public RoundMessage(String msg) {
        roundStatus = msg;
    }
    
    public String getStatus() {
        return roundStatus;
    }
    
    @Override public String toString() {
        return "Round status: " + roundStatus;
    }
}
