package controller;

import Model.RoomType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class roomTypeManagementController implements Initializable {
    @FXML
    TextField searchField;
    @FXML
    Button addRoomType;
    @FXML
    Button saveButton;
    @FXML
    TableView<RoomType> tableView;
    @FXML
    TableColumn<RoomType, String> idColumn;
    @FXML
    TableColumn<RoomType, String> nameColumn;
    @FXML
    TableColumn<RoomType, Integer> numberOfBedColumn;
    @FXML
    TableColumn<RoomType, Integer> maxCapacityColumn;
    @FXML
    TableColumn<RoomType, Double> pricePerHour;
    @FXML
    TableColumn<RoomType, Double> pricePerDay;

    ObservableList<RoomType> list;

    public void onSaveButtonClicked() throws IOException {
        Main.roomTypeList.clear();
        Main.roomTypeList.addAll(list);
        Main.writeIntoDB.writeRoomTypeList();
        System.out.println("Saved");
    }
    public void onAddRoomTypeButtonClicked() throws IOException {
        Dialog<RoomType> dialog = new Dialog<>();
        dialog.setTitle("Thêm loại phòng");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRoomTypeView.fxml"));
        AnchorPane rootPane = loader.load();
        rootPane.setPrefSize(610, 475);
        dialog.getDialogPane().setContent(rootPane);

        ButtonType submit = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);

        Node submitButton = dialog.getDialogPane().lookupButton(submit);
        submitButton.setDisable(true);

        TextField name = (TextField)dialog.getDialogPane().getContent().lookup("#name");
        TextField id = (TextField)dialog.getDialogPane().getContent().lookup("#id");
        TextField pricePerHour = (TextField)dialog.getDialogPane().getContent().lookup("#pricePerHour");
        TextField pricePerDay = (TextField)dialog.getDialogPane().getContent().lookup("#pricePerDay");
        TextField beds = (TextField)dialog.getDialogPane().getContent().lookup("#beds");
        TextField maxCapacity = (TextField)dialog.getDialogPane().getContent().lookup("#maxCapacity");

        Platform.runLater(name::requestFocus);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || id.getText().trim().isEmpty() || pricePerHour.getText().trim().isEmpty() || pricePerDay.getText().trim().isEmpty() || beds.getText().trim().isEmpty() || maxCapacity.getText().trim().isEmpty());
        });

        id.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || name.getText().trim().isEmpty() || pricePerHour.getText().trim().isEmpty() || pricePerDay.getText().trim().isEmpty() || beds.getText().trim().isEmpty() || maxCapacity.getText().trim().isEmpty());
        });

        pricePerHour.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || id.getText().trim().isEmpty() || name.getText().trim().isEmpty() || pricePerDay.getText().trim().isEmpty() || beds.getText().trim().isEmpty() || maxCapacity.getText().trim().isEmpty());
        });

        pricePerDay.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || id.getText().trim().isEmpty() || pricePerHour.getText().trim().isEmpty() || name.getText().trim().isEmpty() || beds.getText().trim().isEmpty() || maxCapacity.getText().trim().isEmpty());
        });

        beds.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || id.getText().trim().isEmpty() || pricePerHour.getText().trim().isEmpty() || pricePerDay.getText().trim().isEmpty() || name.getText().trim().isEmpty() || maxCapacity.getText().trim().isEmpty());
        });

        maxCapacity.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty() || id.getText().trim().isEmpty() || pricePerHour.getText().trim().isEmpty() || pricePerDay.getText().trim().isEmpty() || beds.getText().trim().isEmpty() || name.getText().trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == submit){
                RoomType roomType = new RoomType();
                AnchorPane pane = (AnchorPane) dialog.getDialogPane().getContent();
                roomType.setName(((TextField)pane.lookup("#name")).getText().trim());
                roomType.setId(((TextField)pane.lookup("#id")).getText().toUpperCase().trim());
                roomType.setPricePerDay(Double.parseDouble(((TextField)pane.lookup("#pricePerDay")).getText().trim()));
                roomType.setPricePerHour(Double.parseDouble(((TextField)pane.lookup("#pricePerHour")).getText().trim()));
                roomType.setNumberOfBed(Integer.parseInt(((TextField)pane.lookup("#beds")).getText().trim()));
                roomType.setMaxPeople(Integer.parseInt(((TextField)pane.lookup("#maxCapacity")).getText().trim()));

                for(RoomType x : list){
                    if(x.getId().equals(roomType.getId()) || x.getName().equals(roomType.getName())){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("ID hoặc tên đã tồn tại");
                        alert.setContentText("Vui lòng kiểm tra và thử lại");
                        alert.showAndWait();

                        return null;
                    }
                }
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
            list.add(a);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList(Main.roomTypeList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfBedColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfBed"));
        maxCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("maxPeople"));
        pricePerHour.setCellValueFactory(new PropertyValueFactory<>("pricePerHour"));
        pricePerDay.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));

        tableView.setItems(list);
    }
}
