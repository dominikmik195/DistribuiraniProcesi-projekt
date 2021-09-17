import java.util.*;
public class Msg {
    int srcId, destId;
    String tag;
    String msgBuf;
    ArrayList<Integer> pivotLength = new ArrayList<>(); 
    ArrayList<Integer> pivotParent = new ArrayList<>(); 
    
    public Msg(int s, int t, String msgType, String buf, ArrayList<Integer> _pivotLength,  ArrayList<Integer> _pivotParent) {
        this.srcId = s;
        destId = t;
        tag = msgType;
        msgBuf = buf;
        pivotLength = _pivotLength;
        pivotParent = _pivotParent;
    }
    public int getSrcId() {
        return srcId;
    }
    public int getDestId() {
        return destId;
    }
    public String getTag() {
        return tag;
    }
    public String getMessage() {
        return msgBuf;
    }
    public ArrayList<Integer> getPivotRow() {
        return pivotLength;
    }
    public ArrayList<Integer> getPivotParent() {
        return pivotParent;
    }
    public int getMessageInt() {
        StringTokenizer st = new StringTokenizer(msgBuf);
        return Integer.parseInt(st.nextToken());
    }
    public static Msg parseMsg(StringTokenizer st){
        int srcId = Integer.parseInt(st.nextToken());
        int destId = Integer.parseInt(st.nextToken());
        String tag = st.nextToken();
        
        if (tag.equals("pivotLength")) {
        	String msg = st.nextToken(); 
        	String strPivotLength = st.nextToken("]");
        	String strPivotParent = st.nextToken("#");
        	
        	int[] arrPivotLength = Arrays.stream(strPivotLength.substring(2, strPivotLength.length()).split(", "))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();
        	
        	int[] arrPivotParent = Arrays.stream(strPivotParent.substring(3, strPivotParent.length() - 1).split(", "))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();
        	
        	ArrayList<Integer> listPivotLength = new ArrayList<Integer>(arrPivotLength.length);
        	ArrayList<Integer> listPivotParent = new ArrayList<Integer>(arrPivotParent.length);
        	
        	for (int i : arrPivotLength)
        		listPivotLength.add(i);

        	for (int i : arrPivotParent)
        		listPivotParent.add(i);
        	
        	return new Msg(srcId, destId, tag, msg, listPivotLength, listPivotParent);
        }
        else { // tag = "inTree" || tag = "notInTree"
        	String msg = st.nextToken("#"); 
        	return new Msg(srcId, destId, tag, msg, null, null);
        }  
    }
    public String toString(){
        String s = String.valueOf(srcId)+" " +
                    String.valueOf(destId)+ " " +
                    tag + " " + msgBuf + "#";
        return s;
    }
    
}
