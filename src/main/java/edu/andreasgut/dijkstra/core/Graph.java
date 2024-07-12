package edu.andreasgut.dijkstra.core;

import java.util.*;

public class Graph {

    private LinkedList<Node> nodes = new LinkedList<>();
    private LinkedList<Edge> edges = new LinkedList<>();


    public Graph(){

    }

    public Graph(int[][] matrix) {
        for (int nodeNumber = 0; nodeNumber < matrix.length; nodeNumber++){
            Node node = new Node(String.valueOf(nodeNumber));
            nodes.add(node);
        }
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                if (matrix[row][column] != 0){
                    Node currentNode = getNodeByName(String.valueOf(row));
                    connectNodes(currentNode, getNodeByName(String.valueOf(column)), matrix[row][column]);
                }
            }
        }
    }

    public Node getNodeByName(String name){
        for (Node node : nodes){
            if (node.getName().equals(name)){
                return node;
            }
        }
        return null;
    }

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }


    public LinkedList<Node> getNodes() {
        return nodes;
    }

    public void connectNodes(Node startNode, Node endNode, double weight){
        edges.add(new Edge(startNode, endNode, weight));
        edges.add(new Edge(endNode, startNode, weight));
    }

    public LinkedList<Edge> getEdgesFromNode(Node node){
        LinkedList<Edge> edgesList = new LinkedList<>();
        for (Edge e : edges){
            if (e.getStart().equals(node)){
                edgesList.add(e);
            }
        }
        return edgesList;
    }

    public LinkedList<Edge> getEdgeToNode(Node node){
        LinkedList<Edge> edgesList = new LinkedList<>();
        for (Edge e : edges){
            if (e.getEnd().equals(node)){
                edgesList.add(e);
            }
        }
        return edgesList;
    }




    @Override
    public String toString(){
        String graphAsString = "";

        for (Node node : nodes){
            graphAsString += "Knoten: " + node.toString();
            graphAsString += "\n";
            for (Edge edge : getEdgesFromNode(node)){
                graphAsString += edge.toString();
                graphAsString += "\n";
            }
            graphAsString += "\n";


        }

        return graphAsString;
    }

    public LinkedList<Node> getShortestPathFromTo(Node startNode, Node goalNode){
        LinkedList<Node> nodesInQueue = new LinkedList<>();
        LinkedList<Node> visitedNodes = new LinkedList<>();
        Map<Node, Double> distanceToNodesMap = new HashMap<>();
        Map<Node, Node> previousNodeMap = new HashMap<>();
        for (Node node : nodes){
            distanceToNodesMap.put(node, Double.MAX_VALUE);
            previousNodeMap.put(node, null);
        }

        nodesInQueue.add(startNode);
        distanceToNodesMap.put(startNode, 0.0);

        while (nodesInQueue.size() != 0){
            Node currentNode = getNodeWithSmallestDistance(distanceToNodesMap, nodesInQueue);
            System.out.println("Current Node " + currentNode);
            updateDistancesInDistanceMap(currentNode, distanceToNodesMap, previousNodeMap);
            visitedNodes.add(currentNode);
            LinkedList<Node> firstTimeVisitedNodes = getFirstTimeVisitedNodes(currentNode, visitedNodes);
            nodesInQueue.addAll(firstTimeVisitedNodes);
            nodesInQueue.remove(currentNode);
        }

        LinkedList<Node> shortestPath = new LinkedList<>();
        Node previousNode = previousNodeMap.get(goalNode);
        shortestPath.add(0, goalNode);
        while (previousNode != null){
            shortestPath.add(0, previousNode);
            previousNode = previousNodeMap.get(previousNode);
        }

        return shortestPath;

    }

    private Node getNodeWithSmallestDistance(Map<Node, Double> distanceMap, LinkedList<Node> nodesInQueue){
        Node nodeWithSmallestDistance = nodesInQueue.get(0);
        for (Node node : nodesInQueue){
            if (distanceMap.get(node) < distanceMap.get(nodeWithSmallestDistance)){
                nodeWithSmallestDistance = node;
            }
        }
        return nodeWithSmallestDistance;
    }

    private void updateDistancesInDistanceMap(Node currentNode, Map<Node, Double> distanceToNodesMap, Map<Node, Node> previousNodeMap){
        for (Edge edge : getEdgesFromNode(currentNode)){
            double currentWeight = distanceToNodesMap.get(edge.getEnd());
            double newPossibleWeight = distanceToNodesMap.get(currentNode) + edge.getWeight();
            if (newPossibleWeight < currentWeight){
                distanceToNodesMap.put(edge.getEnd(), newPossibleWeight);
                previousNodeMap.put(edge.getEnd(), currentNode);
            }
        }

    }

    private LinkedList<Node> getFirstTimeVisitedNodes(Node currentNode, LinkedList<Node> visitedNodes){
        LinkedList<Node> firstTimeVisitedNodes = new LinkedList<>();
        for (Edge edge : getEdgesFromNode(currentNode)){
            if (!visitedNodes.contains(edge.getEnd())){
                firstTimeVisitedNodes.add(edge.getEnd());
            }
        }
        return firstTimeVisitedNodes;
    }



}
