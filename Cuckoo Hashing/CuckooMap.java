/*
 * Jae Min Baek
 * CS 1332 HW5
 * CuckooMap
 */
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

/**
 * 
 * @author jaeminbaek
 * 
 * @param <K>
 *            generic type
 * @param <V>
 *            generic type
 */
public class CuckooMap<K extends Hashable, V> implements Map<K, V> {
	private final double MAX_LOAD_FACTOR = 0.9;
	private int entries;
	private int upperMostMax = 0;

	private Random random;
	private Bucket<K, V>[] table1;
	private Bucket<K, V>[] table2;
	private int p1;
	private int p2;

	/**
	 * Constructor
	 * 
	 * @param size
	 *            the size
	 */
	CuckooMap(int size) {
		table1 = (Bucket<K, V>[]) new Bucket[size];
		table2 = (Bucket<K, V>[]) new Bucket[size];
		random = new Random();
		entries = 0;
		upperMostMax = 0;
	}

	/**
	 * Overriding
	 */
	@Override
	public void clear() {
		table1 = new Bucket[10];
		table2 = new Bucket[10];
		entries = 0;
		p1 = random.nextInt(Integer.MAX_VALUE);
		p2 = random.nextInt(Integer.MAX_VALUE);
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean containsKey(Object o) {
		boolean found = true;
		if (o == null)
			throw new IllegalArgumentException();
		int index1 = Math.abs(p1 * o.hashCode()) % (table1.length);
		int index2 = Math.abs(p2 * o.hashCode()) % (table2.length);
		Bucket<K, V> entry = table1[index1];
		while (entry != null) {
			if (entry.getKey().equals(o))
				return found;
			index1 = Math.abs(index1 + 1) % (table1.length);
			entry = table1[index1];
		}
		entry = table2[index2];
		while (entry != null) {
			if (entry.getKey().equals(o))
				return found;
			index2 = Math.abs(index2 + 1) % (table2.length);
			entry = table2[index2];
		}
		return !found;
	}

	/**
	 * Overriding
	 */
	@Override
	public boolean containsValue(Object o) {
		if (o == null)
			return false;
		for (int i = 0; i < table1.length; i++) {
			Bucket<K, V> entry = table1[i];
			if (entry != null && entry.getValue().equals(o))
				return true;
			Bucket<K, V> entry2 = table2[i];
			if (entry2 != null && entry2.getValue().equals(o))
				return true;
		}
		return false;
	}

	/**
	 * Overriding
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		if (isEmpty()) {
			return null;
		}
		Set<Map.Entry<K, V>> set = new TreeSet<Map.Entry<K, V>>();
		for (int i = 0; i < table1.length; i++) {
			Bucket<K, V> entry1 = table1[i];
			if (entry1 != null)
				set.add(entry1);
			Bucket<K, V> entry2 = table2[i];
			if (entry2 != null)
				set.add(entry2);
		}
		return set;
	}

	/**
	 * Overriding
	 */
	@Override
	public V get(Object o) {
		K key = (K) o;
		int index1 = Math.abs((key.hash1() * p1) % table1.length);
		
		if (table1[index1] != null && table1[index1].getKey().equals(key)) {
			return table1[index1].getValue();
		}
		int index2 = Math.abs((key.hash2() * p2) % table2.length);

		if (table2[index2] != null && table2[index2].getKey().equals(key)) {
			return table2[index2].getValue();
		}
		return null;
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
	public Set<K> keySet() {
		TreeSet<K> keys = new TreeSet<K>();
		for (int i = 0; i < table1.length; i++) {
			Bucket<K, V> entry = table1[i];
			if (entry != null) {
				keys.add(entry.getKey());
			}
		}
		for (int i = 0; i < table2.length; i++) {
			Bucket<K, V> entry = table2[i];
			if (entry != null) {
				keys.add(entry.getKey());
			}
		}
		return keys;
	}

	/**
	 * Overriding
	 */
	@Override
	public V put(K key, V value) {
		if (key == null)
			return null;
		if (value == null)
			return null;
		Bucket<K, V> bucket = new Bucket<K, V>(key, value);
		return upperMost1(bucket);
	}

	/**
	 * Recursive Call for table 1
	 * 
	 * @param newTable
	 *            new table
	 * @return the old value
	 */
	private V upperMost1(Bucket<K, V> newTable) {
		K key = newTable.getKey();
		V value = newTable.getValue();
		int index = Math.abs((key.hash1() * p1) % table1.length);
		if (upperMostMax > entries) {
			regrow();
		}
		upperMostMax++;
		if (table1[index] == null) {
			if (checkLoadFactor() == false) {
				regrow();
			}
			table1[index] = newTable;
			entries++;
			return null;
		} else {
			if (table1[index].getKey().equals(key)) {
				V oldValue = table1[index].getValue();
				table1[index].setValue(value);
				return oldValue;
			} else {
				Bucket<K, V> oldBucket = table1[index];
				table1[index] = newTable;
				return upperMost2(oldBucket);
			}
		}
	}

	/**
	 * Recursive Call for table 2
	 * 
	 * @param newTable
	 *            new table
	 * @return the old value
	 */
	private V upperMost2(Bucket<K, V> newTable) {
		K key = newTable.getKey();
		V value = newTable.getValue();
		int index = Math.abs((key.hash2() * p2) % table2.length);
		if (upperMostMax > entries) {
			regrow();
		}
		upperMostMax++;
		if (table2[index] == null) {
			if (checkLoadFactor() == false) {
				regrow();
			}
			table2[index] = newTable;
			entries++;
			return null;
		} else {
			if (table2[index].getKey().equals(key)) {
				V oldValue = table2[index].getValue();
				table2[index].setValue(value);
				return oldValue;
			} else {

				Bucket<K, V> oldBucket = table2[index];
				table2[index] = newTable;
				return upperMost1(oldBucket);
			}
		}
	}

	/**
	 * Check the load factor
	 * 
	 * @return true if it's ok
	 */
	private boolean checkLoadFactor() {
		return MAX_LOAD_FACTOR >= (double) entries
				/ (table1.length + table2.length);
	}

	/**
	 * Regrowing
	 */
	private void regrow() {
		upperMostMax = 0;
		entries = 0;
		p1 = random.nextInt(Integer.MAX_VALUE);
		p2 = random.nextInt(Integer.MAX_VALUE);
		Bucket<K, V>[] newtable1 = table1;
		Bucket<K, V>[] newtable2 = table2;
		table1 = (Bucket<K, V>[]) new Bucket[table1.length * 2];
		table2 = (Bucket<K, V>[]) new Bucket[table1.length * 2];
		for (int i = 0; i < newtable1.length; i++) {
			if (newtable1[i] != null) {
				put(newtable1[i].getKey(), newtable1[i].getValue());
			}
			if (newtable2[i] != null) {
				put(newtable2[i].getKey(), newtable2[i].getValue());
			}
		}
	}

	/**
	 * Overriding
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		Set<? extends Map.Entry<? extends K, ? extends V>> set = map.entrySet();
		for (Map.Entry<? extends K, ? extends V> temp : set) {
			put(temp.getKey(), temp.getValue());
		}
	}

	/**
	 * Overriding
	 */
	@Override
	public V remove(Object o) {
		if (o == null)
			return null;
		V value = null;
		int index1 = Math.abs(p1 * o.hashCode()) % (table1.length);
		int index2 = Math.abs(p2 * o.hashCode()) % (table2.length);
		Bucket<K, V> entry = table1[index1];
		while (entry != null) {
			if (entry.getKey().equals(o)) {
				entries--;
				value = entry.getValue();
				entry.setValue(null);
				return value;
			}
			index1 = Math.abs(index1 + 1) % (table1.length);
			entry = table1[index1];
		}
		entry = table2[index2];
		while (entry != null) {
			if (entry.getKey().equals(o)) {
				entries--;
				value = entry.getValue();
				entry.setValue(null);
				return value;
			}
			index2 = Math.abs(index2 + 1) % (table2.length);
			entry = table2[index2];
		}
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
	public Collection<V> values() {
		Collection<V> collection = new ArrayList<V>();
		if (isEmpty()) {
			return null;
		}
		for (int i = 0; i < table1.length; i++) {
			if (table1[i] != null) {
				collection.add(table1[i].getValue());
			}
			if (table2[i] != null) {
				collection.add(table2[i].getValue());
			}
		}
		return collection;
	}

	/**
	 * The Bucket class
	 * 
	 * @author jaeminbaek
	 * 
	 * @param <K>
	 *            the generic type
	 * @param <V>
	 *            the generic type
	 */
	private class Bucket<K extends Comparable, V> implements Map.Entry<K, V>,
			Comparable {
		private K key;
		private V value;

		/**
		 * Constructor.
		 */
		public Bucket(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Overriding
		 */
		@Override
		public int compareTo(Object o) {
			Bucket<K, V> bucket = (Bucket<K, V>) o;
			return key.compareTo(bucket.getKey());
		}

		/**
		 * Overriding
		 */
		@Override
		public K getKey() {
			return key;
		}

		/**
		 * Overriding
		 */
		@Override
		public V getValue() {
			return value;
		}

		/**
		 * Overriding
		 */
		@Override
		public V setValue(V value) {
			return this.value = value;
		}
	}
}