import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class AdjacencyList {

	/**
	 * This is a map (Hash Table) of vertices to their adjacent vertices and the weight associated with that edge
	 */
	private Map<Vertex, Map<Vertex, Integer>> adjList;
    private GraphType myType = GraphType.UNDIRECTED;
    private int entries=0;
    /**
     * Create an adjacency list from a file
     */
	@SuppressWarnings("null")
	public AdjacencyList(String filename, GraphType pType) throws IOException {
		adjList= new HashMap<Vertex, Map<Vertex, Integer>>();
		File file = new File(filename);
		Scanner fileScanner = null;
		try{
			fileScanner = new Scanner(file);
		} catch(FileNotFoundException e){
			 e.printStackTrace();
		}
		while (fileScanner.hasNextLine()){
			String line= fileScanner.nextLine();
			String[] splits=line.split("," , 100);
			Map<Vertex, Integer> valueInput = new HashMap<Vertex, Integer>();
			valueInput.put(new Vertex(splits[1]), Integer.parseInt(splits[2]));
			adjList.put(new Vertex(splits[0]), 	valueInput);
			entries++;
		}
	}

	@Override
	public String toString() {
		Map<String, String> map= new TreeMap<String, String>();
		for (Map.Entry entry : adjList.entrySet()) {
		    map.put(entry.getKey().toString(), entry.getValue().toString());
		}

		//then you just access the reversedMap however you like...
		for (Map.Entry entry : map.entrySet()) {
		    System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		return "AdjacencyList";
	}
	
    /**
     *  Find a vertex by its name
     *  @param  name   the name of the vertex to find
     *  @return the vertex with that name (or null if none)
     */
	public Vertex findVertexByName(String name) {
       if( adjList.containsKey(new Vertex(name))){
    	   return (Vertex) adjList.get(new Vertex(name));
       } else {
    	   return null;
       }
    }
    
    /**
     * @return the number of vertexes in the graph
     */
    public int vertexCount() {
        return entries;
    }
    
	public Map<Vertex, Map<Vertex, Integer>> getAdjList() {
		return adjList;
	}
}
