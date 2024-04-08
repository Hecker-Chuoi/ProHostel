package controller;

import Model.Room;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

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
    @FXML
    TableColumn<RoomType, String> fixColumn;
    @FXML
    TableColumn<RoomType, String> delColumn;

    ObservableList<RoomType> list;

    public void onSaveButtonClicked() throws IOException {
        Main.roomTypeList.clear();
        Main.roomTypeList.addAll(list);
        Main.writeIntoDB.writeRoomTypeList();
        System.out.println("Saved");
    }
    public void onAddRoomTypeButtonClicked() throws IOException {
        Dialog<RoomType> dialog = addRoomTypeDialog();
        Optional<RoomType> result = dialog.showAndWait();
        if(result.isPresent()) {
            System.out.println("Added " + result.get().getName());
            list.add(result.get());
        }
    }
    public Dialog<RoomType> addRoomTypeDialog() throws IOException {
        Dialog<RoomType> dialog = new Dialog<>();
        dialog.setTitle("Thêm loại phòng");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRoomTypeView.fxml"));
        AnchorPane rootPane = loader.load();
        rootPane.setPrefSize(610, 475);
        dialog.getDialogPane().setContent(rootPane);

        ButtonType submit = new ButtonType("Thêm", OK_DONE);
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

        return dialog;
    }

    public Dialog<RoomType> fixRoomTypeDialog(RoomType oldRoomType) throws IOException {
        Dialog<RoomType> dialog = addRoomTypeDialog();
        dialog.setTitle("Thay đổi thông tin loại phòng");

        TextField name = (TextField)dialog.getDialogPane().getContent().lookup("#name");
        TextField id = (TextField)dialog.getDialogPane().getContent().lookup("#id");
        TextField pricePerHour = (TextField)dialog.getDialogPane().getContent().lookup("#pricePerHour");
        TextField pricePerDay = (TextField)dialog.getDialogPane().getContent().lookup("#pricePerDay");
        TextField beds = (TextField)dialog.getDialogPane().getContent().lookup("#beds");
        TextField maxCapacity = (TextField)dialog.getDialogPane().getContent().lookup("#maxCapacity");

        name.setText(oldRoomType.getName());
        id.setText(oldRoomType.getId());
        pricePerHour.setText(String.valueOf(oldRoomType.getPricePerHour()));
        pricePerDay.setText(String.valueOf(oldRoomType.getPricePerDay()));
        beds.setText(String.valueOf(oldRoomType.getNumberOfBed()));
        maxCapacity.setText(String.valueOf(oldRoomType.getMaxPeople()));

        ButtonType submit = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == submit){
                RoomType newRoomType = new RoomType();
                AnchorPane pane = (AnchorPane) dialog.getDialogPane().getContent();
                newRoomType.setName(((TextField)pane.lookup("#name")).getText().trim());
                newRoomType.setId(((TextField)pane.lookup("#id")).getText().toUpperCase().trim());
                newRoomType.setPricePerDay(Double.parseDouble(((TextField)pane.lookup("#pricePerDay")).getText().trim()));
                newRoomType.setPricePerHour(Double.parseDouble(((TextField)pane.lookup("#pricePerHour")).getText().trim()));
                newRoomType.setNumberOfBed(Integer.parseInt(((TextField)pane.lookup("#beds")).getText().trim()));
                newRoomType.setMaxPeople(Integer.parseInt(((TextField)pane.lookup("#maxCapacity")).getText().trim()));

                return newRoomType;
            }
            return null;
        });

        return dialog;
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

        idColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER;");
        numberOfBedColumn.setStyle("-fx-alignment: CENTER;");
        maxCapacityColumn.setStyle("-fx-alignment: CENTER;");
        pricePerHour.setStyle("-fx-alignment: CENTER;");
        pricePerDay.setStyle("-fx-alignment: CENTER;");
        fixColumn.setStyle("-fx-alignment: CENTER;");
        delColumn.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<RoomType, String>, TableCell<RoomType, String>> delCellFactory
                = new Callback<TableColumn<RoomType, String>, TableCell<RoomType, String>>() {
            @Override
            public TableCell call(final TableColumn<RoomType, String> param) {
                final TableCell<RoomType, String> cell = new TableCell<RoomType, String>() {
                    final Button delButton = new Button("Xóa");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            delButton.setOnAction(event -> {
                                RoomType roomType = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Xác nhận xóa");
                                alert.setHeaderText("Bạn có chắc chắn muốn xóa loại phòng này?");
                                alert.setContentText("Hành động này không thể hoàn tác");
                                ButtonType yesButton = new ButtonType("Xóa", ButtonBar.ButtonData.YES);
                                ButtonType noButton = new ButtonType("Hủy", ButtonBar.ButtonData.NO);
                                alert.getButtonTypes().setAll(yesButton, noButton);
                                Optional<ButtonType> result = alert.showAndWait();
                                if(result.get() == yesButton){
                                    System.out.println("Deleted " + roomType.getName());
                                    list.remove(roomType);
                                }
                            });
                            setGraphic(delButton);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        Callback<TableColumn<RoomType, String>, TableCell<RoomType, String>> fixCellFactory
                = new Callback<TableColumn<RoomType, String>, TableCell<RoomType, String>>() {
            @Override
            public TableCell call(final TableColumn<RoomType, String> param) {
                final TableCell<RoomType, String> cell = new TableCell<RoomType, String>() {
                    final Button fixButton = new Button("Sửa");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            fixButton.setOnAction(event -> {
                                RoomType roomType = getTableView().getItems().get(getIndex());
                                try {
                                    Dialog<RoomType> fixDialog = fixRoomTypeDialog(roomType);
                                    Optional<RoomType> result = fixDialog.showAndWait();
                                    if(result.isPresent()){
                                        list.remove(roomType);
                                        list.add(result.get());
                                        System.out.println("Fixed " + result.get().getName());
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            setGraphic(fixButton);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        delColumn.setCellFactory(delCellFactory);
        fixColumn.setCellFactory(fixCellFactory);
        tableView.setItems(list);
    }
}
