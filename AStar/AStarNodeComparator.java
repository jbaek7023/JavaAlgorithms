import java.util.Comparator;

/**
 * AStarNode Comparator
 * 
 * @author jaeminbaek, Abhishek Deo
 * 
 */
public class AStarNodeComparator implements Comparator<AStarNode> {

	/**
	 * Overriding
	 */
	@Override
	public int compare(AStarNode first, AStarNode second) {
		float fir = first.getHValue() + first.getGValue();
		float sec = second.getHValue() + second.getGValue();
		if (fir < sec) {
			return -1;
		} else if (fir > sec) {
			return 1;
		} else {
			return 0;
		}
	}
}
