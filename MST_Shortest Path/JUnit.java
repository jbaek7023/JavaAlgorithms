import java.io.IOException;

import org.junit.Test;

public class JUnit {
	@Test
	public void testAdjInitializeWorks() throws IOException {
		AdjacencyList ad = new AdjacencyList("graphdata.txt",
				GraphType.UNDIRECTED);
		System.out.println(ad);
	}

	@Test
	public void testDJWorks() {
		Dijkstra dj = new Dijkstra();
	}
	
	@Test
	public void testEdgeList() throws IOException{
		EdgeList eg= new EdgeList("graphdata.txt");
		System.out.println(eg);
	}
}
