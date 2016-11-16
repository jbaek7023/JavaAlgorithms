import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/*
 * LazyDeleteLinkedList.java
 *
 * Version 1.0
 * Georgia Tech CS 1332 HW3
 */

/**
 * Lazy Deletion Linked List
 * 
 * @author Jae Min Baek
 * 
 * @param <T>
 *            The generic type
 */
public class LazyDeleteLinkedList<T> implements LazyDeleteList<T> {

	Node<T> head;
	Node<T> tail;
	int entries;
	Stack<Node<T>> stack;

	/**
	 * Default constructor
	 */
	public LazyDeleteLinkedList() {
		head = null;
		tail = null;
		entries = 0;
		stack = new Stack<Node<T>>();

	}

	/**
	 * Overriding isEmpty() method from LazyDeleteList Interface.
	 */
	@Override
	public boolean isEmpty() {
		return entries == 0;
	}

	/**
	 * Overriding size() method from LazyDeleteList Interface.
	 */
	@Override
	public int size() {
		return entries;
	}

	/**
	 * Overriding add(T data) method from LazyDeleteList Interface.
	 */
	@Override
	public void add(T data) {
		if (data == null) {

		} else if (stack.isEmpty()) {
			if (isEmpty()) {
				head = new Node<T>();
				head.setData(data);
				head.setNext(null);
				head.setPrev(null);
				head.setDeleted(false);
				tail = head;
				entries++;
			} else {
				// 1
				Node<T> newNode = new Node<T>();
				newNode.setData(data);
				newNode.setPrev(tail);
				newNode.setNext(null);
				newNode.setDeleted(false);
				// 1<-2->null
				tail.setNext(newNode);
				tail = newNode;
				entries++;
			}
		} else {
			Node<T> deletedFrontNode = stack.pop();  //the most front node
			deletedFrontNode.setData(data);
			deletedFrontNode.setDeleted(false);
			entries++;
		}
	}

	/**
	 * Overriding compress() method from LazyDeleteList Interface.
	 */
	@Override
	public int compress() {
		Node<T> popedNode = null;
		int output = 0;
		int stackSize = stack.size(); // It will be changed if it's going to be
										// in the for-loop
		for (int i = 0; i < stackSize; i++) {
			popedNode = stack.pop();
			if (popedNode.equals(head)) {
				head = popedNode.getNext();
				popedNode = null;
			} else if (popedNode.equals(tail)) {
				tail = popedNode.getPrev();
				popedNode = null;
			} else {
				popedNode.getPrev().setNext(popedNode.getNext());
				popedNode.getNext().setPrev(popedNode.getPrev());
				popedNode = null;
			}
			output++;
		}
		return output;
	}

	/**
	 * Overriding clear() method from LazyDeleteList Interface.
	 */
	@Override
	public void clear() {
		head = null;
		tail = null;
		entries = 0;
		stack = new Stack<Node<T>>();
	}

	/**
	 * Overriding contains() method from LazyDeleteList Interface.
	 */
	@Override
	public boolean contains(T data) {
		Node<T> cur = head;
		if (data == null) {

		} else {
			for (int i = 0; i < entries + deletedNodeCount(); i++) {
				if (cur.getData().equals(data) && !cur.isDeleted()) {
					return true;
				}
				if (i != entries + deletedNodeCount()) {
					cur = cur.getNext();
				}
			}
		}
		return false;
	}

	/**
	 * Get the index of the matched data. We assume that the list contains data
	 * and the data is not null.
	 * 
	 * @param data
	 *            the data we are looking for
	 * @return the index the data is located
	 */
	private int getIndexOfMatchedData(T data) {
		int index = 0;
		Node<T> cur = head;
		for (int i = 0; i < entries + deletedNodeCount(); i++) {
			if (cur.getData().equals(data) && !cur.isDeleted()) {
					return index;
				}
				index++;
				cur = cur.getNext();
			}
		return index;
	}

