package src.graph;

public class Edge {
    double probability; 
    int weight;

    public Edge(int weight, double probability) {
        this.weight = weight; 
        this.probability = probability; 
    }

    public Edge() {
        this(1, 1); 
    }

    public void setWeight(int weight) {
        this.weight = weight; 
    }

    public int getWeight() {
        return weight; 
    }

    public void setProbability(double probability) {
        this.probability = probability; 
    }

    public double getProbability() {
        return probability; 
    }

    @Override
    public String toString() {
        return "weight: "+weight +" and probability: "+probability;
    }
}