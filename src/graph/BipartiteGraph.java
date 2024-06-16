package src.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

    public int getN() {
        return offlineVertices.length; 
    }

    public int getM() {
        return onlineVertices.length;
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

    /**
     * Creates a BipartiteGraph that is given by an input file. Each line must contain exactly two integers seperated by whitespace. 
     * First line has to contain the number of offline (n) and online (m) nodes.
     * Next n lines have to contain vertex capacity and weight of all offline nodes.
     * Remaining lines contain all the edges. Each line contains one edge by specifying the endpoints. 
     */
    public static BipartiteGraph createFromFile(String fileName) {
        File input = new File(fileName);
        Scanner scanner;
        try {
            scanner = new Scanner(input);
            String line = scanner.nextLine();
            String[] lineArr = line.split("\\s+");
            int N = Integer.parseInt(lineArr[0]), M = Integer.parseInt(lineArr[1]);
            BipartiteGraph g = new BipartiteGraph(N, M);
            int[] weights = new int[N];
            // read node info
            for(int u = 0; u < N; ++u) {
                line = scanner.nextLine();
                lineArr = line.split("\\s+");
                int b = Integer.parseInt(lineArr[0]), w = Integer.parseInt(lineArr[1]);
                g.setCapacity(u, b);
                weights[u] = w; 
            }
            // read edges
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                lineArr = line.split("\\s+");
                int u = Integer.parseInt(lineArr[0]), v = Integer.parseInt(lineArr[1]);
                Edge e = new Edge(weights[u], 1);
                g.addEdge(u, v, e);
            }
            scanner.close();
            return g; 
        } catch (FileNotFoundException e) {
            System.err.println("File "+fileName+" not found");
        }
        return null;
    }
   // Todo: public static BipartiteGraph createFromFile()
}