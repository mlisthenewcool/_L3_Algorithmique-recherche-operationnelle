package algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import core.Graph;
import core.Traversal;
import core.VertexState;

public class Kosaraju {
	private ArrayList<LinkedList<Integer>> stronglyConnectedComponents;
	private final Graph graph;
	private final int graphSize;
	
	/** */
	public Kosaraju(Graph graph) {
		if(graph == null)
			throw new NullPointerException("Graph can't be null");
		
		this.stronglyConnectedComponents = new ArrayList<LinkedList<Integer>>();
		this.graph = graph;
		this.graphSize = this.graph.get().size();
	}
	
	/** */
	public ArrayList<LinkedList<Integer>> getStronglyConnectedComponents() {
		return this.stronglyConnectedComponents;
	}
	
	/** */
	private void initializeVertexStates(VertexState[] states) {
		for(int i = 0; i < this.graph.get().size(); i++)
			states[i] = VertexState.UNVISITED;
	}
	
	/** */
	public void run() {
		LinkedList<Integer> endOfVisitOrder = new LinkedList<Integer>();
		VertexState[] states = new VertexState[this.graphSize];
		Traversal traversal = new Traversal();
		
		this.initializeVertexStates(states);
		
		// first dfs
		for(int i = 0; i < this.graphSize; i++)
			if(states[i] == VertexState.UNVISITED)
				traversal.dfsIterative_endOfVisitOrder(this.graph.get(), endOfVisitOrder, states, i);
		
		// clean states and reverse order to run second dfs
		this.initializeVertexStates(states);
		Collections.reverse(endOfVisitOrder);
		
		// second dfs
		for(int i : endOfVisitOrder) {
			if(states[i] == VertexState.UNVISITED) {
				LinkedList<Integer> stronglyConnectedComponent = new LinkedList<Integer>();
				traversal.dfsIterative_endOfVisitOrder(this.graph.getTranspose(), stronglyConnectedComponent, states, i);
				this.stronglyConnectedComponents.add(stronglyConnectedComponent);
			}
		}
	}
}