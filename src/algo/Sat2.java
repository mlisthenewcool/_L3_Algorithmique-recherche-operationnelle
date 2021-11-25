package algo;

import java.util.Collections;
import java.util.LinkedList;

import core.Graph;
import core.Traversal;
import core.VertexState;

public class Sat2 {
	private final Graph graph;
	private final int graphSize;
	private int[] stronglyConnectedComponentsAffectation;
	private boolean[] solutions;
	
	/** */
	public Sat2(Graph graph) {
		this.graphSize = graph.get().size();
		this.graph = graph;
		this.stronglyConnectedComponentsAffectation = new int[this.graphSize];
		this.solutions = null;
	}
	
	/** */
	public boolean[] getSolutions() {
		return this.solutions;
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
		int currentStronglyConnectedComponent = 0;
		for(int i : endOfVisitOrder)
			if(states[i] == VertexState.UNVISITED)
				traversal.dfsIterative_stronglyConnectedComponentsAffectation(this.graph.getTranspose(), this.stronglyConnectedComponentsAffectation, states, currentStronglyConnectedComponent++, i);
		
		if(this.isSolvable())
			this.solve();
	}
	
	/** */
	private boolean isSolvable() {
		for(int i = 0; i < this.graphSize; i += 2)
			if(this.stronglyConnectedComponentsAffectation[i] == this.stronglyConnectedComponentsAffectation[i + 1])
				return false;
		
		return true;
	}
	
	/** */
	private void solve() {
		this.solutions = new boolean[this.graphSize / 2];
		
		for(int i = 0; i < this.graphSize; i += 2)
			this.solutions[i / 2] = this.stronglyConnectedComponentsAffectation[i] > this.stronglyConnectedComponentsAffectation[i + 1];
	}
}
