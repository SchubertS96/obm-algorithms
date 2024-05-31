package src.graph;

import java.util.*;

public class Vertex {
    Map<Vertex, Edge> adj; 
    final int id; 

    public Vertex(int id) {
        adj = new HashMap<>(); 
        this.id = id; 
    }

    public void addNeighbor(Vertex neighbor, Edge e) {
        adj.put(neighbor, e); 
    }

    public void deleteNeighbor(Vertex neighbor) {
        adj.remove(neighbor);
    }

    public Set<Vertex> getNeighbors() {
        return adj.keySet();
    }

    public Edge getEdge(Vertex neighbor) {
        return adj.get(neighbor);
    }

    public int getId() {
        return id; 
    }

    @Override
    public String toString() {
        return ""+id;
    }
}