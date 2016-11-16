/**
 * Jae Min Baek
 * CS 1332
 * HW6
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Sorted List
 * 
 * @author jaeminbaek
 * 
 */
public class SortedList implements Container {
	private List<WorkOrder> myList = new ArrayList<WorkOrder>(10);
	private Comparator<WorkOrder> comp = null;
	private int entries = 0;

	/**
	 * Arrange using Quick Sort or Insertion Sort
	 */
	public void arrange() {
		quickSort(0, entries - 1);
	}

	/**
	 * Quick Sort or Insertion Sort
	 * 
	 * @param start
	 *            the index of the start
	 * @param end
	 *            the index of the end
	 */
	private void quickSort(int start, int end) {
		if (start < end) {
			if (end - start >= 10) {
				// quick sort
				int pi = partition(start, end);
				quickSort(start, pi - 1); // left
				quickSort(pi + 1, end); // right
			} else {
				// insertion sort
				insertionSort(start, end);
			}
		}
	}

	/**
	 * Insertion Sort
	 * 
	 * @param start
	 *            the index of the start
	 * @param end
	 *            the index of the start
	 */
	private void insertionSort(int start, int end) {
		// 6, 7, 8, 9
		// size=end-start+1
		// 6, 5, 3, 1
		//
		// 5, 6, 3, 1
		for (int i = start + 1; i < end + 1; i++) {
			WorkOrder insertionWO = myList.get(i);
			int j = i;
			while (j > start
					&& comp.compare(myList.get(j - 1), insertionWO) > 0) {
				myList.set(j, myList.get(j - 1));
				j--;
			}
			myList.set(j, insertionWO);

		}

	}

	/**
	 * Dividing the partitions based on the start and end
	 * 
	 * @param start
	 *            the index of the start
	 * @param end
	 *            the index of the end
	 * @return the partition index
	 */
	private int partition(int start, int end) {
		WorkOrder pivot = myList.get(end);
		int pi = start; // partition index;
		for (int i = start; i < end; i++) {
			if (comp.compare(myList.get(i), pivot) <= 0) {
				swap(i, pi);
				pi++;
			}
		}
		swap(pi, end);
		return pi;
	}

	/**
	 * SWAP the two elements from the list corresponding to the a, b index
	 * 
	 * @param a
	 *            the index of the workOrder
	 * @param b
	 *            the index of the workOrder
	 */
	private void swap(int a, int b) {
		WorkOrder temp = myList.get(a);
		myList.set(a, myList.get(b));
		myList.set(b, temp);
	}

	/**
	 * Gets a workorder (removes it also) from the container
	 */
	public WorkOrder getNext() {
		if (isEmpty()) {
			return null;
		}
		entries--;
		return myList.remove(0);
	}

	/**
	 * Add a workorder to the container
	 */
	public void add(WorkOrder workOrder) {
		myList.add(workOrder);
		entries++;
	}

	/**
	 * Overriding
	 */
	@Override
	public void setComparator(Comparator comp) {
		this.comp = comp;
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean isEmpty() {
		return entries == 0;
	}

}
