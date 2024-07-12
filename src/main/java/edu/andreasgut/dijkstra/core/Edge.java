package edu.andreasgut.dijkstra.core;

public class Edge {

    private Node start;
    private Node end;
    private double weight;

    public Edge(Node start, Node end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    @Override
    public String toString(){
        return start + " ---> " + end + " mit Gewicht " + weight;
    }
}
