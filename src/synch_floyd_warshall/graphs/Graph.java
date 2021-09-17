package graphs;

import java.util.ArrayList;


/**
 * Class which represents a <b>directed weighted graph</b>.
 * The graph is created from a <i>.txt</i> file which contains the ID of the source node and a matrix representing the graph. <br>
 * The class can also create all the processes based on the graph.
 */
public class Graph {
    public ArrayList<ArrayList<Integer>> weights;
    String path;
    int numberOfNodes;
    int source;
    
    /**
     * Constructor which <b>only specifies the path</b> to the <i>.txt</i> file.
     * @param _path path to the file containing the graph
     */
    public Graph(String _path) {
        path = _path;
    }
    
    /**
     * Function which returns the number of nodes.
     * @return number of nodes in the graph
     */
    public int getNumberOfNodes() {
        return numberOfNodes;
    }   
      
}
