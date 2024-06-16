package src;

import src.algorithms.OfflineOPT;
import src.graph.*;

class App {
    public static void main(String[] args) {
        BipartiteGraph g = new BipartiteGraph(2,10); 
        Edge e = new Edge();
        g.setCapacity(0, 4);
        g.setCapacity(1, 19);
        for(int v = 0; v < 10; ++v) g.addEdge(0, v, e);
        for(int v = 3; v < 10; ++v) g.addEdge(1, v, e);
        System.out.println(g); 
        //Ranking algo = new Ranking();
        //System.out.println(algo.executeAlgorithm(g));
        //RelativeBalance algo2 = new RelativeBalance();
        //System.out.println(algo2.executeAlgorithm(g));
        OfflineOPT opt = new OfflineOPT(); 
        opt.executeAlgorithm(g);
    }
}