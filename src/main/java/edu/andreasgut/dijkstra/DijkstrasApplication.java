package edu.andreasgut.dijkstra;

import edu.andreasgut.dijkstra.core.Edge;
import edu.andreasgut.dijkstra.core.Graph;
import edu.andreasgut.dijkstra.core.Node;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class DijkstrasApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Graph graph = new Graph();

        //TODO: Definiere, ob der Minimale Spannbaum (1) oder der Kürzeste Weg (2) markiert werden soll
        int modus = 2;

        //TODO: Definiere hier alle Knoten, die du im Graph haben möchtest
        Node nodeStart = new Node("Start");
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");
        Node nodeZiel = new Node("Ziel");

        //TODO: Füge hier alle Knoten zum Graph hinzu
        graph.addNode(nodeStart);
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);
        graph.addNode(nodeZiel);

        //TODO: Erstelle hier alle Kanten, die du zwischen zwei Knoten einfügen möchtest
        graph.connectNodes(nodeStart, nodeA, 4);
        graph.connectNodes(nodeStart, nodeC, 7);
        graph.connectNodes(nodeStart, nodeE, 4);
        graph.connectNodes(nodeA, nodeB, 3);
        graph.connectNodes(nodeA, nodeC, 2);
        graph.connectNodes(nodeB, nodeD, 1);
        graph.connectNodes(nodeC, nodeD, 2);
        graph.connectNodes(nodeC, nodeE, 9);
        graph.connectNodes(nodeD, nodeF, 4);
        graph.connectNodes(nodeD, nodeZiel, 6);
        graph.connectNodes(nodeE, nodeF, 9);
        graph.connectNodes(nodeF, nodeZiel, 5);

        setupGUI(stage, graph, nodeStart, nodeZiel, modus);
    }



    private void setupGUI(Stage stage, Graph graph, Node startNode, Node goalNode, int modus) {
        LinkedList<Node> shortestPath = graph.getShortestPathFromTo(startNode, goalNode);
        LinkedList<Edge> minimalSpanningTree = graph.getMinimalSpanningTree();


        stage.setTitle("Dijkstras Algorithmus");

        int canvasWidth = 800;
        int canvasHeight = 600;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane pane = new Pane();
        pane.getChildren().add(canvas);


        List<GuiNode> guiNodes = new ArrayList<>();
        Map<Node, GuiNode> nodeMap = new HashMap<>();

        List<Point2D> points = distributePointsOnCircle(Math.min(canvasHeight, canvasWidth)/2.0*0.85, graph.getNodes().size(), canvasWidth, canvasHeight);


        int counter = 0;
        for (Node node : graph.getNodes()){
            Point2D position = points.get(counter);

            GuiNode guiNode = new GuiNode(node.getName(), position.getX(), position.getY());
            nodeMap.put(node, guiNode);
            guiNodes.add(guiNode);
            counter++;

        }

        List<GuiEdge> guiEdges = new ArrayList<>();

        for (Node node : graph.getNodes()){
            for (Edge edge : graph.getEdgesFromNode(node)){
                guiEdges.add(new GuiEdge(nodeMap.get(edge.getStart()), nodeMap.get(edge.getEnd()), edge.getWeight()));
            }
        }


        drawGraph(gc, guiNodes, guiEdges);
        if (modus == 1){
            highlightPath(gc, shortestPath, nodeMap);
        }
        if (modus == 2) {
            highlightMinimalSpanningTree(gc, minimalSpanningTree, nodeMap);
        }




        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


    private List<Point2D> distributePointsOnCircle(double radius, int numberOfPoints, int canvasWidth, int canvasHeight) {
        List<Point2D> points = new ArrayList<>();
        double angleStep = 2 * Math.PI / numberOfPoints;
        int offsetX = canvasWidth / 2;
        int offsetY = canvasHeight /2;

        for (int i = 0; i < numberOfPoints; i++) {
            double angle = i * angleStep + Math.PI;
            double x = radius * Math.cos(angle) + offsetX;
            double y = radius * Math.sin(angle) + offsetY;
            points.add(new Point2D(x, y));
        }

        return points;
    }

    private void highlightPath(GraphicsContext gc, LinkedList<Node> path, Map<Node, GuiNode> map){
        for (int i = 0; i < path.size()-1; i++) {
            GuiNode n1 = map.get(path.get(i));
            GuiNode n2 = map.get(path.get(i+1));
            gc.setStroke(Color.LIGHTGREEN);
            gc.setLineWidth(3);
            gc.strokeLine(n1.x, n1.y, n2.x, n2.y);

        }
    }

    private void highlightMinimalSpanningTree(GraphicsContext gc, LinkedList<Edge> minimalSpanningTree, Map<Node, GuiNode> map){
        for (Edge e : minimalSpanningTree){
            GuiNode n1 = map.get(e.getStart());
            GuiNode n2 = map.get(e.getEnd());
            gc.setStroke(Color.ORANGE);
            gc.setLineWidth(3);
            gc.strokeLine(n1.x, n1.y, n2.x, n2.y);
        }
    }

    private void drawGraph(GraphicsContext gc, List<GuiNode> nodes, List<GuiEdge> edges) {
        // Draw edges
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        for (GuiEdge edge : edges) {
            gc.strokeLine(edge.start.x, edge.start.y, edge.end.x, edge.end.y);
            gc.fillText(String.valueOf(edge.weight), (edge.start.x + edge.end.x) / 2, ((edge.start.y + edge.end.y) / 2)-5);
        }

        // Draw nodes
        gc.setFill(Color.RED);
        for (GuiNode node : nodes) {
            gc.fillOval(node.x - 10, node.y - 10, 20, 20);
            gc.fillText(node.name, node.x - 10, node.y - 20);
        }
    }

    static class GuiNode {
        String name;
        double x, y;

        GuiNode(String name, double x, double y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }
    }

    static class GuiEdge {
        GuiNode start, end;
        double weight;

        GuiEdge(GuiNode start, GuiNode end, double weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}