import java.util.ArrayList;
import java.util.Random;

/**
 * Graph
 * 
 * @author jaeminbaek, Abhishek Deo
 * 
 */
public class Graph {
	private int columnNum;
	private int rowNum;
	public int totalNum;
	private ArrayList<ArrayList<AStarNode>> map;
	private int startX = 0;
	private int startY = 0;
	private int goalX = 0;
	private int goalY = 0;
	private int[][] importingMap;

	/**
	 * Graph Constructor
	 * 
	 * @param mapWidth
	 *            width
	 * @param mapHeight
	 *            height
	 * @param obstacleMap
	 *            map
	 */
	Graph(int mapWidth, int mapHeight, int[][] importingMap) {
		this.columnNum = mapWidth;
		this.rowNum = mapHeight;
		this.importingMap = importingMap;
		createMap();
		connectEdges();
		totalNum = columnNum + rowNum;
	}

	/**
	 * Create Map
	 */
	private void createMap() {
		AStarNode node;
		map = new ArrayList<ArrayList<AStarNode>>();
		for (int x = 0; x < columnNum; x++) {
			map.add(new ArrayList<AStarNode>());
			for (int y = 0; y < rowNum; y++) {
				node = new AStarNode(x, y);
				if (importingMap[x][y] == 1)
					node.setObstical(true);
				map.get(x).add(node);
			}
		}
	}

	/**
	 * Get Random Map
	 * 
	 * @param numOfRocks
	 *            the number of rocks
	 */
	public void getRandomMap(int numOfRocks) {
		Random random = new Random();
		for (int x = 0; x < columnNum; x++) {
			for (int y = 0; y < rowNum; y++) {
				importingMap[x][y] = 0;
			}
		}
		for (int i = 0; i < numOfRocks; i++) {
			int randomColumn = random.nextInt(columnNum);
			int randomRow = random.nextInt(rowNum);
			importingMap[randomColumn][randomRow] = 1;
		}
		createMap();
		connectEdges();
	}

	/**
	 * get Nodes
	 * 
	 * @return the list of Nodes
	 */
	public ArrayList<ArrayList<AStarNode>> getNodes() {
		return map;
	}

	/**
	 * Set Obstacles
	 * 
	 * @param x
	 *            the column
	 * @param y
	 *            the row
	 * @param isObstical
	 *            obstacle or not
	 */
	public void setObstical(int x, int y, boolean isObstical) {
		map.get(x).get(y).setObstical(isObstical);
	}

	/**
	 * Get Node
	 * 
	 * @param x
	 *            the column
	 * @param y
	 *            the row
	 * @return the node at that location
	 */
	public AStarNode getNode(int x, int y) {
		return map.get(x).get(y);
	}

	/**
	 * Connect the Edges
	 */
	private void connectEdges() {
		for (int x = 0; x < columnNum - 1; x++) {
			for (int y = 0; y < rowNum - 1; y++) {
				AStarNode node = map.get(x).get(y);
				if (!(y == 0))
					node.setNorth(map.get(x).get(y - 1));
				if (!(y == 0) && !(x == columnNum))
					node.setNorthEast(map.get(x + 1).get(y - 1));
				if (!(x == columnNum))
					node.setEast(map.get(x + 1).get(y));
				if (!(x == columnNum) && !(y == rowNum))
					node.setSouthEast(map.get(x + 1).get(y + 1));
				if (!(y == rowNum))
					node.setSouth(map.get(x).get(y + 1));
				if (!(x == 0) && !(y == rowNum))
					node.setSouthWest(map.get(x - 1).get(y + 1));
				if (!(x == 0))
					node.setWest(map.get(x - 1).get(y));
				if (!(x == 0) && !(y == 0))
					node.setNorthWest(map.get(x - 1).get(y - 1));
			}
		}
	}

	/**
	 * Set the Start location
	 */
	public void setStartLocation() {
		startX = 0;
		startY = 0;
	}

	/**
	 * Set Goal Location
	 * 
	 * @param x
	 *            column
	 * @param y
	 *            row
	 */
	public void setGoalLocation(int x, int y) {
		map.get(goalX).get(goalY).setGoal(false);
		map.get(x).get(y).setGoal(true);
		goalX = x;
		goalY = y;
	}

	/**
	 * Get Start Node
	 * 
	 * @return the node
	 */
	public AStarNode getStartNode() {
		return map.get(startX).get(startY);
	}

	/**
	 * Get the row
	 * 
	 * @return the node
	 */
	public int getGoalLocationX() {
		return goalX;
	}

	/**
	 * Get the column
	 * 
	 * @return the node
	 */
	public int getGoalLocationY() {
		return goalY;
	}

	/**
	 * Get the GValue
	 * 
	 * @param node1
	 *            the node
	 * @param node2
	 *            the node
	 * @return
	 */
	public float getGValue(AStarNode node1, AStarNode node2) {
		if (node1.getX() == node2.getX() || node1.getY() == node2.getY()) {
			return (float) 1 * (rowNum + columnNum);
		} else {
			return (float) 1.4 * (rowNum + columnNum);
		}
	}

	/**
	 * Get the width
	 * 
	 * @return the index
	 */
	public int getMapWidth() {
		return columnNum;
	}

	/**
	 * Get Height
	 * 
	 * @return the index
	 */
	public int getMapHeight() {
		return rowNum;
	}
}
