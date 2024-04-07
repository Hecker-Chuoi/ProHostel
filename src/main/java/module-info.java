module controller.prohotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens controller to javafx.graphics, javafx.fxml, javafx.base;
    opens Model to javafx.graphics, javafx.fxml, javafx.base;
    exports controller;
}