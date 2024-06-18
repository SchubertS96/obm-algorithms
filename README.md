# (Generalized) Online Bipartite Matching

This repository contains a Java implementation of the online algorithms analyzed in my research papers [[1](https://drops.dagstuhl.de/entities/document/10.4230/LIPIcs.APPROX/RANDOM.2021.2)][yet to publish] and an optimal offline algorithm as a benchmark. 

# Problem Statement

Online bipartite matching is a fundamental problem in computer science and is especially relevant for modeling online advertisements. The input is a bipartite graph G=(U u V, E), where the vertex set U is known in advance, and the other bipartition V arrives sequentially over time. Whenever a new vertex v arrives, its incident edges are revealed. An online algorithm has to immediately assign v to an unmatched neighbor (i.e., before the arrival of the next vertex from V) or leave v unmatched forever. Prior matching decisions cannot be reverted later. The goal is to match as many vertices from V as possible. 

Over the years, numerous generalizations have been considered. In this implementation, I focus on the capacitated and vertex-weighted extensions. The capacitated generalization is known as the online b-matching problem. There, offline vertices may be matched multiple times. Each offline vertex u is assigned a vertex capacity b_u, which upper bounds the number of matching partners u can accommodate. The goal is still to match as many vertices from V as possible. 
In the vertex-weighted extension, each offline vertex u is further assigned a weight w_u. Intuitively, the weight w_u models how valuable a matching edge incident to u is. The goal is then to construct a matching that maximizes the total value.

In another setting, known as online matching with stochastic rewards, the problem is further generalized by assigning a probability to each edge. Whenever an online algorithm assigns an online vertex u to an offline vertex v, the match only succeeds with the probability that is assigned to the connecting edge. The objective is to maximize the expected size/weight of the matching. Note that the previous settings are identical to the case where all edges have probability 1. 

# Algorithms

Online algorithms: 
- RelativeBalance: A simple and intuitive deterministic algorithm proposed in my [paper](https://drops.dagstuhl.de/entities/document/10.4230/LIPIcs.APPROX/RANDOM.2021.2). In the unweighted case, an incoming online vertex is assigned to the neighbor that currently utilizes the smallest portion of its capacity. The algorithm can be generalized to the vertex-weighted by greedily choosing the neighbor u that maximizes w_u * f_u, where f_u is a factor that depends on the fraction of used capacity. 
- Ranking: A famous randomized algorithm introduced by [Karp et al.](https://dl.acm.org/doi/pdf/10.1145/100216.100262). In the unweighted case, a random permutation (ranking) of the offline vertices is chosen at the beginning. Incoming online vertices are then assigned to the highest-ranked neighbor with remaining capacity. Ranking can also be generalized to the vertex-weighted setting, where it is then known as [PerturbedGreedy](https://epubs.siam.org/doi/pdf/10.1137/1.9781611973082.95). 
- StochasticBalance: A simple deterministic algorithm for the online matching problem with stochastic rewards. In the case of equal probabilities (i.e., where even edge has the same probability p assigned to it), incoming vertices are assigned to the neighbor with remaining capacity that has the least amount of vertices assigned (successfully or unsuccessfully )to it so far. It can also be generalized to the case of unequal probabilities; see my latest paper. 

Offline Algorithms: 
- OfflineOPT: This algorithm computes the maximum weight matching in the given bipartite graph. This is done by computing a max-cost max-flow in a flow network based on the input graph. A source s is added, which has edges of cost 0 and capacity b_u to each offline vertex u. The edges from the bipartite graph carry over, where each edge has a capacity of one and a cost equal to the weight of the edge. Finally, a sink t is added so that each online vertex has one edge with a capacity of one and a cost of zero to t. Dinc's algorithm is used first to compute the max-cost max-flow. The max-flow is then augmented incrementally by cycle-cancelling. 

# Evaluation

Online algorithms are evaluated/analyzed by comparing their output to the output of the optimal offline algorithm. More precisely, the (expected) weight/size of the matching constructed by an online algorithm is divided by the weight/size of the matching constructed by the optimal offline algorithm. The competitive ratio of an online algorithm is then defined as the infimum over all input graphs of said fraction. In the case of online matching with stochastic rewards, different benchmarks are used for comparison, but they are not implemented here. 

The provided Python scripts can generate three different types of graphs. 
- Random graphs (Gilbert graph): The script asks for the number of nodes and a probability p. A graph is then constructed where an edge between any pair of offline and online vertex is added independently with probability p. Upper bounds for vertex weights and capacities can also be specified. For each offline vertex, the weight and capacity are then uniformly drawn in the range from 1 to the specified upper bound (inclusive). 
- Balance graphs: These graphs refer to the worst-case input for the Balance algorithm mentioned [here](https://www.sciencedirect.com/science/article/pii/S0304397599001401). Note that the graphs have (b+1)^(b+1) nodes, so the graphs get huge fast. 
- Triangle graphs: These graphs constitute the worst-case input for the Ranking algorithm. There are n offline vertices, all with the same capacity b. There are n groups of online vertices. Each group consists of b identical vertices. The vertices belonging to group i, 1 ≤ i ≤ n, are adjacent to all servers i through n. 

# Improvement

- Implement a benchmark for online algorithms for the problem with stochastic rewards.
- Implement a more efficient algorithm to compute maximum weight matching in a bipartite graph (see, e.g. [here](https://en.wikipedia.org/wiki/Minimum-cost_flow_problem#Solutions)). 
- Add a visualization.

# License

This project is licensed under the MIT license. Refer to the [LICENSE](./LICENSE) file for more information.
