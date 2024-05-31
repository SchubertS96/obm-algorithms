package src.graph;

public class Edge {
    double weight, probability; 

    public Edge(double weight, double probability) {
        this.weight = weight; 
        this.probability = probability; 
    }

    public Edge() {
        this(1.0, 1.0); 
    }

    public void setWeight(double weight) {
        this.weight = weight; 
    }

    public double getWeight() {
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