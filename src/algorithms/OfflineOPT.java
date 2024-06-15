package src.algorithms;

import java.util.*;
import src.graph.BipartiteGraph;
import src.graph.Matching;

/**
 * Computes the offline optimal solution by computing the max cost max flow 
 * Note that this is only correct for vertex-weighted input graphs
 */
public class OfflineOPT extends OfflineAlgorithm {
    @Override
    public Matching executeAlgorithm(BipartiteGraph g) {
        ResidualGraph res = new ResidualGraph(g.getN()+g.getM()+2);
        res.buildGraph(g);
        res.computeDincMaxFlow();
        return new Matching(g);
    }

    class ResidualGraph {
        class Edge {
            int capacity, flow, cost; 
            int to; 

            Edge reverse; 

            Edge(int to, int capacity, int flow, int cost) {
                this.to = to; 
                this.capacity = capacity; 
                this.flow = flow; 
                this.cost = cost; 
                reverse = null; 
            }
        }

        class Vertex {
            List<Edge> adj; 

            public Vertex() {
                this.adj = new ArrayList<>();
            }
        }

        Vertex[] vertices;
        int flow, cost; 
        int s, t; 
        int[] levels; 

        public ResidualGraph(int V) {
            vertices = new Vertex[V];
            for(int i = 0; i < V; ++i) vertices[i] = new Vertex();
            levels = new int[V];
            flow = 0; cost = 0; s = V-2; t = V-1;
        }

        /**
         * Constructs the initial flow network used to compute the max cost max vlow
         * @param g     the input bipartite graph
         */
        void buildGraph(BipartiteGraph g) {
            int N = g.getN(), M = g.getM(); 
            for(int u = 0; u < N; ++u) {
                vertices[u] = new Vertex(); 
                addEdge(s, u, g.getCapacity(u), 0);
            }
            for(int v = 0; v < M; ++v) {
                addEdge(N+v, t, 1, 0);
                for(src.graph.Vertex u : g.getOnlineVertex(v).getNeighbors()) {
                    int weight = u.getEdge(g.getOnlineVertex(N+v)).getWeight();
                    addEdge(u.getId(), N+v, 1, weight);
                }
            }
        }

        void addEdge(int from, int to, int capacity, int cost) {
            Edge one = new Edge(to, capacity,0, cost);
            Edge two = new Edge(from, 0, 0, cost);
            one.reverse = two; 
            two.reverse = one; 
            vertices[from].adj.add(one);
            vertices[to].adj.add(two);
        }

        void computeDincMaxFlow() {
            while(bfs()) {
                int[] start = new int[vertices.length];

                while(true) {
                    int addedFlow = sendFlow(s, Integer.MAX_VALUE, start);
                    if(addedFlow == 0) break; 
                    flow += addedFlow; 
                }
            }
        }

        int sendFlow(int from, int inFlow, int[] start) {
            if(from == t) return inFlow; 

            while(start[from] < vertices[from].adj.size()) {
                Edge e = vertices[from].adj.get(start[from]++);
                if(levels[e.to] == levels[from]+1 && e.flow < e.capacity) {
                    int tryFlow = Math.min(inFlow, e.capacity-e.flow);
                    int addFlow = sendFlow(e.to, tryFlow, start);
                    if(addFlow > 0) {
                        e.flow += addFlow;
                        e.reverse.flow -= addFlow;
                        return addFlow;
                    }
                }
            }

            return 0; 
        }

        /**
         * Starts a BFS from the source s and assigns levels to each
         * @return  true if there is a path from s to t
         */
        boolean bfs() {
            for (int i = 0; i < vertices.length; i++) {
                levels[i] = -1;
            }
            levels[s] = 0;

            Queue<Integer> q = new LinkedList<>(); 
            q.add(s);

            while(!q.isEmpty()) {
                int cur = q.poll(); 
                for(Edge e : vertices[cur].adj) {
                    int neighbor = e.to;
                    if(levels[neighbor] < 0 && e.flow < e.capacity) {
                        levels[neighbor] = levels[cur]+1;
                        q.add(neighbor);
                    }
                }
            }
            return levels[t] > 0; 
        }
    }
}
