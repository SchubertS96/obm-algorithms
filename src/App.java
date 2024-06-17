package src;

import src.algorithms.*;
import src.graph.*;

class App {
    public static void main(String[] args) {
        BipartiteGraph g = BipartiteGraph.createFromFile("rg.gr");
        OnlineAlgorithm Rank = new Ranking(); 
        OfflineAlgorithm Opt = new OfflineOPT(); 
        Evaluation eval = new Evaluation(g, Opt, Rank);
        int[] arrivalOrder = new int[g.getM()];
        for(int i = 0; i < g.getM(); ++i) arrivalOrder[i] = i; 
        System.out.println("For given arrival: c="+eval.evaluateRandomAlgorithm(arrivalOrder, 1000));
    }
}