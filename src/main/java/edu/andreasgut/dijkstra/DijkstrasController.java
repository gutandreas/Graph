package edu.andreasgut.dijkstra;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DijkstrasController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}