package org.grasshub.airportbaggage.executor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.grasshub.airportbaggage.algorithm.DijkstraShortestPath;
import org.grasshub.airportbaggage.model.Graph;
import org.grasshub.airportbaggage.model.Vertex;

public class AirportBaggage {
	
	private static final List<String> edgeList = new ArrayList<>();
	private static final Map<String, String> flightDepartures = new HashMap<>();
	private static final List<String> baggageList = new ArrayList<>();
	private static final String INPUT_FILE = "Resources/input.txt";
	private static final String POUND_SIGN = "#";
	private static final String ARRIVAL = "ARRIVAL";
	private static final String BAGGAGE_CLAIM = "BaggageClaim";
	private static final String EMPTY_STRING = "";
	private static final String ONE_EMPTY_SPACE_STRING = " ";
	private static final String COLON = " : ";
	
	private static void readInput() {
		String line;
		int sectionNumber = 0;
		
		// Read from input file and fill in data structures
		try (BufferedReader inputReader = 
				new BufferedReader(new FileReader(INPUT_FILE))) {
			
    		while ((line = inputReader.readLine()) != null) {
    			if (!line.startsWith(POUND_SIGN)) {
 				
    				if (sectionNumber == 1) {
    					edgeList.add(line);   						
    				} else if (sectionNumber == 2) {
    					// Split with whitespace
        				String[] tokens = line.split("\\s");
    					flightDepartures.put(tokens[0], tokens[1]); 						
    				} else if (sectionNumber == 3) {
    					baggageList.add(line);
    				}   				
    			} else {
    				sectionNumber += 1;
    			}
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	private static void outputResult(Graph<String> graph, String[] tokens, 
		Vertex<String> sourceVertex, Map<Vertex<String>, Integer> distances,
		Map<Vertex<String>, Vertex<String>> parentPathes) {
		String pathString = EMPTY_STRING;
		
		Vertex<String> startVertex = graph.getVertex(tokens[2]);
		List<String> path = new ArrayList<>();
		 
		// generate the shortest path reversely
		while (!startVertex.equals(sourceVertex)) {
			startVertex = parentPathes.get(startVertex);
			path.add(ONE_EMPTY_SPACE_STRING + startVertex.getName());
		}
		
		Collections.reverse(path);
		
		for (String node: path) {
			pathString = pathString + node;
		}
		 
		pathString = pathString + ONE_EMPTY_SPACE_STRING + tokens[2];
		 
		// print out the result
		System.out.println(tokens[0] + pathString + COLON + 
				distances.get(graph.getVertex(tokens[2])));
	}
	
	private static void routeBaggage() throws Exception {
		
		Graph<String> graph = new Graph<>(false);
		DijkstraShortestPath dsp = new DijkstraShortestPath();
				
		// Use the Dijkstra algorithm to find the shortest path, and output the
		// result to console
		for (String bag: baggageList) {
			String[] tokens = bag.split("\\s");
			
			// Set the gate to BaggageClaim for ARRIVAL flight
			if (tokens[2].equals(ARRIVAL)) {
				tokens[2] = BAGGAGE_CLAIM;
			} else {
				// Find the flight departure gate
				tokens[2] = flightDepartures.get(tokens[2]);	
			}
			
			for (String edge: edgeList) {
				String[] edgeDetails = edge.split("\\s");
				
				graph.addEdge(edgeDetails[0], edgeDetails[1], 
					Integer.parseInt(edgeDetails[2]));
			}

	        Vertex<String> sourceVertex = graph.getVertex(tokens[1]);
	        Map<Vertex<String>, Integer> distances = 
	        	dsp.shortestPath(graph, sourceVertex);
	        Map<Vertex<String>, Vertex<String>> parentPathes = dsp.getParent();
	        
	        outputResult(graph, tokens, sourceVertex, distances, parentPathes);
		}
	}

	public static void main(String[] args) throws Exception {
		readInput(); 
		
		routeBaggage();
	}

}
