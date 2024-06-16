package src;

import src.algorithms.OfflineOPT;
import src.graph.*;

class App {
    public static void main(String[] args) {
        BipartiteGraph g = new BipartiteGraph(2,10); 
        Edge e1 = new Edge(2,1);
        Edge e2 = new Edge(4,1);
        g.setCapacity(0, 4);
        g.setCapacity(1, 19);
        for(int v = 0; v < 10; ++v) g.addEdge(0, v, e1);
        for(int v = 3; v < 10; ++v) g.addEdge(1, v, e2);
        System.out.println(g); 
        //Ranking algo = new Ranking();
        //System.out.println(algo.executeAlgorithm(g));
        //RelativeBalance algo2 = new RelativeBalance();
        //System.out.println(algo2.executeAlgorithm(g));
        OfflineOPT opt = new OfflineOPT(); 
        System.out.println(opt.executeAlgorithm(g));
    }
}