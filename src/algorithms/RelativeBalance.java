package src.algorithms;

import java.util.*;
import src.graph.*;

/**
 * Represents the RelativeBalance Algorithm
 */
public class RelativeBalance extends OnlineAlgorithm {

    @Override
    public Matching executeAlgorithm(BipartiteGraph g, int[] arrivalOrder) {
        Matching m = new Matching(g);
        int n = g.getN();
        int[] loads = new int[n];
        for(int v : arrivalOrder) {
            Set<Vertex> availableNeighbors = getAvailableNeighbors(g.getOnlineVertex(v), loads, g);
            if(!availableNeighbors.isEmpty()) {
                Vertex partner = chooseVertex(availableNeighbors, loads, g);
                //match only succeeds with the probability of the given edge
                if(r.nextDouble() < g.getOnlineVertex(v).getEdge(partner).getProbability()) {
                    m.match(partner.getId(), v);
                    ++loads[partner.getId()];
                }
            }
        }
        return m; 
    }

    private Vertex chooseVertex(Set<Vertex> availableNeighbors, int[] loads, BipartiteGraph g) {
        double min = 1; 
        Vertex partner = null; 
        for(Vertex u : availableNeighbors) {
            // Definition of RelativeBalance https://drops.dagstuhl.de/storage/00lipics/lipics-vol207-approx-random2021/LIPIcs.APPROX-RANDOM.2021.2/LIPIcs.APPROX-RANDOM.2021.2.pdf
            // Todo: add vertex weights for consideration
            double relLoad = 1.0*loads[u.getId()]/g.getCapacity(u.getId());
            if(relLoad < min) {
                min = relLoad; 
                partner = u; 
            }
        }
        return partner;
    }
}

