package src;

import src.algorithms.Ranking;
import src.algorithms.RelativeBalance;
import src.graph.*;

class App {
    public static void main(String[] args) {
        BipartiteGraph g = new BipartiteGraph(2,2); 
        Edge e = new Edge();
        g.addEdge(1, 0, e);
        g.addEdge(0, 1, e);
        g.addEdge(1, 1, e);
        System.out.println(g); 
        Ranking algo = new Ranking();
        System.out.println(algo.executeAlgorithm(g));
        RelativeBalance algo2 = new RelativeBalance();
        System.out.println(algo2.executeAlgorithm(g));
    }
}