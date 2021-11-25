package core;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
	private final ArrayList<LinkedList<Integer>> edges;
	private final ArrayList<LinkedList<Integer>> reversedEdges;
	
	/** */
	public Graph(int vertexCount) throws IllegalArgumentException {
		if(vertexCount < 1)
			throw new IllegalArgumentException("Graph must contains at least one vertex.");
		
		this.edges = new ArrayList<LinkedList<Integer>>(vertexCount);
		this.reversedEdges = new ArrayList<LinkedList<Integer>>(vertexCount);
		
		for(int i = 0; i < vertexCount; i++) {
			this.edges.add(new LinkedList<Integer>());
			this.reversedEdges.add(new LinkedList<Integer>());
		}
	}
	
	/** */
	public void addEdge(int source, int destination) {
		if(source != destination) {
			this.edges.get(source).add(destination);
			this.reversedEdges.get(destination).add(source);
		}
	}
	
	/** */
	public void addEdgeWithoutDuplicate(int source, int destination) {
		if(source != destination) {
			if( ! this.edges.get(source).contains(destination))
				this.edges.get(source).add(destination);
			if( ! this.reversedEdges.get(destination).contains(source))
				this.reversedEdges.get(destination).add(source);
		}
	}
	
	/** */
	public void checkVertex(int index) throws IllegalArgumentException {
		if(index > this.edges.size() || index < 0)
			throw new IllegalArgumentException(
					"Vertex's value (" + index + ") is out of range [0, " + this.edges.size() + "].");
	}
	
	/** */
	public ArrayList<LinkedList<Integer>> get() {
		return this.edges;
	}
	
	/** */
	public ArrayList<LinkedList<Integer>> getTranspose() {
		return this.reversedEdges;
	}
}