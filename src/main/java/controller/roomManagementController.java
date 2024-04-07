package controller;

import Model.Room;
import Model.RoomType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class roomManagementController implements Initializable {
    @FXML
    TextField searchField;
    @FXML
    Button addRoomButton;
    @FXML
    Button filterApplyButton;
    @FXML
    Button filterResetButton;
    @FXML
    Button saveButton;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ChoiceBox<String> viewOption;

    FXMLLoader roomViewLoader;

    public void onFilterApplyButtonClicked(){

    }

    public void onFilterResetButtonClicked(){

    }

    public void onSaveButtonClicked() throws IOException {
        Main.roomList.clear();
        Main.roomList.addAll(((displayRoomListAsTableController) roomViewLoader.getController()).list);
        Main.writeIntoDB.writeRoomList();
    }

    private String defaultId(String floor, String number){
        return "P" + floor + String.format("%02d", Integer.parseInt(number));
    }

    public void onAddRoomButtonClicked() throws IOException {
        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Thêm phòng");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRoomView.fxml"));
        AnchorPane rootPane = loader.load();
        rootPane.setPrefSize(600, 300);
        dialog.getDialogPane().setContent(rootPane);

        ButtonType submit = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);
        Node submitButton = dialog.getDialogPane().lookupButton(submit);
        submitButton.setDisable(true);

        TextField id = ((addRoomController)loader.getController()).getId();
        TextField floor = ((addRoomController)loader.getController()).getFloor();
        TextField number = ((addRoomController)loader.getController()).getNumber();
        ChoiceBox<String> type = ((addRoomController)loader.getController()).getType();

        floor.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || id.getText().trim().isEmpty() || number.getText().trim().isEmpty() || type.getValue() == null);
            if(!newValue.trim().isEmpty() && !number.getText().trim().isEmpty()){
                id.setText(defaultId(newValue.trim(), number.getText().trim()));
            }
        });
        number.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || floor.getText().trim().isEmpty() || number.getText().trim().isEmpty() || type.getValue() == null);
            if(!newValue.trim().isEmpty() && !floor.getText().trim().isEmpty()){
                id.setText(defaultId(newValue.trim(), floor.getText().trim()));
            }
        });
        type.valueProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue == null || floor.getText().trim().isEmpty() || number.getText().trim().isEmpty() || id.getText().trim().isEmpty());
        });
        id.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || floor.getText().trim().isEmpty() || number.getText().trim().isEmpty() || type.getValue() == null);
        });

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == submit){
                Room room = new Room();
                room.setFloor(Integer.parseInt(floor.getText().trim()));
                room.setNumber(Integer.parseInt(number.getText().trim()));
                room.setId(id.getText().trim());
                for(RoomType roomType : Main.roomTypeList){
                    if(roomType.getName().equals(type.getValue())){
                        room.setType(roomType);
                        break;
                    }
                }
                return room;
            }
            return null;
        });

        Optional<Room> result = dialog.showAndWait();
        result.ifPresent(room -> {
            ((displayRoomListAsTableController)roomViewLoader.getController()).addRoom(room);
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewOption.getItems().addAll("Danh sách", "Theo tầng", "Theo trạng thái");
        viewOption.setValue("Danh sách");
        //viewOption.setOnAction(this::changeView);

        roomViewLoader = new FXMLLoader(getClass().getResource("displayRoomListAsTable.fxml"));
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(roomViewLoader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeView(ActionEvent event){
        if(viewOption.getValue().equals("Danh sách")){
            anchorPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayRoomListAsTable.fxml"));
            try {
                anchorPane = loader.load();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(viewOption.getValue().equals("Theo tầng")){

        }
        else {

        }
    }
}
