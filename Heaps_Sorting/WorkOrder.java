/** 
 * This class represents a single workorder coming in
 * 
 * @author robertwaters
 *
 */
public class WorkOrder {
	/** the work order number */
	private int number;
	
	/** the amount of time this will take */
	private int hours;
	
	/** the name of the customer */
	private String name;
	
	/** the priority of the job */
	private int priority;
	
	/**
	 * Make a new workorder
	 * @param n the number
	 * @param h the hours it will take
	 * @param s the name of the customer
	 */
	public WorkOrder(int n, int h, int p, String s) {
		number = n;
		hours = h;
		priority = p;
		name = s;
	}
	
	public String getName() { return name; }
	
	public int getHours() { return hours; }
	
	public int getNumber() { return number; }
	
	public int getPriority() { return priority; }
	
	public boolean runOneCycle() {
		--hours;
		return hours > 0;
	}
	
	public String toString() {
		return "WO - " + number + " hours: " + hours + " priority: " + priority + " name: " + name;
	}

}
