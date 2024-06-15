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
        for(int onId : arrivalOrder) {
            Vertex on = g.getOnlineVertex(onId);
            Set<Vertex> availableNeighbors = getAvailableNeighbors(on, loads, g);
            if(!availableNeighbors.isEmpty()) {
                Vertex partner = chooseVertex(on, availableNeighbors, loads, g);
                match(partner, on, loads, m);
            }
        }
        return m; 
    }

    private Vertex chooseVertex(Vertex on, Set<Vertex> availableNeighbors, int[] loads, BipartiteGraph g) {
        double max = 0; 
        Vertex partner = null; 
        for(Vertex off : availableNeighbors) {
            // Definition of RelativeBalance https://drops.dagstuhl.de/storage/00lipics/lipics-vol207-approx-random2021/LIPIcs.APPROX-RANDOM.2021.2/LIPIcs.APPROX-RANDOM.2021.2.pdf
            int b = g.getCapacity(off.getId());
            double relLoad = 1.0*loads[off.getId()]/b;
            int w = on.getEdge(off).getWeight();
            double offer = w*(1-f(b, relLoad));
            if(offer > max) {
                max = offer; 
                partner = off; 
            }
        }
        return partner;
    }

    // computes f(b, l) := (1+1/b)^(b*(l-1)); 
    private double f(int b, double l) {
        return Math.pow(1+1.0/b, b*(l-1));
    }
}

