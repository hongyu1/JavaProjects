package org.grasshub.airportbaggage.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T>{

    private List<Edge<T>> allEdges;
    private Map<String,Vertex<T>> allVertex;
    boolean isDirected = false;
    
    public Graph(boolean isDirected){
        allEdges = new ArrayList<Edge<T>>();
        allVertex = new HashMap<String,Vertex<T>>();
        this.isDirected = isDirected;
    }
    
    public void addEdge(String name1, String name2){
        addEdge(name1,name2,0);
    }
    
    //This works only for directed graph because for undirected graph we can end up
    //adding edges two times to allEdges
    public void addVertex(Vertex<T> vertex){
        if(allVertex.containsKey(vertex.getName())){
            return;
        }
        allVertex.put(vertex.getName(), vertex);
        for(Edge<T> edge : vertex.getEdges()){
            allEdges.add(edge);
        }
    }
    
    public Vertex<T> addSingleVertex(String name){
        if(allVertex.containsKey(name)){
            return allVertex.get(name);
        }
        Vertex<T> v = new Vertex<T>(name);
        allVertex.put(name, v);
        return v;
    }
    
    public Vertex<T> getVertex(String name){
        return allVertex.get(name);
    }
    
    public void addEdge(String name1,String name2, int weight){
        Vertex<T> vertex1 = null;
        if(allVertex.containsKey(name1)){
            vertex1 = allVertex.get(name1);
        }else{
            vertex1 = new Vertex<T>(name1);
            allVertex.put(name1, vertex1);
        }
        Vertex<T> vertex2 = null;
        if(allVertex.containsKey(name2)){
            vertex2 = allVertex.get(name2);
        }else{
            vertex2 = new Vertex<T>(name2);
            allVertex.put(name2, vertex2);
        }

        Edge<T> edge = new Edge<T>(vertex1,vertex2,isDirected,weight);
        allEdges.add(edge);
        vertex1.addAdjacentVertex(edge, vertex2);
        if(!isDirected){
            vertex2.addAdjacentVertex(edge, vertex1);
        }

    }
    
    public List<Edge<T>> getAllEdges(){
        return allEdges;
    }
    
    public Collection<Vertex<T>> getAllVertex(){
        return allVertex.values();
    }
    public void setDataForVertex(long id, T data){
        if(allVertex.containsKey(id)){
            Vertex<T> vertex = allVertex.get(id);
            vertex.setData(data);
        }
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        for(Edge<T> edge : getAllEdges()){
            buffer.append(edge.getVertex1() + " " + edge.getVertex2() + " " + edge.getWeight());
            buffer.append("\n");
        }
        return buffer.toString();
    }
}


