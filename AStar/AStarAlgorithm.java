import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 * @author jaeminbaek, Abhishek Deo
 * 
 */
public class AStarAlgorithm {
	private List<AStarNode> closedList;
	private PriorityQueue<AStarNode> openList;
	private Graph map;
	private HValue hValue;
	private Path path;

	AStarAlgorithm(Graph map, HValue hValue) {
		this.map = map;
		this.hValue = hValue;
		closedList = new ArrayList<AStarNode>();
		AStarNodeComparator comp = new AStarNodeComparator();
		openList = new PriorityQueue<AStarNode>(20, comp);
	}

	public Path astarAlgorithm(int startX, int startY, int goalX, int goalY) {
		map.setStartLocation();
		map.setGoalLocation(goalX, goalY);

		if (map.getNode(goalX, goalY).isObstacle) {
			map.getNode(goalX, goalY).setObstical(false);
		}

		// intialize
		map.getStartNode().gValue(0);
		closedList.clear();
		openList.clear();
		openList.add(map.getStartNode());

		// run until there is nothin to poll.
		while (openList.size() != 0) {
			AStarNode current = openList.poll();
			if (current.getX() == map.getGoalLocationX()
					&& current.getY() == map.getGoalLocationY()) {
				return connectThePaths(current);
			}
			openList.remove(current);
			closedList.add(current);

			for (AStarNode neighbor : current.getNeighborList()) {
				boolean found;
				if (closedList.contains(neighbor))
					continue;

				if (!neighbor.isObstacle) {
					float neighborDistance = (current.getGValue() + map
							.getGValue(current, neighbor));
					if (!openList.contains(neighbor)) {
						openList.add(neighbor);
						found = true;
					} else if (neighborDistance < current.getGValue()) {
						found = true;
					} else {
						found = false;
					}

					if (found) {
						neighbor.setPreviousNode(current);
						neighbor.gValue(neighborDistance);
						neighbor.setHeuristicDistanceFromGoal(hValue.distance(
								neighbor.getX(), neighbor.getY(),
								map.getGoalLocationX(), map.getGoalLocationY()));
					}
				}

			}

		}
		return null; // there is not path.
	}

	/**
	 * Print the Paths
	 */
	public void printNodes() {
		AStarNode node;
		for (int x = 0; x < map.getMapWidth(); x++) {
			if (x == 0) {
				for (int i = 0; i <= map.getMapWidth(); i++)
					System.out.print("-");
				System.out.println();
			}
			System.out.print("|");
			for (int y = 0; y < map.getMapHeight(); y++) {
				node = map.getNode(x, y);
				if (node.isObstacle && !node.isStart) {
					System.out.print("X");
				} else if (node.isStart) {
					System.out.print("s");
				} else if (node.isGoal) {
					System.out.print("g");
				} else if (path.contains(node.getX(), node.getY())) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
				if (y == map.getMapHeight())
					System.out.print("_");
			}

			System.out.print("|");
			System.out.println();
		}
		for (int i = 0; i <= map.getMapWidth(); i++)
			System.out.print("-");
	}

	/**
	 * Connect the paths
	 * 
	 * @param node
	 *            the node
	 * @return the path from the node
	 */
	private Path connectThePaths(AStarNode node) {
		Path path = new Path();
		while (!(node.getPreviousNode() == null)) {
			path.addFromFirst(node);
			node = node.getPreviousNode();
		}
		this.path = path;
		return path;
	}
}