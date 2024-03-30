package controller;

import Model.Room;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckInController implements Initializable {
    @FXML
    TextField fromTime;
    @FXML
    TextField toTime;
    @FXML
    DatePicker fromDate;
    @FXML
    DatePicker toDate;
    @FXML
    ChoiceBox<String> viewOption;
    @FXML
    ComboBox<String> roomTypeFilter;
    @FXML
    ComboBox<String> roomRankFilter;
    @FXML
    Button filterApplyButton;
    @FXML
    Button filterResetButton;
    @FXML
    Button roomReceivingButton;
    @FXML
    TableView<Room> roomListingTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewOption.getItems().setAll(new String[]{"Danh sách", "Theo khu vực"});
        viewOption.setValue("Danh sách");
    }
}
