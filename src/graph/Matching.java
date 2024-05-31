package src.graph;

import java.util.Arrays;

public class Matching {
    BipartiteGraph g; 
    int[] assignment; 
    int size; 
    double weight; 

    public Matching(BipartiteGraph g) {
        this.g = g; 
        assignment = new int[g.getM()];
        Arrays.fill(assignment, -1);
        size = 0; 
        weight = 0; 
    }

    public void match(int u, int v) {
        assignment[v] = u; 
        ++size; 
        weight += g.getOfflineVertex(u).getEdge(g.getOnlineVertex(v)).getWeight();
    }

    public int getSize() {
        return size; 
    }

    public double getWeight() {
        return weight; 
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(); 
        output.append("Matching of size: "); 
        output.append(size); 
        output.append("; weight: "); 
        output.append(weight); 
        output.append("\n");
        for(int v = 0; v < assignment.length; ++v) {
            if(assignment[v] == -1) continue; 
            output.append("\tOnline Vertex ");
            output.append(v);
            output.append(" is assigned to offline Vertex "); 
            output.append(assignment[v]);
            output.append("\n");
        }
        return output.toString();
    }
    
}
