package messages;

public class MessageIsInTree extends Message {
	
	boolean isInTree;

	public MessageIsInTree(int _ID, boolean _isInTree) {
		super(_ID);
		isInTree = _isInTree;
	}
	
    public boolean getIsInTree() {
        return isInTree;
    }
}
