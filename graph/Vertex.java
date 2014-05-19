/* Vertex.java */

package graph;

import list.*;

/**
 * The Vertex class represents a vertex in the graph.  
 * It contains its adjacency list.
 */

public class Vertex {
	private DList adjacencyedge = new DList();
	private Object item;

	public Vertex(Object item) {
		this.item = item;
	}

	public Object item() {
		return item;
	}

	protected DListNode addEdge(Edge e) {
		DListNode newbie = (DListNode)adjacencyedge.insertBack(e);
		return newbie;
	}

	public int degree() {
		return adjacencyedge.length();
	}
	
	public DList adjacencyedge() {
		return adjacencyedge;
	}
}