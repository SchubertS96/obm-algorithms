package src.graph;

public class BipartiteGraph {
    Vertex[] offlineVertices, onlineVertices;
    int[] capacities;  
    
    public BipartiteGraph(int n, int m) {
        offlineVertices = new Vertex[n]; 
        capacities = new int[n];  
        for(int i = 0; i < n; ++i) {
            offlineVertices[i] = new Vertex(i); 
            capacities[i] = 1; 
        }
        onlineVertices = new Vertex[m];
        for(int i = 0; i < m; ++i) {
            onlineVertices[i] = new Vertex(i); 
        }
    }

    public void addEdge(int u, int v, Edge e) {
        offlineVertices[u].addNeighbor(onlineVertices[v], e);
        onlineVertices[v].addNeighbor(offlineVertices[u], e);
    }

    public void deleteEdge(int u, int v) {
        offlineVertices[u].deleteNeighbor(onlineVertices[v]);
        onlineVertices[v].deleteNeighbor(offlineVertices[u]);
    }

    public void setCapacity(int u, int capacity) {
        capacities[u] = capacity;
    }

    public int getCapacity(int u) {
        return capacities[u];
    }

    public Vertex getOfflineVertex(int u) {
        return offlineVertices[u];
    }

    public Vertex getOnlineVertex(int v) {
        return onlineVertices[v];
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(); 
        for(int u = 0; u < offlineVertices.length; ++u) {
            output.append("Offline Vertex "); 
            output.append(u); 
            output.append(" with capacity ");
            output.append(capacities[u]);
            output.append(" has Edges to: \n"); 
            for(Vertex v : offlineVertices[u].getNeighbors()) {
                Edge e = offlineVertices[u].getEdge(v);
                output.append("\t Online vertex "); 
                output.append(v.getId());
                output.append(" with ");
                output.append(e);
                output.append("\n");
            }
        }
        return output.toString();
    }

   // Todo: public static BipartiteGraph createFromFile()
}