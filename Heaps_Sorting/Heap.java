/**
 * Jae Min Baek
 * CS 1332
 * HW6
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Longest Job
 * 
 * @author jaeminbaek
 * 
 */
public class Heap implements Container {
	private Comparator<WorkOrder> comp = null;
	private int entries = 0;
	private List<WorkOrder> myList = new ArrayList<WorkOrder>(10);

	/**
	 * Add a workorder to the container
	 */
	public void add(WorkOrder workOrder) {
		if (isEmpty()) {
			myList.add(workOrder);
			entries++;
		} else {
			myList.add(workOrder);
			entries++;
			int index = entries - 1;
			balanceUp(index);
		}
	}

	/**
	 * Balncing from the down to up
	 * 
	 * @param index
	 *            the index of the children
	 */
	private void balanceUp(int index) {
		if (index != 0) { // if we are balancing the root, just stop
			// if it's unstable,
			if (comp.compare(myList.get(index), myList.get(parentIndex(index))) > 0) {
				swap(index, parentIndex(index));
				balanceUp(parentIndex(index));
			}
		}
	}

	/**
	 * Get the parent index
	 * 
	 * @param index
	 *            the index of the child
	 * @return the index of the parent
	 */
	private int parentIndex(int index) {
		return (index % 2 == 1) ? index / 2 : index / 2 - 1;
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
		} else if (entries == 1) {
			entries--;
			return myList.remove(0);
		}
		WorkOrder maxHeap = myList.get(0);
		WorkOrder lastWO = myList.get(myList.size() - 1);

		// move the recent node to the top
		myList.remove(0);
		entries--;
		myList.add(0, lastWO);
		myList.remove(myList.size() - 1);

		balanceDown(0);
		return maxHeap;
	}

	/**
	 * Balnce from up to down
	 * 
	 * @param parent
	 *            the index of the parent
	 */
	private void balanceDown(int parent) {
		if (parent < myList.size()) {
			// if child is bigger than No
			// find the biggest, and swap

			// cut out the invalid, index first.
			int lci = 0, rci = 0;
			if (leftChildIndex(parent) >= myList.size())
				lci = -1;
			else
				lci = leftChildIndex(parent);

			if (leftChildIndex(parent) + 1 >= myList.size())
				rci = -1;
			else
				rci = leftChildIndex(parent) + 1;

			// determine whether the variables are correct or not.
			// leftC and the parent
			int lcAp = (lci == -1) ? -1 : comp.compare(myList.get(lci),
					myList.get(parent));

			int rcAp = (rci == -1) ? -1 : comp.compare(myList.get(rci),
					myList.get(parent));

			int lcRc = 0;
			if (rci == -1 && lci == -1)
				lcRc = -100;
			else if (rci == -1 && lci != -1)
				// right is invalid so left is bigger
				lcRc = -1;
			else if (lci == -1 && rci != -1)
				lcRc = 1;
			else
				lcRc = comp.compare(myList.get(lci), myList.get(rci));

			// leftC and the rightC
			if (lcRc == -100) {
				// base case
			} else if (lcRc <= 0) {
				// right is bigger.
				if (rcAp > 0) {
					swap(parent, leftChildIndex(parent) + 1);
					balanceDown(leftChildIndex(parent) + 1);
				}

			} else {
				// left is bigger
				if (lcAp > 0) {
					swap(parent, leftChildIndex(parent));
					balanceDown(leftChildIndex(parent));
				}

			}
		}
	}

	/**
	 * Get the left child index
	 * 
	 * @param index
	 *            the index of the parent
	 * @return the index of the child
	 */
	private int leftChildIndex(int index) {
		return index * 2 + 1;
		// 0->1, 2
		// 1->3, 4
		// 2->5, 6
		// 3->7, 8
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
	public void arrange() {
		// we don't need this
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean isEmpty() {
		return entries == 0;
	}

}
