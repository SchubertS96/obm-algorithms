package src.algorithms;

import src.graph.BipartiteGraph;
import src.graph.Matching;

public abstract class OfflineAlgorithm {
    public abstract Matching executeAlgorithm(BipartiteGraph g);
}
