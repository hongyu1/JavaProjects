package org.grasshub.airportbaggage.algorithm;

import java.util.HashMap;
import java.util.Map;

import org.grasshub.airportbaggage.model.BinaryMinHeap;
import org.grasshub.airportbaggage.model.Edge;
import org.grasshub.airportbaggage.model.Graph;
import org.grasshub.airportbaggage.model.Vertex;

public class DijkstraShortestPath {

    // stores parent of every vertex in shortest distance
    private Map<Vertex<String>, Vertex<String>> parent = new HashMap<>();
    
    // stores shortest distance from root to every vertex
    private Map<Vertex<String>,Integer> distance = new HashMap<>();

    public Map<Vertex<String>,Integer> shortestPath(Graph<String> graph, 
    	Vertex<String> sourceVertex){
    	
    	// heap + map data structure
        BinaryMinHeap<Vertex<String>> minHeap = new BinaryMinHeap<>();

        // initialize all vertex with infinite distance from source vertex
        for(Vertex<String> vertex : graph.getAllVertex()) {
            minHeap.add(Integer.MAX_VALUE, vertex);
        }

        // set distance of source vertex to 0
        minHeap.decrease(sourceVertex, 0);

        // put it in map
        distance.put(sourceVertex, 0);

        // source vertex parent is null
        parent.put(sourceVertex, null);

        //iterate till heap is not empty
        while(!minHeap.empty()){
            // get the min value from heap node which has vertex and distance of
        	// that vertex from source vertex.
            BinaryMinHeap<Vertex<String>>.Node heapNode = minHeap.extractMinNode();
            Vertex<String> current = heapNode.getKey();

            // update shortest distance of current vertex from source vertex
            distance.put(current, heapNode.getWeight());

            // iterate through all edges of current vertex
            for(Edge<String> edge : current.getEdges()){

                // get the adjacent vertex
                Vertex<String> adjacent = getVertexForEdge(current, edge);

                // if heap does not contain adjacent vertex means adjacent vertex
                // already has shortest distance from source vertex
                if(!minHeap.containsData(adjacent)){
                    continue;
                }

                // add distance of current vertex to edge weight to get distance
                // of adjacent vertex from source vertex
                //when it goes through current vertex
                int newDistance = distance.get(current) + edge.getWeight();

                // see if this above calculated distance is less than current 
                // distance stored for adjacent vertex from source vertex
                if(minHeap.getWeight(adjacent) > newDistance) {
                    minHeap.decrease(adjacent, newDistance);
                    parent.put(adjacent, current);
                }
            }
        }
        return distance;
    }
    
    public Map<Vertex<String>, Vertex<String>> getParent() {
    	return parent;
    }

    private Vertex<String> getVertexForEdge(Vertex<String> v, Edge<String> e){
        return e.getVertex1().equals(v) ? e.getVertex2() : e.getVertex1();
    }
}