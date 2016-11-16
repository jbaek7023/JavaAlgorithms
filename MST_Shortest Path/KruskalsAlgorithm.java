import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * This class encapsulates Kruskal's algorithm for finding a minimum spanning tree 
 * 
 * @author robertwaters
 *
 */
public final class KruskalsAlgorithm {

	@SuppressWarnings("unchecked")
	public static Collection<Edge> kruskal(EdgeList edgeList) {
		List<Edge> edges = new ArrayList<>(edgeList.getEdges());
		Collections.sort(edges);
		UnionFind set = new UnionFind(edgeList.getVertexes());
		ArrayList<Edge> returnList = new ArrayList<Edge>();
		for (Edge e : edges) {
			Vertex source = e.getSource();
			Vertex destination = e.getDestination();
			if (!set.sameSet(source, destination)) {
				set.union(source, destination);
				returnList.add(e);
			}
		}
		return returnList;
		

		/*DisjointSets dj_set = new DisjointSets(edges.getVertices());
		Collection<Edge> mst = new ArrayList<Edge>();
		for (Edge edge:edges){
			Vertex vert1 = edge.vertices()[0];
			Vertex vert2 = edge.vertices()[1];
			if (!dj_set.sameSet(vert1, vert2)){
				dj_set.merge(vert1, vert2);
				mst.add(edge);
			}
		}
		return mst;
		
	}
	*/
	}
}
