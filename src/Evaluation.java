package src;

import src.algorithms.OfflineAlgorithm;
import src.algorithms.OnlineAlgorithm;
import src.graph.BipartiteGraph;
import src.graph.Matching;

public class Evaluation {
    BipartiteGraph g; 
    OfflineAlgorithm benchmark; 
    OnlineAlgorithm alg; 
    Matching offline, online; 
    
    public Evaluation(BipartiteGraph g, OfflineAlgorithm benchmark, OnlineAlgorithm alg) {
        this.g = g; 
        this.benchmark = benchmark; 
        this.alg = alg; 
        offline = null; 
        online = null; 
    }

    /**
     * Computes the competitive ratio of the specified deterministic online algorithm compared against the 
     * specified offline benchmark for a given arrival order.
     * @param arrivalOrder: arrival order of the online nodes from input graph g
     * @return              competitive ratio
     */
    public double evaluateDeterministicAlgorithm(int[] arrivalOrder) {
        if(offline == null) offline = benchmark.executeAlgorithm(g);
        if(online == null) online = alg.executeAlgorithm(g, arrivalOrder);
        return 1.0*online.getWeight()/offline.getWeight();
    }

    /**
     * Computes the competitive ratio of the specified online algorithm compared against the 
     * specified offline benchmark when online nodes arrive in a random order
     * @param repetitions: number of times online algorithm is executed with a random arrival order
     * @return             competitive ratio
     */
    public double evaluateAlgorithmRandomArrival(int repetitions) {
        if(offline == null) offline = benchmark.executeAlgorithm(g);
        double averageWeight = 0; 
        for(int i = 0; i < repetitions; ++i) {
            online = alg.executeAlgorithm(g);
            averageWeight = averageWeight/(i+1)*i + online.getWeight()*1.0/(i+1);
        }
        return averageWeight/offline.getWeight();
    }

     /**
     * Computes the competitive ratio of the specified randomized online algorithm compared against the 
     * specified offline benchmark for a given arrival order.
     * @param repetitions: number of times online algorithm is executed
     * @return             competitive ratio
     */
    public double evaluateRandomAlgorithm(int[] arrivalOrder, int repetitions) {
        if(offline == null) offline = benchmark.executeAlgorithm(g);
        double averageWeight = 0; 
        for(int i = 0; i < repetitions; ++i) {
            online = alg.executeAlgorithm(g, arrivalOrder);
            averageWeight = averageWeight/(i+1)*i + online.getWeight()*1.0/(i+1);
        }
        return averageWeight/offline.getWeight();
    }
}
