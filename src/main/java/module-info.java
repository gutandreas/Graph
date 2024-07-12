module edu.andreasgut.dijkstra {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.andreasgut.dijkstra to javafx.fxml;
    exports edu.andreasgut.dijkstra;
}