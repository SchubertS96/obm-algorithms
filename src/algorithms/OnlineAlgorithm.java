package src.algorithms;

import java.util.*;
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
        for (int i = arrivalOrder.length - 1; i > 0; --i) {
            int index = r.nextInt(i + 1);
            int temp = arrivalOrder[index];
            arrivalOrder[index] = arrivalOrder[i];
            arrivalOrder[i] = temp;
        }
        return executeAlgorithm(g, arrivalOrder);
    }

    /**
     * Executes the online algorithm on the input graph g with a given arrival order
     * @param g     the input graph
     * @return      matching constructed by the algorithm
     */
    public abstract Matching executeAlgorithm(BipartiteGraph g, int[] arrivalOrder);

    List<Vertex> getAvailableNeighbors(Vertex on, int[] loads, BipartiteGraph g) {
        List<Vertex> neighbors = new LinkedList<>(on.getNeighbors());
        for (Iterator<Vertex> i = neighbors.iterator(); i.hasNext();) {
            Vertex off = i.next();
            int offId = off.getId();
            if(loads[offId] == g.getCapacity(offId)) {
                i.remove();
            }
        }
        return neighbors;
    }

    void match(Vertex off, Vertex on, int[] loads, Matching m) {
        //match only succeeds with the probability of the given edge
        if(r.nextDouble() < off.getEdge(on).getProbability()) {
            m.match(off.getId(), on.getId());
            ++loads[off.getId()];
        }
    }
}
