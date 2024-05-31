package src;

import src.graph.*;

class App {
    public static void main(String[] args) {
        BipartiteGraph g = new BipartiteGraph(2,2); 
        Edge e = new Edge();
        g.addEdge(0, 0, e);
        g.addEdge(0, 1, e);
        g.addEdge(1, 1, e);
        System.out.println(g); 
    }
}