package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class addRoomController implements Initializable {
    @FXML
    TextField id;
    @FXML
    TextField floor;
    @FXML
    TextField number;
    @FXML
    ChoiceBox<String> type;

    ObservableList<String> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();
        for(int i = 0; i < Main.roomTypeList.size(); i++){
            list.add(Main.roomTypeList.get(i).getName());
        }
        type.setItems(list);
    }

    public TextField getId() {
        return id;
    }

    public void setId(TextField id) {
        this.id = id;
    }

    public TextField getFloor() {
        return floor;
    }

    public void setFloor(TextField floor) {
        this.floor = floor;
    }

    public TextField getNumber() {
        return number;
    }

    public void setNumber(TextField number) {
        this.number = number;
    }

    public ChoiceBox<String> getType() {
        return type;
    }

    public void setType(ChoiceBox<String> type) {
        this.type = type;
    }
}
