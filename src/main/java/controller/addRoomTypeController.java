package controller;

import Model.RoomType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class addRoomTypeController {
    public Dialog showDialog() throws IOException {
        Dialog<RoomType> dialog = new Dialog<>();
        dialog.setTitle("Thêm loại phòng");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRoomTypeView.fxml"));
        RoomTypeViewController controller = new RoomTypeViewController();
        loader.setController(controller);
        AnchorPane pane = loader.load();
        pane.setMinSize(600, 475);

        ButtonType submit = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);
        dialog.getDialogPane().setContent(pane);

        Node submitButton = dialog.getDialogPane().lookupButton(submit);
        submitButton.setDisable(true);

        controller.getName().textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == submit){
                RoomType roomType = new RoomType();
                roomType.setName(controller.getName().getText().trim());
                roomType.setId(controller.getId().getText().trim());
                roomType.setDescription(controller.getDescription().getText().trim());
                roomType.setPricePerDay(Double.parseDouble(controller.getPricePerDay().getText().trim()));
                roomType.setPricePerHour(Double.parseDouble(controller.getPricePerHour().getText().trim()));
                roomType.setNumberOfBed(Integer.parseInt(controller.getBeds().getText().trim()));
                roomType.setMaxPeople(Integer.parseInt(controller.getMaxCapacity().getText().trim()));
                return roomType;
            }
            return null;
        });
        return dialog;
    }

//    public Dialog showDialog() throws IOException {
//        Dialog<RoomType> dialog = new Dialog<>();
//        dialog.setTitle("Thêm loại phòng");
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRoomTypeView.fxml"));
//        AnchorPane pane = loader.load();
//        pane.setMinSize(600, 475);
//
//        ButtonType submit = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
//        ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
//        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);
//        dialog.getDialogPane().setContent(pane);
//
//        Node submitButton = dialog.getDialogPane().lookupButton(submit);
//        submitButton.setDisable(true);
//
//        pane = (AnchorPane) dialog.getDialogPane().getContent();
//        pane.getName().textProperty().addListener((observable, oldValue, newValue) -> {
//            submitButton.setDisable(newValue.trim().isEmpty());
//        });
//
//        dialog.setResultConverter(dialogButton -> {
//            if(dialogButton == submit){
//                RoomType roomType = new RoomType();
//                roomType.setName(abc.getName().getText().trim());
//                roomType.setId(abc.getId().getText().trim());
//                roomType.setDescription(abc.getDescription().getText().trim());
//                roomType.setPricePerDay(Double.parseDouble(abc.getPricePerDay().getText().trim()));
//                roomType.setPricePerHour(Double.parseDouble(abc.getPricePerHour().getText().trim()));
//                roomType.setNumberOfBed(Integer.parseInt(abc.getBeds().getText().trim()));
//                roomType.setMaxPeople(Integer.parseInt(abc.getMaxCapacity().getText().trim()));
//                return roomType;
//            }
//            return null;
//        });
//        return dialog;
//    }
}
