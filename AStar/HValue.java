/**
 * 
 * @author jaeminbaek, Abhishek Deo
 * 
 */
public class HValue {

	/**
	 * The Distance
	 * 
	 * @param startX
	 *            start location index
	 * @param startY
	 *            start location index
	 * @param goalX
	 *            goal location index
	 * @param goalY
	 *            goal location index
	 * @return the distance. (H Value)
	 */
	public float distance(int startX, int startY, int goalX, int goalY) {
		float dx = goalX - startX;
		float dy = goalY - startY;

		float result = (float) (dx * dx) + (dy * dy);
		return result;
	}

}