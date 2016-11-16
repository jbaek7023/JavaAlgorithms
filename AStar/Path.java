import java.util.ArrayList;
import java.util.List;

/**
 * The Path
 * 
 * @author jaeminbaek, Abhishek Deo
 * 
 */
public class Path {
	private List<AStarNode> markedNodes = new ArrayList<AStarNode>();

	/**
	 * Default Constructor
	 */
	public Path() {
	}

	/**
	 * Get Path
	 * 
	 * @param index
	 * @return
	 */
	public AStarNode getPathNodes(int index) {
		return markedNodes.get(index);
	}

	/**
	 * Add from the first
	 * 
	 * @param n
	 *            the node
	 */
	public void addFromFirst(AStarNode n) {
		markedNodes.add(0, n);
	}

	/**
	 * Contains or not
	 * 
	 * @param x
	 *            the index
	 * @param y
	 *            the index
	 * @return contains or not
	 */
	public boolean contains(int x, int y) {
		for (AStarNode node : markedNodes) {
			if (node.getX() == x && node.getY() == y)
				return true;
		}
		return false;
	}

}
