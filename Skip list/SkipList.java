import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Skip List class
 * 
 * @author jaeminbaek
 * 
 * @param <K>
 *            the generic type
 * @param <V>
 *            the generic type
 */
public class SkipList<K extends Integer, V> implements java.util.Map<K, V> {
	protected CoinFlipper flipper;
	protected int entries = 0;
	protected int height = 0;
	@SuppressWarnings("unchecked")
	protected K posInf = (K) new Integer(Integer.MAX_VALUE);
	@SuppressWarnings("unchecked")
	protected K negInf = (K) new Integer(-Integer.MAX_VALUE);
	protected SkipNode<K, V> headNode;
	protected SkipNode<K, V> tailNode;

	/**
	 * Skip List Constructor
	 * 
	 * @param flipper
	 */
	public SkipList(CoinFlipper flipper) {
		SkipNode<K, V> node1, node2;
		node1 = new SkipNode<K, V>(negInf, null);
		node2 = new SkipNode<K, V>(posInf, null);
		node1.setNext(node2);
		node2.setPrev(node1);
		headNode = node1;
		tailNode = node2;
		this.flipper = flipper;
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean containsKey(Object key) {
		if (key == null)
			throw new IllegalArgumentException();
		Set<K> keySet = keySet();
		return keySet.contains(key);
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean containsValue(Object value) {
		if (value == null)
			throw new IllegalArgumentException();
		Collection<V> valueSet = values();
		return valueSet.contains(value);
	}

	/**
	 * Overriding
	 */
	@Override
	public V put(K key, V newValue) {
		if (key == null || newValue == null)
			throw new IllegalArgumentException();

		SkipNode<K, V> prevNode, newNode;
		prevNode = findNode(key);
		if (key.equals(prevNode.getKey())) {
			V oldValue = prevNode.getValue();
			prevNode.setValue(newValue);
			while (prevNode.getUp() != null) {
				prevNode = prevNode.getUp();
				prevNode.setValue(newValue);
			}
			return oldValue;
		} else {
			// if there was no mapping for key,
			newNode = new SkipNode<K, V>(key, newValue);
			newNode.setPrev(prevNode);
			newNode.setNext(prevNode.getNext());
			prevNode.getNext().setPrev(newNode);
			prevNode.setNext(newNode);	
			
			int searchingHeight = 0;
			/**
			 * Coin Tossing
			 */
			while (flipper.flipCoin() == CoinFlipper.Coin.HEADS) {
				if (searchingHeight >= height) {
					addLayer();
				}
				while (prevNode.getUp() == null) {
					prevNode = prevNode.getPrev();
				}

				prevNode = prevNode.getUp();

				SkipNode<K, V> temp = new SkipNode<K, V>(key, newValue);
				temp.setPrev(prevNode);
				temp.setNext(prevNode.getNext());
				temp.setDown(newNode);

				prevNode.getNext().setPrev(temp);
				prevNode.setNext(temp);
				newNode.setUp(temp);
				newNode = temp;
				searchingHeight++;
			}
			entries++;
			return null;
		}
	}

	/**
	 * Overriding
	 */
	@Override
	public V get(Object key) {
		if (key == null)
			throw new IllegalArgumentException();
		SkipNode<K, V> currentNode;
		currentNode = findNode(key);
		if (key.equals(currentNode.getKey()))
			return currentNode.getValue();
		else
			return null;
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
	public void clear() {
		SkipNode<K, V> node1, node2;
		node1 = new SkipNode<K, V>(negInf, null);
		node2 = new SkipNode<K, V>(posInf, null);
		node1.setNext(node2);
		node2.setPrev(node1);
		headNode = node1;
		tailNode = node2;
		entries = 0;
		height = 0;
	}

	/**
	 * Overriding
	 */
	@Override
	public Set<K> keySet() {
		Set<K> keys = new TreeSet<K>();
		SkipNode<K, V> bottom = headNode;
		while (bottom.getDown() != null)
			bottom = bottom.getDown();
		while (bottom.getNext().getKey() != posInf) {
			bottom = bottom.getNext();
			keys.add(bottom.getKey());
		}
		return keys;
	}

	/**
	 * Overriding
	 */
	@Override
	public Collection<V> values() {
		Collection<V> vals = new LinkedList<V>();
		SkipNode<K, V> bottom = headNode;
		while (bottom.getDown() != null)
			bottom = bottom.getDown();
		while (bottom.getNext().getKey() != posInf) {
			bottom = bottom.getNext();
			vals.add(bottom.getValue());
		}
		return vals;
	}

	/**
	 * Overriding
	 */
	@Override
	public V remove(Object key) {
		if (key == null)
			throw new IllegalArgumentException();
		V removedValue = null;
		if (containsKey(key) == true) {
			SkipNode<K, V> removedNode = findNode(key);
			removedValue = removedNode.getValue();
			while (removedNode.getUp() != null) {
				removedNode.getPrev().setNext(removedNode.getNext());
				removedNode.getNext().setPrev(removedNode.getPrev());
				removedNode = removedNode.getUp();
			}
			if (removedNode.getPrev().getKey() == negInf
					&& removedNode.getNext().getKey() == posInf)
				height--;
			removedNode.getPrev().setNext(removedNode.getNext());
			removedNode.getNext().setPrev(removedNode.getPrev());
			entries--;

		}

		return removedValue;
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
	public void putAll(Map<? extends K, ? extends V> m) {
		Set<? extends Map.Entry<? extends K, ? extends V>> set = entrySet();
		for (Map.Entry<? extends K, ? extends V> temp : set) {
			put(temp.getKey(), temp.getValue());
		}
	}

	/**
	 * Overriding
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return entrySet();
	}

	/**
	 * Find the nearest node
	 * 
	 * @param key
	 *            the key
	 * @return the nearest node which is less than key
	 */
	private SkipNode<K, V> findNode(Object key) {
		SkipNode<K, V> nodeOut;
		nodeOut = headNode;
		while (true) {
			while ((nodeOut.getNext().getKey()) != posInf
					&& (nodeOut.getNext().getKey()).compareTo((Integer) key) <= 0)
				nodeOut = nodeOut.getNext();
			if (nodeOut.getDown() != null)
				nodeOut = nodeOut.getDown();
			else
				break;
		}
		return nodeOut;
	}

	/**
	 * Add Layer
	 */
	private void addLayer() {
		SkipNode<K, V> node1, node2;
		node1 = new SkipNode<K, V>(negInf, null);
		node2 = new SkipNode<K, V>(posInf, null);
		node1.setNext(node2);
		node1.setDown(headNode);
		node2.setPrev(node1);
		node2.setDown(tailNode);

		headNode.setUp(node1);
		tailNode.setUp(node2);

		headNode = node1;
		tailNode = node2;

		height++;
	}
}
