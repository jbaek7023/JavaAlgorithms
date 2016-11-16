/**
 * CS 1332 HW4
 * Jun 18
 * AVLTree.java
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * AVLTree class
 * 
 * @author jaemin baek
 * 
 * @param <E>
 *            the generic type
 */
public class AVLTree<E extends Comparable> implements BinaryTree<E> {
	private AVLNode<E> root = null;
	private int entries = 0;

	/**
	 * Overriding
	 */
	@Override
	public boolean add(E item) {
		if (item == null) {
			return false;
		} else if (contains(item)) {
			return false;
		} else {
			root = add(item, root);
			entries++;
			return true;
		}
	}

	/**
	 * add method.
	 * 
	 * @param item
	 *            the item
	 * @param entryNode
	 *            the entry node
	 * @return
	 */
	private AVLNode<E> add(E item, AVLNode<E> entryNode) {
		if (entryNode == null) { // base case
			entryNode = new AVLNode<E>(item);
		} else {
			if (entryNode.data.compareTo(item) < 0) {
				entryNode.right = add(item, entryNode.right); // recursive call

			} else if (entryNode.data.compareTo(item) > 0) {
				entryNode.left = add(item, entryNode.left); // recursive call
			} else {
				// do nothing if it's duplicate
				return entryNode;
			}
			// else , reset height, return the new root.
		}
		return balance(entryNode);
	}

	/**
	 * balance the nodes,recursively
	 * 
	 * @param entryNode
	 *            entry node
	 * @return the balanced node
	 */
	private AVLNode<E> balance(AVLNode<E> entryNode) {
		if (entryNode == null) {
			return null;
		}
		AVLNode<E> unbalanced = update(entryNode);
		int factor = unbalanced.factor;
		// if factor is not 1, 0, 1, others should be factored.
		if (factor > 1) {
			AVLNode<E> leftNode = entryNode.left;
			AVLNode<E> left = update(leftNode);
			if (left == null)
				return entryNode;
			int leftFactor = left.factor;
			if (leftFactor < 0) {
				entryNode = lrRotate(entryNode);
			} else if (leftFactor > 0) {
				entryNode = rRotate(entryNode);
			}
		} else if (factor < -1) {
			AVLNode<E> rightNode = entryNode.right;
			AVLNode<E> right = update(rightNode);
			if (right == null)
				return entryNode;
			int rightFactor = right.factor;
			if (rightFactor > 0) {
				entryNode = rlRotate(entryNode);
			} else if (rightFactor < 0) {
				entryNode = lRotate(entryNode);
			}
		}
		return update(entryNode);
	}

	/**
	 * Left Rotate
	 * 
	 * @param entryNode
	 *            the entry node
	 * @return rotated node
	 */
	private AVLNode<E> lRotate(AVLNode<E> entryNode) {
		AVLNode<E> right = entryNode.right;
		entryNode.right = right.left;
		right.left = entryNode;
		update(entryNode);
		update(right);
		return right;
	}

	/**
	 * Right Left Rotate
	 * 
	 * @param entryNode
	 *            the entry node
	 * @return the rotated node
	 */
	private AVLNode<E> rlRotate(AVLNode<E> entryNode) {
		entryNode.right = rRotate(entryNode.right);
		return lRotate(entryNode);
	}

	/**
	 * Right Rotate
	 * 
	 * @param entryNode
	 *            the entry node
	 * @return the rotated node
	 */
	private AVLNode<E> rRotate(AVLNode<E> entryNode) {
		AVLNode<E> left = entryNode.left;
		entryNode.left = left.right;
		left.right = (entryNode);
		update(entryNode);
		update(left);
		return left;
	}

	/**
	 * Left Right Rotate
	 * 
	 * @param entryNode
	 *            the entry node
	 * @return the roated node
	 */
	private AVLNode<E> lrRotate(AVLNode<E> entryNode) {
		entryNode.left = lRotate(entryNode.left);
		return rRotate(entryNode);
	}

	/**
	 * Update the properties everytime it is called
	 * 
	 * @param entryNode
	 *            the entry node
	 * @return updated node with factor
	 */
	private AVLNode<E> update(AVLNode<E> entryNode) {
		int leftHeight = 0, rightHeight = 0;
		if (entryNode.left != null) {
			leftHeight = entryNode.left.height;
		}
		if (entryNode.right != null) {
			rightHeight = entryNode.right.height;
		}
		entryNode.height = Math.max(leftHeight, rightHeight) + 1;
		entryNode.factor = leftHeight - rightHeight;
		return entryNode;
	}

	/**
	 * Overriding
	 */
	@Override
	public E max() {
		if (isEmpty()) {
			return null;
		} else {
			AVLNode<E> maxNode = root;
			while (maxNode.right != null) {
				maxNode = maxNode.right;
			}
			return maxNode.data;
		}
	}

