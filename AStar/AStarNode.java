import java.util.ArrayList;

/**
 * Data Structure Node.
 * 
 * @author jaeminbaek, Abhishek Deo
 * 
 */
public class AStarNode {
	Graph map;
	AStarNode n, ne, e, se, s, sw, w, nw; // nine directions of the nodes
	ArrayList<AStarNode> neighborList;
	boolean visited;
	float gValue;
	float hValueNum;

	AStarNode cameFromNode;
	int x, y;
	boolean isObstacle, isStart, isGoal;

	AStarNode(int x, int y) {
		neighborList = new ArrayList<AStarNode>();
		this.x = x;
		this.y = y;
		this.visited = false;
		this.gValue = Integer.MAX_VALUE;
		this.isObstacle = false;
		this.isStart = false;
		this.isGoal = false;
	}

	AStarNode(int x, int y, boolean visited, int distanceFromStart,
			boolean isObstical, boolean isStart, boolean isGoal) {
		neighborList = new ArrayList<AStarNode>();
		this.x = x;
		this.y = y;
		this.visited = visited;
		this.gValue = distanceFromStart;
		this.isObstacle = isObstical;
		this.isStart = isStart;
		this.isGoal = isGoal;
	}

	public AStarNode getNorth() {
		return n;
	}

	public AStarNode getNorthEast() {
		return ne;
	}

	public AStarNode getEast() {
		return e;
	}

	public AStarNode getSouthEast() {
		return se;
	}

	public AStarNode getSouth() {
		return s;
	}

	public AStarNode getSouthWest() {
		return sw;
	}

	public AStarNode getWest() {
		return w;
	}

	public AStarNode getNorthWest() {
		return nw;
	}

	public void setNorth(AStarNode n) {
		if (neighborList.contains(this.n))
			neighborList.remove(this.n);
		neighborList.add(n);
		this.n = n;
	}

	public void setEast(AStarNode e) {
		if (neighborList.contains(this.e))
			neighborList.remove(this.e);
		neighborList.add(e);

		this.e = e;
	}

	public void setSouthEast(AStarNode se) {
		if (neighborList.contains(this.se))
			neighborList.remove(this.se);
		neighborList.add(se);

		this.se = se;
	}

	public void setSouth(AStarNode s) {
		if (neighborList.contains(this.s))
			neighborList.remove(this.s);
		neighborList.add(s);

		this.s = s;
	}

	public void setSouthWest(AStarNode sw) {
		if (neighborList.contains(this.sw))
			neighborList.remove(this.sw);
		neighborList.add(sw);

		this.sw = sw;
	}

	public void setWest(AStarNode w) {
		if (neighborList.contains(this.w))
			neighborList.remove(this.w);
		neighborList.add(w);

		this.w = w;
	}

	public void setNorthWest(AStarNode nw) {
		if (neighborList.contains(this.nw))
			neighborList.remove(this.nw);
		neighborList.add(nw);

		this.nw = nw;
	}

	public void setNorthEast(AStarNode ne) {
		if (neighborList.contains(this.ne))
			neighborList.remove(this.ne);
		neighborList.add(ne);

		this.ne = ne;
	}

	public ArrayList<AStarNode> getNeighborList() {
		return neighborList;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public float getGValue() {
		return gValue;
	}

	public void gValue(float gValue) {
		this.gValue = gValue;
	}

	public AStarNode getPreviousNode() {
		return cameFromNode;
	}

	public void setPreviousNode(AStarNode previousNode) {
		this.cameFromNode = previousNode;
	}

	public float getHValue() {
		return hValueNum;
	}

	public void setHeuristicDistanceFromGoal(float hValue) {
		this.hValueNum = hValue;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isObstical() {
		return isObstacle;
	}

	public void setObstical(boolean isObstical) {
		this.isObstacle = isObstical;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isGoal() {
		return isGoal;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public boolean equals(AStarNode node) {
		return (node.x == x) && (node.y == y);
	}
}
