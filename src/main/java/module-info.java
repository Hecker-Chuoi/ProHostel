module controller.prohotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens controller to javafx.fxml;
    exports controller;
}