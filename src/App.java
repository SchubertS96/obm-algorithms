package src;

import src.algorithms.OfflineOPT;
import src.graph.*;

class App {
    public static void main(String[] args) {
        BipartiteGraph g = BipartiteGraph.createFromFile("rg.gr");
        System.out.println(g); 
        //Ranking algo = new Ranking();
        //System.out.println(algo.executeAlgorithm(g));
        //RelativeBalance algo2 = new RelativeBalance();
        //System.out.println(algo2.executeAlgorithm(g));
        OfflineOPT opt = new OfflineOPT(); 
        System.out.println(opt.executeAlgorithm(g));
    }
}