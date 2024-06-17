package src.algorithms;

import java.util.*;
import src.graph.BipartiteGraph;
import src.graph.Matching;

/**
 * Computes the offline optimal solution by computing the max cost max flow using a cycle-cancelling algorithm.
 * Note that this is only correct for vertex-weighted input graphs.
 */
public class OfflineOPT extends OfflineAlgorithm {
    @Override
    public Matching executeAlgorithm(BipartiteGraph g) {
        ResidualGraph res = new ResidualGraph(g.getN()+g.getM()+2);
        res.buildGraph(g);
        res.computeDincMaxFlow();
        ResidualGraph.Edge[] parent = new ResidualGraph.Edge[res.vertices.length];
        while(true) {
            int node = res.findPositiveCycle(parent);
            if(node == -1) break;
            // identify max flow that can be sent through the cycle
            int possibleFlow = Integer.MAX_VALUE;
            int cur = node; 
            do {
                ResidualGraph.Edge e = parent[cur];
                possibleFlow = Math.min(possibleFlow, e.capacity-e.flow);
                cur = e.from;
            } while(cur != node);
            // send flow through cycle
            do { 
                ResidualGraph.Edge e = parent[cur];
                e.flow += possibleFlow;
                e.reverse.flow -= possibleFlow;
                //res.cost += possibleFlow*e.cost;
                cur = e.from;
            } while (cur != node);
        }
        return constructMatching(g, res);
    }

    /**
     * Creates a matching for the original bipartite graph after computing the max cost max flow with a residual graph
     */
    private Matching constructMatching(BipartiteGraph g, ResidualGraph res) {
        Matching m = new Matching(g);
        for(int u = 0; u < g.getN(); ++u) {
            for(ResidualGraph.Edge e : res.vertices[u].adj) {
                if(e.flow > 0) {
                    m.match(u, e.to-g.getN());
                }
            }
        }
        return m; 
    }

    class ResidualGraph {
        class Edge {
            int capacity, flow, cost; 
            int from, to; 

            Edge reverse; 

            Edge(int from, int to, int capacity, int flow, int cost) {
                this.from = from; 
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
        List<Edge> edges; 
        //int flow, cost 
        int s, t; 
        int[] levels; 

        public ResidualGraph(int V) {
            vertices = new Vertex[V];
            edges = new ArrayList<>();
            for(int i = 0; i < V; ++i) vertices[i] = new Vertex();
            levels = new int[V];
            //flow = 0; cost = 0; 
            s = V-2; t = V-1;
        }

        /**
         * Constructs the initial flow network used to compute the max cost max flow.
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
                    int weight = u.getEdge(g.getOnlineVertex(v)).getWeight();
                    addEdge(u.getId(), N+v, 1, weight);
                }
            }
        }

        /**
         * Adds a directed edge to the residual graph. 
         */
        void addEdge(int from, int to, int capacity, int cost) {
            Edge one = new Edge(from, to, capacity,0, cost);
            Edge two = new Edge(to, from, 0, 0, -cost);
            one.reverse = two; 
            two.reverse = one; 
            vertices[from].adj.add(one);
            vertices[to].adj.add(two);
            edges.add(one);
            edges.add(two); 
        }

        /**
         * Computes the maximum flow in the flow network using Dinc's Algorithm.
         * If the residual graph was built from a bipartite graph, this essentially computes a 
         * maximum cardinality matching.
         */
        void computeDincMaxFlow() {
            while(bfs()) {
                int[] start = new int[vertices.length];

                while(true) {
                    int addedFlow = sendFlow(s, Integer.MAX_VALUE, start);
                    if(addedFlow == 0) break; 
                    //flow += addedFlow; 
                }
            }
        }

        /**
         * Helper method used in Dinc's max flow algorithm. As much flow as possible is sent from "from" to t, when from receives "inFlow".
         * start contains the indices of the edges that are yet to be discovered in each adjacency list. 
         */
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
                        //cost += addFlow*e.cost;
                        return addFlow;
                    }
                }
            }

            return 0; 
        }

        /**
         * Starts a BFS from the source s and assigns levels to each.
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

        /**
         * Identifies a positive cycle (w.r.t. the costs) if there is one in the residual graph. 
         * Similar to the Bellman-Ford-Algorithm from the sink t, but here the longest path is identified.
         * @param parent:   Array for storing the edge from parent, i.e. the predecessor, on the shortest path from t to each node.
         * @return          -1 if there is no positive cycle, otherwise node id of a node on the cycle is returned.
         */
        int findPositiveCycle(Edge[] parent) {
            int V = vertices.length;
            int[] dist = new int[V];
            int update = -1; 
            Arrays.fill(dist, Integer.MIN_VALUE);
            dist[t] = 0; 
            for(int i = 0; i < V; ++i) {
                update = -1; 
                for(Edge e : edges) {
                    if(e.flow < e.capacity && dist[e.from] > Integer.MIN_VALUE && dist[e.from] + e.cost > dist[e.to]) {
                        dist[e.to] = dist[e.from] + e.cost;
                        parent[e.to] = e;
                        update = e.to;
                    }
                }
            }
            if(update == -1) return -1; 
            // make sure that node is on cycle
            for(int i = 0; i < V; ++i) {
                update = parent[update].from;
            }
            return update; 
        }
    }
}
