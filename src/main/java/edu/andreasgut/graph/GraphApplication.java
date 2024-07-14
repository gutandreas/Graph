package edu.andreasgut.graph;

import edu.andreasgut.graph.core.Edge;
import edu.andreasgut.graph.core.Graph;
import edu.andreasgut.graph.core.Node;
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

public class GraphApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //TODO: Definiere, ob der Minimale Spannbaum (1) oder der Kürzeste Weg (2) markiert werden soll
        int modus = 1;

        //TODO: Definiere, ob Szenario Häuserquartier (1), Szenario Fluss (2) oder eigenes Szenario (3) gewählt wird
        //      Wählst du eigenes Szenario (3), implementiere die Methode defineCustomGraph im Anschluss entsprechend
        int template = 1;

        //TODO: Falls du Modus 1 gewählt hast, nenne den Namen des Start- und des Endknotens
        String startNodeName = "Start";
        String endNodeName = "Ziel";


        Node startNode = null;
        Node endNote = null;
        Graph graph;

        if (template != 3){
            graph = getGraphTemplate(template);
         }else {
            graph = defineCustomGraph();
        }

        if (modus == 2){
            startNode = graph.getNodeByName(startNodeName);
            endNote = graph.getNodeByName(endNodeName);
        }

        setupGUI(stage, graph, startNode, endNote, modus);

    }

    private Graph defineCustomGraph(){

        Graph graph = new Graph();
        //TODO: Definiere hier alle Knoten, die du im Graph haben möchtest
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");
        Node nodeG = new Node("G");

        //TODO: Füge hier alle Knoten zum Graph hinzu
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);
        graph.addNode(nodeG);


        //TODO: Erstelle hier alle Kanten, die du zwischen zwei Knoten einfügen möchtest
        graph.connectNodes(nodeA, nodeB, 4);
        graph.connectNodes(nodeA, nodeF, 7);
        graph.connectNodes(nodeB, nodeC, 2);
        graph.connectNodes(nodeB, nodeE, 6);
        graph.connectNodes(nodeB, nodeF, 9);
        graph.connectNodes(nodeC, nodeD, 3);
        graph.connectNodes(nodeC, nodeE, 2);
        graph.connectNodes(nodeE, nodeF, 9);
        graph.connectNodes(nodeF, nodeG, 6);

        return graph;
    }

    private Graph getGraphTemplate(int number) {
        switch (number) {
            case 1:
                Graph graph1 = new Graph();
                Node nodeA = new Node("A");
                Node nodeB = new Node("B");
                Node nodeC = new Node("C");
                Node nodeD = new Node("D");
                Node nodeE = new Node("E");
                Node nodeF = new Node("F");
                Node nodeG = new Node("G");

                graph1.addNode(nodeA);
                graph1.addNode(nodeB);
                graph1.addNode(nodeC);
                graph1.addNode(nodeD);
                graph1.addNode(nodeE);
                graph1.addNode(nodeF);
                graph1.addNode(nodeG);

                graph1.connectNodes(nodeA, nodeB, 4);
                graph1.connectNodes(nodeA, nodeF, 7);
                graph1.connectNodes(nodeB, nodeC, 2);
                graph1.connectNodes(nodeB, nodeE, 6);
                graph1.connectNodes(nodeB, nodeF, 9);
                graph1.connectNodes(nodeC, nodeD, 3);
                graph1.connectNodes(nodeC, nodeE, 2);
                graph1.connectNodes(nodeE, nodeF, 9);
                graph1.connectNodes(nodeF, nodeG, 6);
                return graph1;


            case 2:

                Graph graph2 = new Graph();
                Node nodeStart = new Node("Start");
                Node nodeA2 = new Node("A");
                Node nodeB2 = new Node("B");
                Node nodeC2 = new Node("C");
                Node nodeD2 = new Node("D");
                Node nodeE2 = new Node("E");
                Node nodeF2 = new Node("F");
                Node nodeZiel = new Node("Ziel");

                graph2.addNode(nodeStart);
                graph2.addNode(nodeA2);
                graph2.addNode(nodeB2);
                graph2.addNode(nodeC2);
                graph2.addNode(nodeD2);
                graph2.addNode(nodeE2);
                graph2.addNode(nodeF2);
                graph2.addNode(nodeZiel);

                graph2.connectNodes(nodeStart, nodeA2, 4);
                graph2.connectNodes(nodeStart, nodeC2, 7);
                graph2.connectNodes(nodeStart, nodeE2, 4);
                graph2.connectNodes(nodeA2, nodeB2, 3);
                graph2.connectNodes(nodeA2, nodeC2, 2);
                graph2.connectNodes(nodeB2, nodeD2, 1);
                graph2.connectNodes(nodeC2, nodeD2, 2);
                graph2.connectNodes(nodeC2, nodeE2, 9);
                graph2.connectNodes(nodeD2, nodeF2, 4);
                graph2.connectNodes(nodeD2, nodeZiel, 6);
                graph2.connectNodes(nodeE2, nodeF2, 9);
                graph2.connectNodes(nodeF2, nodeZiel, 5);

                return graph2;


            default:
                return null;
        }

    }

    private void setupGUI(Stage stage, Graph graph, Node startNode, Node goalNode, int modus) {
        LinkedList<Edge> minimalSpanningTree = null;
        LinkedList<Node> shortestPath = null;

        if (modus == 1){
            minimalSpanningTree = graph.getMinimalSpanningTree();
        }
        if (modus == 2){
            shortestPath = graph.getShortestPathFromTo(startNode, goalNode);
        }

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

        if (modus == 1) {
            highlightMinimalSpanningTree(gc, minimalSpanningTree, nodeMap);
        }
        if (modus == 2){
            highlightPath(gc, shortestPath, nodeMap);
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