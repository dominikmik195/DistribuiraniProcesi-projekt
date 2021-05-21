package najkraćiputevi;
import procesi.Process;
import procesi.Message;

/**
 *
 * @author Dominik
 */
public class NajkraćiPutevi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Process p1 = new Process(17);
        Process p2 = new Process(31);
        Process[] ps = {p2};
        p1.setUpNeighbours(ps);
        p1.informNeighbours();
        p2.printMessages();
    }
    
}
