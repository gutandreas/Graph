module edu.andreasgut.graph {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.andreasgut.graph to javafx.fxml;
    exports edu.andreasgut.graph;
}