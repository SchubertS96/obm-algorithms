package src.algorithms;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import src.graph.*;

public abstract class OnlineAlgorithm {

    Random r; 

    public OnlineAlgorithm() {
        r = new Random();
    }

    public OnlineAlgorithm(long seed) {
        r = new Random(seed);
    }
    
    /**
     * Executes the online algorithm on the input graph g with a random arrival order
     * @param g     the input graph
     * @return      matching constructed by the algorithm
     */
    public Matching executeAlgorithm(BipartiteGraph g) {
        int[] arrivalOrder = new int[g.getM()];
        for(int i = 0; i < arrivalOrder.length; ++i) arrivalOrder[i] = i; 
        // Implementing Fisher–Yates shuffle
        Random r = new Random();
        for (int i = arrivalOrder.length - 1; i > 0; --i) {
            int index = r.nextInt(i + 1);
            int a = arrivalOrder[index];
            arrivalOrder[index] = arrivalOrder[i];
            arrivalOrder[i] = a;
        }
        return executeAlgorithm(g, arrivalOrder);
    }

    /**
     * Executes the online algorithm on the input graph g with a given arrival order
     * @param g     the input graph
     * @return      matching constructed by the algorithm
     */
    public abstract Matching executeAlgorithm(BipartiteGraph g, int[] arrivalOrder);

    Set<Vertex> getAvailableNeighbors(Vertex v, int[] loads, BipartiteGraph g) {
        Set<Vertex> neighbors = v.getNeighbors();
        for (Iterator<Vertex> i = neighbors.iterator(); i.hasNext();) {
            Vertex u = i.next();
            int uid = u.getId();
            if(loads[uid] == g.getCapacity(uid)) {
                i.remove();
            }
        }
        return neighbors;
    }
}