	/**
	 * Overriding
	 */
	@Override
	public int size() {
		return entries;
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean isEmpty() {
		return entries == 0;
	}

	/**
	 * Overriding
	 */
	@Override
	public E min() {
		AVLNode<E> temp = root;
		if (temp == null)
			return null;
		while (temp.left != null)
			temp = temp.left;
		return temp.data;
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean contains(E item) {
		if (item == null) {
			throw new IllegalArgumentException();
		} else {
			return contains(root, item);
		}
	}

	/**
	 * check if it contains, the item, recursively
	 * 
	 * @param root
	 *            the root
	 * @param item
	 *            the item that we are looking for
	 * @return true if we found it
	 */
	private boolean contains(AVLNode<E> root, E item) {
		if (root == null) {
			return false;
		} else {
			int check = item.compareTo(root.data);
			if (check == 0) {
				return true;
			} else if (root.data == null || check < 0) {
				return contains(root.left, item);
			} else {
				return contains(root.right, item);
			}
		}
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean remove(E item) {
		if (item == null)
			return false;
		if (contains(item)) {
			if (entries == 1) {
				root = null;
			} else {
				root = remove(root, item);
			}
			entries--;
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Remove recursively
	 * 
	 * @param root
	 *            the root
	 * @param item
	 *            the item that we are looking for
	 * @return removed node from the tree
	 */
	private AVLNode<E> remove(AVLNode<E> root, E item) {
		if (root == null)
			return null;
		int check = item.compareTo(root.data);
		AVLNode<E> left = root.left;
		AVLNode<E> right = root.right;
		if (check == 0) {
			if (left == null && right == null)
				return null;
			else if (left == null)
				return right;
			else if (right == null)
				return left;
			else {
				AVLNode<E> temp = minNode(right);
				root.data = temp.data;
				root.right = remove(right, temp.data);
			}
		} else if (check < 0)
			root.left = remove(left, item);
		else
			root.right = remove(right, item);

		return balance(root);
	}

	/**
	 * node with minimum value
	 * 
	 * @param node
	 *            the node which will be assumed as root
	 * @return the node with minimum value
	 */
	private AVLNode<E> minNode(AVLNode<E> node) {
		if (node.left == null)
			return node;
		else
			return minNode(node.left);

	}

	/**
	 * Overriding
	 */
	@Override
	public Iterator<E> iterator() {
		return new AVLIterator<E>();
	}

	/**
	 * AVLIterator class
	 * 
	 * @author jaemin baek
	 * 
	 * @param <E>
	 *            the generic type
	 */
	private class AVLIterator<E extends Comparable> implements Iterator<E> {

		private AVLNode<E> cur;
		private Stack<AVLNode<E>> stack = new Stack<AVLNode<E>>();

		/**
		 * Overriding
		 */
		@SuppressWarnings("unchecked")
		public AVLIterator() {
			cur = (AVLNode<E>) root;
		}

		/**
		 * Overriding
		 */
		@Override
		public boolean hasNext() {
			return cur != null || !stack.isEmpty();
		}

		/**
		 * Overrding
		 */
		@Override
		public E next() {
			E item = null;
			while (cur != null) {
				stack.push(cur);
				cur = cur.left;
			}
			if (!stack.isEmpty()) {
				cur = stack.pop();
				item = cur.data;
				cur = cur.right;
			}
			return item;
		}

		/**
		 * Overriding
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Overriding
	 */
	@Override
	public List<E> getPostOrder() {
		ArrayList<E> list = new ArrayList<E>();
		postOrderList(root, list);
		return list;
	}

	/**
	 * Post Order List
	 * 
	 * @param root
	 *            the root
	 * @param list
	 *            the post order list
	 */
	private void postOrderList(AVLNode<E> root, ArrayList<E> list) {
		if (root == null) {
			return;
		} else {
			postOrderList(root.left, list);
			postOrderList(root.right, list);
			list.add(root.data);
		}

	}

	/**
	 * Overriding
	 */
	@Override
	public List<E> getLevelOrder() {
		AVLNode<E> newNode = root;
		Queue<AVLNode<E>> queue = new LinkedList<AVLNode<E>>();
		List<E> list = new ArrayList<E>();
		if (newNode != null){
			queue.add(newNode);
		}
		
		while (!queue.isEmpty()) {
			AVLNode<E> current = queue.remove();
			if (current.left != null) {
				queue.add(current.left);
			}
			if (current.right != null) {
				queue.add(current.right);
			}
			list.add(current.data);
		}
		return list;
	}

	/**
	 * Overriding
	 */
	@Override
	public List<E> getPreOrder() {
		ArrayList<E> list = new ArrayList<E>();
		preOrderList(root, list);
		return list;
	}

	/**
	 * Pre Order List
	 * 
	 * @param root
	 *            the root
	 * @param list
	 *            the pre order list
	 */
	private void preOrderList(AVLNode<E> root, ArrayList<E> list) {
		if (root == null) {
			return;
		} else {
			list.add(root.data);
			preOrderList(root.left, list);
			preOrderList(root.right, list);
		}
	}

	/**
	 * Overriding
	 */
	@Override
	public void clear() {
		root = null;
		entries = 0;
	}

	/**
	 * AVLNode class
	 * 
	 * @author jaeminbaek
	 * 
	 * @param <E>
	 *            the generic
	 */
	private class AVLNode<E extends Comparable> {
		private AVLNode<E> left = null;
		private AVLNode<E> right = null;
		private E data = null;
		private int height = 0;
		private int factor;

		public AVLNode(E data) {
			this.data = data;
		}
	}
}
