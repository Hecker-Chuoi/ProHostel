package controller;

import Model.RoomType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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
        Dialog<RoomType> dialog = new Dialog<>();
        dialog.setTitle("Thêm loại phòng");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRoomTypeView.fxml"));
        AnchorPane rootPane = loader.load();
        dialog.getDialogPane().setContent(rootPane);

        ButtonType submit = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);

        Node submitButton = dialog.getDialogPane().lookupButton(submit);
        submitButton.setDisable(true);

        TextField name = (TextField)dialog.getDialogPane().getContent().lookup("#name");
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == submit){
                RoomType roomType = new RoomType();
                AnchorPane pane = (AnchorPane) dialog.getDialogPane().getContent();
                roomType.setName(((TextField)pane.lookup("#name")).getText());
                roomType.setId(((TextField)pane.lookup("#id")).getText());
                roomType.setPricePerDay(Double.parseDouble(((TextField)pane.lookup("#pricePerDay")).getText()));
                roomType.setPricePerHour(Double.parseDouble(((TextField)pane.lookup("#pricePerHour")).getText()));
                roomType.setNumberOfBed(Integer.parseInt(((TextField)pane.lookup("#beds")).getText()));
                roomType.setMaxPeople(Integer.parseInt(((TextField)pane.lookup("#maxCapacity")).getText()));
                return roomType;
            }
            return null;
        });

        Optional<RoomType> result = dialog.showAndWait();
        if(result.isPresent()){
            RoomType a = result.get();
            System.out.println("Name: " + a.getName());
            System.out.println("ID: " + a.getId());
            System.out.println("Price per hour: " + a.getPricePerHour());
            System.out.println("Price per day: " + a.getPricePerDay());
            System.out.println("Number of bed: " + a.getNumberOfBed());
            System.out.println("Max people: " + a.getMaxPeople());
        }
    }
}
