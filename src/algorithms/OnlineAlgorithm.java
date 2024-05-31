package src.algorithms;

import java.util.Random;
import src.graph.*;

public interface OnlineAlgorithm {
    
    /**
     * Executes the online algorithm on the input graph g with a random arrival order
     * @param g     the input graph
     * @return      matching constructed by the algorithm
     */
    default Matching executeAlgorithm(BipartiteGraph g) {
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
    public Matching executeAlgorithm(BipartiteGraph g, int[] arrivalOrder);
}
