package src.algorithms;

import java.util.Set;

import src.graph.BipartiteGraph;
import src.graph.Matching;
import src.graph.Vertex;

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
        for(int v : arrivalOrder) {
            Set<Vertex> availableNeighbors = getAvailableNeighbors(g.getOnlineVertex(v), loads, g);
            if(!availableNeighbors.isEmpty()) {
                Vertex partner = chooseVertex(g.getOnlineVertex(v), availableNeighbors, stochLoads, g);
                stochLoads[partner.getId()] += partner.getEdge(g.getOnlineVertex(v)).getProbability();
                //match only succeeds with the probability of the given edge
                if(r.nextDouble() < g.getOnlineVertex(v).getEdge(partner).getProbability()) {
                    m.match(partner.getId(), v);
                    ++loads[partner.getId()];
                }
            }
        }
        return m; 
    }

    private Vertex chooseVertex(Vertex v, Set<Vertex> availableNeighbors, double[] stochLoads, BipartiteGraph g) {
        double max = 0; 
        Vertex partner = null; 
        for(Vertex u : availableNeighbors) {
            // Definition of generalized StochasticBalance (Todo: cite new Approx Paper)
            double weight = v.getEdge(u).getWeight();
            double probability = v.getEdge(u).getProbability();
            double l = stochLoads[u.getId()]/g.getCapacity(u.getId());
            double offer = weight*probability*(1-f(l)); 
            if(offer > max) {
                max = offer; 
                partner = u; 
            }
        }
        return partner;
    }

    // computes f(x) := e^(x-1); 
    private double f(double x) {
        return Math.exp(x-1);
    }
}
