package edu.andreasgut.dijkstra.core;

import java.util.*;

public class Node {

    private String name;
    private LinkedList<Edge> edges = new LinkedList<>();

    public void addConnection(Node node, double weight) {
        Edge edge = new Edge(this, node, weight);
        edges.add(edge);
        node.getEdges().add(edge);
    }

    public Node(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString(){
        return name;
    }



}
