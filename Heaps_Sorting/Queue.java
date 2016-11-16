/**
 * Jae Min Baek
 * CS 1332
 * HW6
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Queue implements Container {
	private List<WorkOrder> myList = new ArrayList<WorkOrder>();
	private int entries = 0;

	/**
	 * Gets a workorder (removes it also) from the container
	 */
	public WorkOrder getNext() {
		if (isEmpty()) {
			return null;
		} else {

			WorkOrder wo = myList.get(0);
			myList.remove(0);
			entries--;
			return wo;
		}
	}

	/**
	 * Add a workorder to the container
	 */
	public void add(WorkOrder workOrder) {
		myList.add(0, workOrder);
		entries++;
	}

	/**
	 * Overriding
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setComparator(Comparator comp) {
		// Not REQUIRED
	}

	/**
	 * Overriding
	 */
	@Override
	public void arrange() {
		// may not be required
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean isEmpty() {
		return entries == 0;
	}

}
