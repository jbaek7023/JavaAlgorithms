import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class implements Dijkstra's algorithm
 * 
 * @author robertwaters
 * 
 */
public final class Dijkstra {

	/**
	 * 
	 * @param source
	 *            The vertex to start the algorithm from
	 * @param adjList
	 *            the graph to perform the search on.
	 * @return
	 */
	public static List<TableEntry> dijkstra(String source, AdjacencyList adjList) {
		PriorityQueue<Map<Vertex, Map<Vertex, Integer>>> priority = new PriorityQueue<Map<Vertex, Map<Vertex, Integer>>>();
		ArrayList<Map<Vertex, Integer>> values = new ArrayList<Map<Vertex, Integer>>();

		// read the data file
		//PriorityQueue<Map<Vertex, Map<Vertex, Integer>>> priority = new PriorityQueue<Map<Vertex, Map<Vertex, Integer>>>();
		Map<Vertex, Map<Vertex, Integer>> map = adjList.getAdjList();
		List<TableEntry> list = new ArrayList<TableEntry>();

		// Set the Table
		Set<Vertex> keys = adjList.getAdjList().keySet();
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			list.add(new TableEntry(new Vertex(((Vertex) (iter.next()))
					.getName())));
			// priority.poll().
		}

		// the first one's weight is 0.

		// a to e -> e's weight = a+..--

		// for the last time, go to the lighter weight

		// if e is larger than a's weight.

		//

		// dijkstra algorithm
		while (!isFinished(list)) {

		}
		return list;

		//return null;*/
	}

	private static boolean isFinished(List<TableEntry> list) {
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			TableEntry cur = null;
			cur = (TableEntry) iter.next();
			if (cur.known() == false) {
				return false;
			}
			// iter.next();
		}
		// everything is known.
		return true;
	}

}
