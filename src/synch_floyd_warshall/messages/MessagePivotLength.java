package messages;
import java.util.ArrayList;


public class MessagePivotLength extends Message{
	
	volatile ArrayList<Integer> length = new ArrayList<>(); 
	
	public MessagePivotLength(int _ID, ArrayList<Integer> _length) {
		super(_ID);
		length = _length;
	}
	
    public ArrayList<Integer> getLength() {
        return length;
    }
}
