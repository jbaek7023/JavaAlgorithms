import java.util.Map.Entry;

/**
 * This is SkipNode!
 * 
 * @author jaeminbaek
 * 
 * @param <K>	the generic type
 * @param <V>	the generic type
 */
public class SkipNode<K, V> implements Entry<K, V> {

	private K key;
	private V value;
	private SkipNode<K, V> next;
	private SkipNode<K, V> prev;
	private SkipNode<K, V> up;
	private SkipNode<K, V> down;

	/**
	 * Default Constructor
	 */
	public SkipNode() {
	}

	/**
	 * Stip Node Constructor
	 * @param negInf
	 * @param value
	 */
	public SkipNode(K negInf, V value) {
		this.key = negInf;
		this.value = value;
		next = null;
		prev = null;
		up = null;
		down = null;
	}

	///Getters and setters below
	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public V setValue(V value) {
		V temp = this.value;
		this.value = value;
		return temp;
	}

	public SkipNode<K, V> getNext() {
		return next;
	}

	public void setNext(SkipNode<K, V> next) {
		this.next = next;
	}

	public SkipNode<K, V> getPrev() {
		return prev;
	}

	public void setPrev(SkipNode<K, V> prev) {
		this.prev = prev;
	}

	public SkipNode<K, V> getUp() {
		return up;
	}

	public void setUp(SkipNode<K, V> up) {
		this.up = up;
	}

	public SkipNode<K, V> getDown() {
		return down;
	}

	public void setDown(SkipNode<K, V> down) {
		this.down = down;
	}
}
