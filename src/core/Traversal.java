package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Traversal {
	/** */
	public void dfsIterative_endOfVisitOrder(ArrayList<LinkedList<Integer>> edges, LinkedList<Integer> results, VertexState[] states, int vertex) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(vertex);

		while( ! stack.isEmpty()) {
			int currentVertex = stack.pop();
			
			// if currentVertex is visited, all his neighbors are already in stack : it is an end of visit for currentVertex
			if(states[currentVertex] == VertexState.VISITED) {
				states[currentVertex] = VertexState.FULLY_VISITED;
				results.add(currentVertex);
			}
			
			else if(states[currentVertex] == VertexState.UNVISITED) {
				states[currentVertex] = VertexState.VISITED;
				
				// using a temporary stack for neighbors to compute in the same order as recursive dfs
				Stack<Integer> neighborsStack = new Stack<Integer>();
				for(int neighbor : edges.get(currentVertex))
					if(states[neighbor] == VertexState.UNVISITED)
						neighborsStack.push(neighbor);
				
				// no neighbor means end of visit for currentVertex 
				if(neighborsStack.isEmpty()) {
					states[currentVertex] = VertexState.FULLY_VISITED;
					results.add(currentVertex);
				}
				// having at least one neighbor means we have to come back to currentVertex after visiting his neighbors
				else {
					stack.push(currentVertex);
					while( ! neighborsStack.isEmpty())
						stack.push(neighborsStack.pop());
				}
			}
		}
	}
	
	/** */
	public void dfsIterative_stronglyConnectedComponentsAffectation(ArrayList<LinkedList<Integer>> edges, int[] affectations, VertexState[] states, int currentStronglyConnectedAffectation, int vertex) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(vertex);

		while( ! stack.isEmpty()) {
			int currentVertex = stack.pop();
			
			// if currentVertex is visited, all his neighbors are already in stack : it is an end of visit for currentVertex
			if(states[currentVertex] == VertexState.VISITED) {
				states[currentVertex] = VertexState.FULLY_VISITED;
				affectations[currentVertex] = currentStronglyConnectedAffectation;
			}
			
			else if(states[currentVertex] == VertexState.UNVISITED) {
				states[currentVertex] = VertexState.VISITED;
				
				// using a temporary stack for neighbors to compute in the same order as recursive dfs
				Stack<Integer> neighborsStack = new Stack<Integer>();
				for(int neighbor : edges.get(currentVertex))
					if(states[neighbor] == VertexState.UNVISITED)
						neighborsStack.push(neighbor);
				
				// no neighbor means end of visit for currentVertex 
				if(neighborsStack.isEmpty()) {
					states[currentVertex] = VertexState.FULLY_VISITED;
					affectations[currentVertex] = currentStronglyConnectedAffectation;
				}
				// having at least one neighbor means we have to come back to currentVertex after visiting his neighbors
				else {
					stack.push(currentVertex);
					while( ! neighborsStack.isEmpty())
						stack.push(neighborsStack.pop());
				}
			}
		}
	}
}
