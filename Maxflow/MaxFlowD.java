package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Dinic Approach O(V^2*E)
 * 
 * @Author: Abhishek Deo, Jaemin Baek
 */
public class MaxFlowD {
	
	private static long start;
	private static long elapsed;
	private static long total;
	private static double seconds;
	
	@SuppressWarnings("unchecked")
	private static List<EdgeD>[] init(int nodes) {
		List<EdgeD>[] graph = new List[nodes];
		for (int i = 0; i < nodes; i++)
			graph[i] = new ArrayList<>();
		return graph;
	}
	
	private static void add(List<EdgeD>[] graph, int source, int sink, int cap) {
		graph[source].add(new EdgeD(cap, graph[sink].size(), sink));
		graph[sink].add(new EdgeD(0, graph[source].size() - 1, source));
	}
	
	private static int dfs(List<EdgeD>[] graph, int dest, int src, 
			int rrCap, int [] distance, int [] pointer) {
		if (src == dest) {
			return rrCap;
		}
		for ( ; pointer[src] < graph[src].size(); ++pointer[src]) {
			EdgeD edge = graph[src].get(pointer[src]);
			if (distance[edge.sink] == distance[src] + 1 
					&& edge.rCap < edge.cap) {
				//Residual Capacity from a to b is edge.cap - edge.rCap;
				int reCap = edge.cap - edge.rCap;
				
				int x = dfs(graph, dest, edge.sink, 
						Math.min(rrCap, reCap), distance, pointer);
				
				if (x > 0) {
					edge.rCap += x;
					graph[edge.sink].get(edge.rev).rCap -= x;
					return x;
				}
			}
		}
		return -1;
	}
	
	private static boolean bfs(List<EdgeD>[] graph, int [] distance, int src, int dest) {
		Arrays.fill(distance, -1);
		distance[src] = 0;
		int [] queue = new int[graph.length];
		int size = 0;
		queue[size++] = src;
		for (int i = 0; i < size; i++) {
			int x = queue[i];
			for (EdgeD edge : graph[x]) {
				if (edge.rCap < edge.cap && distance[edge.sink] < 0) {
					distance[edge.sink] = distance[x] + 1;
					queue[size++] = edge.sink;
				}
			}
		}
		return distance[dest] >= 0;
	}
	
	private static int maxFlow(List<EdgeD>[] graph, int src, int dest) {
		int flow = 0;
		int [] distance = new int[graph.length];
		while (bfs(graph, distance, src, dest)) {
			int [] pointer = new int[graph.length];
			while (true) {
				int flowState = dfs(graph, dest, src, 
						Integer.MAX_VALUE, distance, pointer);
				if (flowState == 0) {
					break;
				}
				flow += flowState;
			}
		}
		return flow;
	}
	
	public static void first(int nodes) {
		start = System.nanoTime();
		List<EdgeD>[] graph = init(nodes);
		add(graph, 0, 1, 3);
	    add(graph, 0, 2, 2);
	    add(graph, 1, 2, 2);
	    System.out.println("MaxFlow: " + maxFlow(graph, 0, 2));
		elapsed = System.nanoTime();
		total = elapsed - start;
		seconds = (double) total / 100000000;
		System.out.println("Total Time: " + seconds);
	}
	
	public static void second(int nodes) {
		long start = System.nanoTime();
	    List<EdgeD>[] graph1 = init(nodes);
		add(graph1, 0, 1, 3);
	    add(graph1, 0, 2, 3);
	    add(graph1, 1, 2, 2);
	    add(graph1, 1, 3, 3);
	    add(graph1, 2, 4, 2);
	    add(graph1, 3, 4, 4);
	    add(graph1, 3, 5, 2);
	    add(graph1, 4, 5, 3);
	    System.out.println("MaxFlow: " + maxFlow(graph1, 0, 5));
	    long elapsed = System.nanoTime();
		long total = elapsed - start;
		double seconds = (double) total / 100000000;
		System.out.println("Total Time: " + seconds);
	}
	
	public static void third(int nodes) {
		start = System.nanoTime();
		List<EdgeD>[] graph2 = init(6);
	    add(graph2, 0, 1, 16);
	    add(graph2, 0, 2, 13);
	    add(graph2, 1, 2, 10);
	    add(graph2, 2, 1, 4);
	    add(graph2, 1, 3, 12);
	    add(graph2, 2, 4, 14);
	    add(graph2, 4, 3, 7);
	    add(graph2, 3, 2, 9);
	    add(graph2, 4, 5, 4);
	    add(graph2, 3, 5, 20);
	    System.out.println("MaxFlow: " + maxFlow(graph2, 0, 5));
	    elapsed = System.nanoTime();
		total = elapsed - start;
		seconds = (double) total / 100000000;
		System.out.println("Total Time: " + seconds);
	}
	
	public static void main(String [] args) {
		first(3); //4
		second(6); //5
		third(6); //23
	}
	
}
