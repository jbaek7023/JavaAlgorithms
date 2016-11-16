import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This class implements the union find algorithm
 * 
 * @author robertwaters
 *
 */
public class UnionFind {
	/*private HashMap<Vertex, VertexPoint> set = new HashMap<>();
	
	private class VertexPoint {
		int rank;
		Vertex parent;
		
		public VertexPoint(Vertex parent, int rank) {
			this.parent = parent;
			this.rank = rank;
		}
	}
	*/
	

	Map<Vertex, Vertex> parents;
	Set<Vertex> vertices;
	/**
	 * This should implement the make_set function of union find
	 * 
	 * @param edgeList  the list to create the disjoint sets from
	 */
	public UnionFind(Set<Vertex> vertexes) {
		parents = new HashMap<Vertex, Vertex>();
		this.vertices = vertices;
	}

	/*private void makeSet(Vertex vertex) {
		set.put(vertex, new VertexPoint(vertex, 0));
	}
	*/
	
	
	public boolean sameSet(Vertex u, Vertex v) {
		Vertex u_root = find(u);
		Vertex v_root = find(v);
		if (v_root == null){
			v_root = v;
		}
		if (u_root == null){
			u_root = u;
		}
		if (u_root.equals(v_root)){
			return true;
		}
		return false;
	}
	
	////////////
	/**
	 * Assume that u is a vertex. Determine if they are currently
	 * in the same component of this UnionFind structure
	 * 
	 * @param u the vertex we want to find the set for
	 * @return the name of the set that u is in
	 */
	public Vertex find(Vertex u) {
		Vertex parent = parents.get(u);
		if (parent == null){
			// Case: vertex has no parent
			return parent;
		}
		else {
			while (parents.get(parent)!=null){
				parent = parents.get(parent);
			}
			return parent;
		}

	}
	
	/*private Vertex findSet(Vertex u) {
		VertexPoint node = set.get(u);
		if (node == null) {
			return u;
		} else if (u != node.parent) {
			node.parent = findSet(node.parent);
		}
		return node.parent;
	}
	

	public boolean sameValue(Vertex u, Vertex v) {
		Vertex parentU = findSet(u);
		Vertex parentV = findSet(v);
		return parentU.equals(parentV);
	}*/

	/**
	 * Assume that u and v are vertices that were in the edgeList. Assume that u and v are in
	 * different components (you have already checked find). Union the component containing u and the component containing v
	 * together.
	 * 
	 * @param u
	 *            a vertex
	 * @param v
	 *            a vertex
	 */
	public void union(Vertex u, Vertex v) {
		if (!sameSet(u, v)){
			Vertex v_root = find(v);
			Vertex u_root = find(u);
			if (v_root == null){
				v_root = v;
			}
			if (u_root == null){
				u_root = u;
			}
			parents.put(u_root, v_root);

		}
	}
}
