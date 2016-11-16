/**
 * Jae Min Baek
 * CS 1332
 * HW6
 */
import java.util.Comparator;

/**
 * Priority Comparator
 * 
 * @author jaeminbaek
 * 
 */
public class PriorityComparator implements Comparator {

	/**
	 * Overriding
	 */
	@Override
	public int compare(Object o1, Object o2) {
		WorkOrder o1c = (WorkOrder) o1;
		WorkOrder o2c = (WorkOrder) o2;

		if (o1c.getPriority() > o2c.getPriority()) {
			return 1;
		} else if (o1c.getPriority() == o2c.getPriority()) {
			return 0;
		} else {
			return -1;
		}
	}

}
