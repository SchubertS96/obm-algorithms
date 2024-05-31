package src.algorithms;

import java.util.*;
import src.graph.*;

/**
 * Represents the Ranking Algorithm and its generalization Perturbed-Greedy
 */
public class Ranking implements OnlineAlgorithm {

    Random r; 

    public Ranking() {
        r = new Random();
    }

    public Ranking(long seed) {
        r = new Random(seed);
    }

    @Override
    public Matching executeAlgorithm(BipartiteGraph g, int[] arrivalOrder) {
        Matching m = new Matching(g);
        int n = g.getN();
        double[] ranks = r.doubles(n).toArray(); 
        int[] loads = new int[n];
        for(int v : arrivalOrder) {
            Set<Vertex> availableNeighbors = getAvailableNeighbors(g.getOnlineVertex(v), loads, g);
            if(!availableNeighbors.isEmpty()) {
                Vertex partner = chooseVertex(g.getOnlineVertex(v), availableNeighbors, ranks);
                m.match(partner.getId(), v);
                ++loads[partner.getId()];
            }
        }
        return m; 
    }

    private Set<Vertex> getAvailableNeighbors(Vertex v, int[] loads, BipartiteGraph g) {
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

    private Vertex chooseVertex(Vertex v, Set<Vertex> availableNeighbors, double[] ranks) {
        double max = 0; 
        Vertex partner = null; 
        for(Vertex u : availableNeighbors) {
            // Definition of Perturbed-Greedy https://drops.dagstuhl.de/storage/00lipics/lipics-vol207-approx-random2021/LIPIcs.APPROX-RANDOM.2021.2/LIPIcs.APPROX-RANDOM.2021.2.pdf
            double offer = v.getEdge(u).getWeight() * (1-g(ranks[v.getId()]));
            if(offer > max) {
                max = offer; 
                partner = u; 
            }
        }
        return partner;
    }

    // computes g(x) := e^(x-1); 
    private double g(double x) {
        return Math.exp(x-1);
    }
}
