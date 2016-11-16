/**
 * CircularArrayBuffer class implements CircularBuffer interface.
 * @author Jae Min Baek
 *
 * @param <T> Generic Type
 * @version 1.0
 */
public class CircularArrayBuffer<T> implements CircularBuffer<T> {
	public T[] array;
	public int entries;
	public BufferGrowMode myMode=BufferGrowMode.REGROW;
	public int front, rear;

	/**
	 * Default Constructor
	 */
	@SuppressWarnings("unchecked")
	public CircularArrayBuffer() {
		array = (T[]) new Object[10];
	}

	/**
	 * Overriding isEmpty() method 
	 */
	@Override
	public boolean isEmpty() {
		return entries == 0;
	}

	/**
	 * Overriding capacity() method 
	 */
	@Override
	public int capacity() {
		return array.length;
	}

	/**
	 * Overriding size() method 
	 */
	@Override
	public int size() {
		return entries;
	}

	/**
	 * Overriding setMode(BufferGrowMode newMode) method 
	 */
	@Override
	public void setMode(BufferGrowMode newMode) {
		myMode = newMode;
	}

	/**
	 * check if it's full. 
	 * @return true if it's full 
	 */
	private boolean isFull(){
		return rear==capacity();
	}
	
	/**
	 * Overriding add(T item) method 
	 */
	@Override
	public void add(T item) {
		//check if the item is null
		if(item!=null){
			//if it's not full, normal transaction to the array
			if (!isFull()) {
				if (front == rear)
					if(myMode.equals(BufferGrowMode.OVERWRITE))
						front++;
				if (array[rear] == null)
					entries++;
				array[rear] = item;
				rear++;
			} else {
				//if it's regrow mode, double the size of the array and queue the item.
				if (myMode.equals(BufferGrowMode.REGROW)) {
					//just like an Arraylist
					@SuppressWarnings("unchecked")
					T[] newArray = (T[]) new Object[capacity()*2];
					for (int i = front; i < rear; i++) {
						newArray[i] = array[i];
					}
					newArray[capacity()/2] = item;
					rear++;
					entries++;
					array = newArray;
				} else if(myMode.equals(BufferGrowMode.OVERWRITE)){
					front = (rear + 1) % (capacity());
					array[0] = item;
					rear=1;
				}
			}	
		}
	}

	/**
	 * Overriding remove() method 
	 */
	@Override
	public T remove() {
		T frontElement = array[front];
		if (!isEmpty()) {
			//dequeue
			//set it to null
			array[front]=null;
			front++;
			entries--;
		}
		return frontElement;
	}

	/**
	 * Overriding contains(T item) method 
	 */
	@Override
	public boolean contains(T item) {
		//check if it's null
		if(item!=null){
			for (int i = 0; i < capacity(); i++) {
				if (array[i] != null && array[i].equals(item))
					return true;
			}
		}
		return false;
	}
}