package main;

import java.util.ArrayList;
import java.util.List;

/*
 * Edmond-Karp Approach O(min((E^2)V, E*FLOW))
 * 
 * @Author: Abhishek Deo, Jaemin Baek
 */
public class MaxFlowEK {
	
	private static long start;
	private static long elapsed;
	private static long total;
	private static double seconds;
	
	@SuppressWarnings("unchecked")
	private static List<EdgeEK>[] init(int nodes) {
		List<EdgeEK>[] graph = new List[nodes];
		for (int i = 0; i < nodes; i++) {
			graph[i] = new ArrayList<>();
		}
		return graph;
	  }

	  private static void add(List<EdgeEK>[] graph, int src, int sink, int cap) {
		  graph[src].add(new EdgeEK(src, sink, graph[sink].size(), cap));
		  graph[sink].add(new EdgeEK(sink, src, graph[src].size() - 1, 0));
	  }

	  private static int maxFlow(List<EdgeEK>[] graph, int source, int sink) {
		  int flow = 0;
		  int[] queue = new int[graph.length];
		  while (true) {
			  int counter = 0;
			  queue[counter++] = source;
			  EdgeEK[] edges = new EdgeEK[graph.length];
			  for (int i = 0; i < counter && edges[sink] == null; i++) {
				  int current = queue[i];
				  for (EdgeEK edge : graph[current]) {
					  if (edges[edge.sink] == null 
							  && edge.cap > edge.rCap) {
						  edges[edge.sink] = edge;
						  queue[counter++] = edge.sink;
					  }
				  }
			  }
			  if (edges[sink] == null)
				  break;
			  int max = Integer.MAX_VALUE;
			  for (int r = sink; r != source; r = edges[r].source) {
				  max = Math.min(max, edges[r].cap - edges[r].rCap);
			  }
			  for (int res = sink; res != source; res = edges[res].source) {
				  edges[res].rCap += max;
				  graph[edges[res].sink]
						  .get(edges[res].rev).rCap -= max;
			  }
			  flow += max;
		  }
		  return flow;
	  }
	
	public static void first(int nodes) {
		start = System.nanoTime();
		List<EdgeEK>[] graph = init(nodes);
		add(graph, 0, 1, 3);
	    add(graph, 0, 2, 2);
	    add(graph, 1, 2, 2);
	    System.out.println("MaxFlow: " + maxFlow(graph, 0, 2)); //4
		elapsed = System.nanoTime();
		total = elapsed - start;
		seconds = (double) total / 100000000;
		System.out.println("Total Time: " + seconds);
	}
	
	public static void second(int nodes) {
		start = System.nanoTime();
		List<EdgeEK>[] graph = init(nodes);
		add(graph, 0, 1, 3);
	    add(graph, 0, 2, 3);
	    add(graph, 1, 2, 2);
	    add(graph, 1, 3, 3);
	    add(graph, 2, 4, 2);
	    add(graph, 3, 4, 4);
	    add(graph, 3, 5, 2);
	    add(graph, 4, 5, 3);
	    System.out.println("MaxFlow: " + maxFlow(graph, 0, 2)); //5
		elapsed = System.nanoTime();
		total = elapsed - start;
		seconds = (double) total / 100000000;
		System.out.println("Total Time: " + seconds);
	}
	
	public static void main(String [] args) throws ArrayIndexOutOfBoundsException {
		first(3);
		second(6);
	}
	
}
