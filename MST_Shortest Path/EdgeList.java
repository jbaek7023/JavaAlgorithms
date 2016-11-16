import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * This class represents a graph that uses an edge list for it's representation
 * 
 * @author robertwaters
 *
 */
public class EdgeList {
	
	/**
	 * The list of edges in the graph
	 */
	private Collection<Edge> edges;
    
    /**
     * The set of vertexes in the graph
     */
    private Set<Vertex> vertexes;
	
    private int size=0;
    
	/**
	 * Construct an EdgeList from a given file
	 * 
	 * @param filename the name of the file
	 * @throws IOException Any of the exceptions that might occur while we process the file
	 */
	public EdgeList(String filename) throws IOException {
		// TODO  Open/read/parse the file
		edges = new PriorityQueue<Edge>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()){
        	String line= scanner.nextLine();
        	String[] splits=line.split("," , 100);
           //
                Vertex vertex1 = new Vertex(splits[0]);
                Vertex vertex2 = new Vertex(splits[1]);
                int weight = Integer.parseInt(splits[2]);
                edges.add(new Edge(vertex1,vertex2,weight));
            //
                size++;
        }
	}
	
	@Override
	public String toString() {
		 String result = "";
	        for (Edge edge:edges){
	            result = result+edge+"\n";
	        }
	        return result;
	        /*
		// TODO Extra Credit
		
*/	}
	
	/**
	 * 
	 * @return all the edges in the graph
	 */
	public Collection<Edge> getEdges() {
		return edges;
	}
    
    public Set<Vertex> getVertexes() {
        return vertexes;
    }
    
    public int getSize(){
    	return size;
    }
}
