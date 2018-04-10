package org.grasshub.airportbaggage.test;

import java.util.Map;

import org.grasshub.airportbaggage.algorithm.DijkstraShortestPath;
import org.grasshub.airportbaggage.model.Graph;
import org.grasshub.airportbaggage.model.Vertex;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestDijkstraAlgorithm {

    @Test
    public void testExcute() {
    	
    	Graph<String> graph = new Graph<>(false);
    	    
    	graph.addEdge("2", "1", 5);
    	graph.addEdge("2", "3", 2);
    	graph.addEdge("1", "4", 9);
    	graph.addEdge("1", "5", 3);
    	graph.addEdge("6", "5", 2);
    	graph.addEdge("4", "6", 2);
    	graph.addEdge("3", "4", 3);

    	DijkstraShortestPath dsp = new DijkstraShortestPath();
    	// Lets check from vertex 1 to 6
        // Need to verify if the shortest path is not null and contain valid result
    	Vertex<String> sourceVertex = graph.getVertex("1");
    	Map<Vertex<String>,Integer> distances = dsp.shortestPath(graph, sourceVertex);

        // Lets check from vertex 1 to 6
        // Need to verify if the shortest path is not null and contain valid result
    	Integer shortestDistance = distances.get(graph.getVertex("6"));
     
        assertNotNull(shortestDistance);
        assertTrue(shortestDistance > 0);
       
        // Lets check from vertex 2 to 5
        Vertex<String> startVertex = graph.getVertex("2");
    	Map<Vertex<String>,Integer> distance = dsp.shortestPath(graph, startVertex);
    	
    	Integer shortDistance = distance.get(graph.getVertex("5"));
        
        assertNotNull(shortDistance);
        assertTrue(shortDistance > 0);
    }
}