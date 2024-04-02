package controller;

import Model.RoomType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class roomTypeManagementController {
    @FXML
    TextField searchField;
    @FXML
    Button addRoomType;
    @FXML
    TableView<RoomType> tableView;

    public void onAddRoomTypeButtonClicked() throws IOException {
        addRoomTypeController addRoomType = new addRoomTypeController();
        Dialog dialog = addRoomType.showDialog();
        Optional<RoomType> result = dialog.showAndWait();
    }
}
