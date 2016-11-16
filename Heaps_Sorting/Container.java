import java.util.Comparator;

/**
 * This represents a container that holds the workorders
 * 
 * @author robertwaters
 *
 */
public interface Container {
	/**
	 * The comparator that the container will use to arrange the container
	 * 
	 * @param comp
	 */
	public void setComparator(Comparator comp);
	
	/**
	 * Add a workorder to the container
	 */
	public void add(WorkOrder wo);
	
	/**
	 * Gets a workorder (removes it also) from the container
	 */
	public WorkOrder getNext();
	
	/**
	 * Arranges the workorders in the required order
	 * Uses the comparator if necessary
	 * Some data structures may not need this method (like Queue)
	 * Just make it a no-op for those structures.
	 */
	public void arrange();
	
	/**
	 * Reports whether the container is empty or not
	 */
    boolean isEmpty();
 }
