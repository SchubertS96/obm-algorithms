package src.algorithms;

import java.util.List;
import src.graph.*;

/**
 * Represents the StochasticBalance Algorithm
 */
public class StochasticBalance extends OnlineAlgorithm {

    @Override
    public Matching executeAlgorithm(BipartiteGraph g, int[] arrivalOrder) {
        Matching m = new Matching(g);
        int n = g.getN();
        int[] loads = new int[n];
        double[] stochLoads = new double[n];
        for(int onId : arrivalOrder) {
            Vertex on = g.getOnlineVertex(onId);
            List<Vertex> availableNeighbors = getAvailableNeighbors(on, loads, g);
            if(!availableNeighbors.isEmpty()) {
                Vertex partner = chooseVertex(on, availableNeighbors, stochLoads, g);
                stochLoads[partner.getId()] += partner.getEdge(on).getProbability();
                match(partner, on, loads, m);
            }
        }
        return m; 
    }

    private Vertex chooseVertex(Vertex on, List<Vertex> availableNeighbors, double[] stochLoads, BipartiteGraph g) {
        double max = 0; 
        Vertex partner = null; 
        for(Vertex off : availableNeighbors) {
            // Definition of generalized StochasticBalance (Todo: cite new Approx Paper)
            Edge e = off.getEdge(on);
            int w = e.getWeight();
            double p = e.getProbability();
            double l = stochLoads[off.getId()]/g.getCapacity(off.getId());
            double offer = w*p*(1-f(l)); 
            if(offer > max) {
                max = offer; 
                partner = off; 
            }
        }
        return partner;
    }

    // computes f(x) := e^(x-1); 
    private double f(double x) {
        return Math.exp(x-1);
    }
}