	/**
	 * Overriding remove() method from LazyDeleteList Interface.
	 */
	@Override
	public boolean remove(T data) {
		if (data == null | isEmpty() | !contains(data)) {
			return false;
		} else if (contains(data)) {
			int indexOfData = getIndexOfMatchedData(data);
			Node<T> cur = head;
			for (int i = 0; i < indexOfData; i++) {
				cur = cur.getNext();
			}

			// cur is the the node that we want to delete
			cur.setDeleted(true);
			stack.push(cur);
			entries--;
		}
		return true;
	}

	/**
	 * Overriding deletedNodeCount() method from LazyDeleteList Interface.
	 */
	@Override
	public int deletedNodeCount() {
		return stack.size();
	}

	/**
	 * Overriding iterator() method from LazyDeleteList Interface.
	 */
	@Override
	public Iterator<T> iterator() {
		return new LazyListIterator<T>();
	}

	/**
	 * LazyListIterator class implemtns Iterator.
	 * 
	 * @author Jae Min Baek
	 * 
	 * @param <T>
	 *            The generic type
	 */
	@SuppressWarnings("hiding")
	private class LazyListIterator<T> implements Iterator<T> {
		Node<T> cur; // weird thing

		/**
		 * The default constructor
		 */
		@SuppressWarnings("unchecked")
		public LazyListIterator() {
			compress(); // compress the list so there are no deleted nodes.
			cur = (Node<T>) head;
		}

		/**
		 * Detects whether this iterator has completed its traversal and gone
		 * beyond the last entry in the collection of data.
		 * 
		 * @return true if the iterator has another entry to return
		 */
		public boolean hasNext() {
			return cur != null;
		}

		/**
		 * Retrieves the next entry in the collection and advances this iterator
		 * by one position
		 * 
		 * @return a reference to the next entry in the iteration, if one exists
		 * @throws NoSuchElementException
		 *             if hasNext is false.
		 */
		public T next() {
			if (hasNext()) {
				Node<T> returnNode = cur;
				cur = cur.getNext();
				return returnNode.getData();
			} else {
				throw new NoSuchElementException();
			}

		}

		/**
		 * We don't support this operation
		 */
		public void remove() {
			throw new UnsupportedOperationException(
					"Invalid operation for the list");
		}
	}

	/**
	 * Node class
	 * 
	 * @author Jae Min Baek
	 * 
	 * @param <T>
	 *            The generic type
	 */
	@SuppressWarnings("hiding")
	private class Node<T> {
		private Node<T> next;
		private Node<T> prev;
		private T data;
		private boolean deleted;

		/**
		 * Default Node class.
		 */
		public Node() {
			deleted = false;
		}

		/**
		 * Get Next
		 * 
		 * @return The next node
		 */
		public Node<T> getNext() {
			return next;
		}

		/**
		 * Set the next node
		 * 
		 * @param next
		 *            the node that we are setting to
		 */
		public void setNext(Node<T> next) {
			this.next = next;
		}

		/**
		 * Get the previous node
		 * 
		 * @return the previous node
		 */
		public Node<T> getPrev() {
			return prev;
		}

		/**
		 * Set the Previous node
		 * 
		 * @param prev
		 *            the node that we are setting to
		 */
		public void setPrev(Node<T> prev) {
			this.prev = prev;
		}

		/**
		 * Get the data
		 * 
		 * @return the data of this node
		 */
		public T getData() {
			return data;
		}

		/**
		 * Set the data
		 * 
		 * @param data
		 *            the data we are setting to
		 */
		public void setData(T data) {
			this.data = data;
		}

		/**
		 * Check if it's deleted
		 * 
		 * @return true if it's deleted
		 */
		public boolean isDeleted() {
			return deleted;
		}

		/**
		 * Set the deleted state
		 * 
		 * @param deleted
		 *            the state we are setting to
		 */
		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}
	}
}
