import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class OrderProcessing {
	private static final int PROCESSORS = 5;
	
	private List<WorkOrder> orders = new ArrayList<>();
	private WorkOrder[] processors = new WorkOrder[PROCESSORS];
	
	public void runSimulation(Container cont) {
		//first we setup the workorders
		for (WorkOrder wo : orders)
			cont.add(wo);
		
	
		//fill the processors for round 1
		for (int i = 0; i < PROCESSORS; ++i)
			processors[i] = cont.getNext();
		
		int cycles = 0;
		int done = 0;
		
		while (true) {
			++cycles;
			for (int i = 0; i < PROCESSORS; ++i) {
				if (processors[i] != null && !processors[i].runOneCycle()) {
					//our timer has expired
					System.out.println("WorkOrder: " + processors[i].getName() + " completed at cycle: " + cycles);
					if (cont.isEmpty()) { processors[i] = null ; ++done; break; }
					processors[i] = cont.getNext();
				}
			}
			if (done >= PROCESSORS) break; 
			
			//System.out.println("Cycle: " + cycles);
			
		}
		System.out.println("Total Cycles: " + cycles);
	}
	
	public void initialize(int count) {
		orders.clear();
		Random rand = new Random(1234);
		for (int i = 0; i < count; ++i) {
			orders.add(new WorkOrder(i, rand.nextInt(100), rand.nextInt(25), "WO-" + i));
		}
	}

	public void printWorkOrders() {
		System.out.println("**Dump Workorders **");
		for (WorkOrder wo : orders) 
			System.out.println(wo);
		System.out.println("*********************");
	}

}
