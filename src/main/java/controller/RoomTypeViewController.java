package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RoomTypeViewController {
    @FXML
    TextField name;
    @FXML
    TextField beds;
    @FXML
    TextField id;
    @FXML
    TextField pricePerHour;
    @FXML
    TextField pricePerDay;
    @FXML
    TextField maxCapacity;
    @FXML
    TextArea description;

    public TextField getName() {
        return name;
    }

    public TextField getBeds() {
        return beds;
    }

    public TextField getId() {
        return id;
    }

    public TextField getPricePerHour() {
        return pricePerHour;
    }

    public TextField getPricePerDay() {
        return pricePerDay;
    }

    public TextField getMaxCapacity() {
        return maxCapacity;
    }

    public TextArea getDescription() {
        return description;
    }
}
