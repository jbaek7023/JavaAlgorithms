package main;

/*
 * Ford-Fulkerson Approach O((V^2)FLOW)
 * 
 * @Author: Abhishek Deo, Jaemin Baek
 */
public class MaxFlowFF {
	
	private static long start;
	private static long elapsed;
	private static long total;
	private static double seconds;
	
	public static int maxFlow(int[][] cap, int source, int sink) {
		for (int flow = 0; ; ) {
			int pathway = path(cap, new boolean[cap.length], 
					source, sink, Integer.MAX_VALUE);
			if (pathway == 0) {
				return flow;
			}
			flow += pathway;
		}
	}
	
	private static int path(int[][] cap, boolean[] visited, int source, int sink, int rCap) {
		if (source == sink)
			return rCap;
		visited[source] = true;
		for (int vertex = 0; vertex < visited.length; vertex++)
			if (!visited[vertex] && cap[source][vertex] > 0) {
				int x = path(cap, visited, vertex, sink, 
								Math.min(rCap, cap[source][vertex]));
				if (x > 0) {
					cap[source][vertex] -= x;
					cap[vertex][source] += x;
					return x;
				}
			}
		return 0;
	}
	
	public static void main(String [] args) {
		start = System.nanoTime();
		int[][] cap = { { 0, 3, 2 }, { 0, 0, 2 }, { 0, 0, 0 } };
		System.out.println(maxFlow(cap, 0, 2));
		elapsed = System.nanoTime();
		total = elapsed - start;
		seconds = (double) total / 100000000;
		System.out.println(seconds);
	}
	
}
