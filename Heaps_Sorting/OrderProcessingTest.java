import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;


public class OrderProcessingTest {
	List<WorkOrder> list = new ArrayList<WorkOrder>();
	List<WorkOrder> list2 = new ArrayList<WorkOrder> ();
	
	private void init() {
		for (int i = 0; i < 10; ++i) {
			list.add(new WorkOrder(i, i*3, i, "A" + i ));
		}
	}
	
	private void init2() {
		for (int i = 0; i < 100; ++i) {
			list2.add(new WorkOrder(i, i + 3, i , "A" + i));
		}
	}
	
	@Test
	public void testQueue() {
		init();
		Queue q = new Queue();
		for (int i=0; i<list.size();++i) {
			q.add(list.get(i));
		}
		for (int i = 0; i < list.size(); ++i) {
			System.out.println(q.getNext());
		}
	}
	
	@Test
	public void testQueueSchedule() {
		OrderProcessing opt = new OrderProcessing();
		opt.initialize(100);
		System.out.println("***First Come First Serve***");
		opt.runSimulation(new Queue());
	}
	
	@Test
	public void testHeap() {
		System.out.println("*****Test the Heap********");
		init();
		Collections.shuffle(list);
		Heap h = new Heap();
		h.setComparator(new HoursComparator());
		for (int i = 0; i < list.size(); ++i) {
			h.add(list.get(i));
		}
		for (int i = 0; i < list.size(); ++i) {
			System.out.println(h.getNext());
		}
	}
	
	@Test
	public void testHeapSchedule() {
		OrderProcessing opt = new OrderProcessing();
		opt.initialize(10);
		opt.printWorkOrders();
		Heap h = new Heap();
		h.setComparator(new HoursComparator());
		System.out.println("**** Test longest job first ****");
		opt.runSimulation(h);
	}
	
	@Test
	public void testQsort() {
		System.out.println("Test Small SortedList");
		SortedList sl = new SortedList();
		//sl.setComparator(new NameComparator());
		sl.setComparator(new HoursComparator());
		init();
		Collections.shuffle(list);
		for (int i = 0; i< list.size(); ++i) {
			sl.add(list.get(i));
			//System.out.println("Add from test");
		}
		sl.arrange();
		for (int i = 0; i < list.size(); ++i) {
			System.out.println(sl.getNext());
		}
	}
	
	@Test
	public void testQsortBig() {
		System.out.println("Test Big SortedList");
		SortedList sl = new SortedList();
		sl.setComparator(new NameComparator());
		init2();
		Collections.shuffle(list2);
		for (int i = 0; i< list2.size(); ++i) {
			sl.add(list2.get(i));
			//System.out.println("Add from test");
		}
		sl.arrange();
		for (int i = 0; i < list2.size(); ++i) {
			System.out.println(sl.getNext());
		}
		
	}
	
	@Test
	public void testQsortSchedule() {
		OrderProcessing opt = new OrderProcessing();
		opt.initialize(100);
		SortedList sl = new SortedList();
		sl.setComparator(new HoursComparator());
		sl.arrange();
		System.out.println(" *** Shortest Job First ****");
		opt.runSimulation(sl);
	}

}
