package edu.andreasgut.dijkstra.core;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        nodeA.addConnection(nodeB, 9);
        nodeB.addConnection(nodeC, 10);
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        System.out.println(graph);
        LinkedList<Node> path = graph.getShortestPathFromTo(nodeA, nodeC);
        System.out.println(path.size());
        for (Node node : path){
            System.out.println(node);
        }
    }
}
