package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import algo.Kosaraju;
import algo.Sat2;
import core.Graph;

public class Launcher {
	// by default, we expect data to be at same level as src/ folder
	// check makeXXXGraph() functions to change that.
	private final static String filenameKosaraju = "/graph16";
	private final static String filenameSat = "/sat10";
	
	// should we display the arrays ? convenient to check tiny instances.
	private final static boolean showResults = true;
	
	/** */
	public static void main(String[] args) {
		long start;
		try {
			System.out.println("******************** KOSARAJU ********************");
			Graph graphKosaraju = makeKosarajuGraph();
			Kosaraju kosaraju = new Kosaraju(graphKosaraju);
			start = System.currentTimeMillis();
			kosaraju.run();
			System.out.printf("Execution time : %d ms.\n", System.currentTimeMillis() - start);
			System.out.printf("%d strongly connected components found.\n", kosaraju.getStronglyConnectedComponents().size());
			if(showResults)
				System.out.println(kosaraju.getStronglyConnectedComponents());
			
			System.out.println("\n**************** SATISFIABILITY-2 ****************");
			Graph graphSat = makeSatGraph();
			Sat2 sat = new Sat2(graphSat);
			start = System.currentTimeMillis();
			sat.run();
			System.out.printf("Execution time : %d ms.\n", System.currentTimeMillis() - start);
			System.out.printf("Is formula solvable ? %b.\n", sat.getSolutions() != null);
			if(showResults)
				System.out.println(Arrays.toString(sat.getSolutions()));
			
		} catch (FileNotFoundException | IllegalArgumentException | NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/** */
	private static Graph makeKosarajuGraph() throws FileNotFoundException, IllegalArgumentException, NullPointerException {
		File file = new File(new File("data").getAbsolutePath().concat(filenameKosaraju));
		Scanner scanner = new Scanner(file);
		
		if( ! scanner.hasNextLine()) {
			scanner.close();
			return null;
		}
		else {
			Graph graph = new Graph(scanner.nextInt());
			int source, destination;
			while(scanner.hasNextLine()) {
				source = scanner.nextInt();
				destination = scanner.nextInt();
				graph.checkVertex(source);
				graph.checkVertex(destination);
				graph.addEdgeWithoutDuplicate(source, destination);
			}
			
			scanner.close();
			return graph;
		}
	}
	
	/** Vertices are represented that way : [a, !a, b, !b, c, !c, ...] */
	private static int correspondingIndexInSatGraph(int index) {
		return index < 0 ? Math.abs(index) * 2 - 1 : index * 2 - 2;
	}
	
	/** */
	private static Graph makeSatGraph() throws FileNotFoundException, IllegalArgumentException, NullPointerException {
		File file = new File(new File("data").getAbsolutePath().concat(filenameSat));
		Scanner scanner = new Scanner(file);
		
		if( ! scanner.hasNextLine()) {
			scanner.close();
			return null;
		}
		else {
			Graph graph = new Graph(scanner.nextInt() * 2);
			int source, destination;
			while(scanner.hasNextLine()) {
				source = correspondingIndexInSatGraph(scanner.nextInt());
				destination = correspondingIndexInSatGraph(scanner.nextInt());
				graph.checkVertex(source);
				graph.checkVertex(destination);
				
            	// (a -> b) :: (!a -> b) and (!b -> a)
            	graph.addEdgeWithoutDuplicate(source ^ 1, destination);
            	graph.addEdgeWithoutDuplicate(destination ^ 1, source);
			}
			
			scanner.close();
			return graph;
		}
	}
}