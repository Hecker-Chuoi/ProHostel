package controller;

import Model.Receipt;
import Model.Room;
import Model.RoomType;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class displayRoomListAsTableController implements Initializable{
    @FXML
    TableView<Room> tableView;
    @FXML
    TableColumn<Room, String> idColumn;
    @FXML
    TableColumn<Room, Integer> floorColumn;
    @FXML
    TableColumn<Room, Integer> numberColumn;
    @FXML
    TableColumn<Room, String> roomTypeColumn;
    @FXML
    TableColumn<Room, String> statusColumn;
    @FXML
    TableColumn<Room, String> customerColumn;
    @FXML
    TableColumn<Room, Integer> numOfCustomers;
    @FXML
    TableColumn<Room, String> fixColumn;
    @FXML
    TableColumn<Room, String> delColumn;

    ObservableList<Room> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList(Main.roomList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeString"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statusString"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerString"));
        numOfCustomers.setCellValueFactory(new PropertyValueFactory<>("numberOfPeople"));

        idColumn.setStyle( "-fx-alignment: CENTER;");
        floorColumn.setStyle( "-fx-alignment: CENTER;");
        numberColumn.setStyle( "-fx-alignment: CENTER;");
        roomTypeColumn.setStyle( "-fx-alignment: CENTER;");
        statusColumn.setStyle( "-fx-alignment: CENTER;");
        customerColumn.setStyle( "-fx-alignment: CENTER;");
        numOfCustomers.setStyle( "-fx-alignment: CENTER;");
        delColumn.setStyle( "-fx-alignment: CENTER;");
        fixColumn.setStyle( "-fx-alignment: CENTER;");

        tableView.setRowFactory( tv -> {
            TableRow<Room> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Room rowData = row.getItem();
                    try {
                        showRoomDetail(rowData);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row ;
        });

        Callback<TableColumn<Room, String>, TableCell<Room, String>> DelCellFactory
            = new Callback<TableColumn<Room, String>, TableCell<Room, String>>() {
                @Override
                public TableCell call(final TableColumn<Room, String> param) {
                    final TableCell<Room, String> cell = new TableCell<Room, String>() {
                        final Button delButton = new Button("Xóa");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                delButton.setOnAction(event -> {
                                    Room room = getTableView().getItems().get(getIndex());

                                    Dialog<ButtonType> dialog = new Dialog();
                                    dialog.setTitle("Xóa phòng");
                                    dialog.setHeaderText("Xác nhận xóa phòng " + room.getId());
                                    dialog.setContentText("Bạn có chắc chắn muốn xóa phòng này không?\n" +
                                            "Dữ liệu sau khi xóa sẽ không thể khôi phục");
                                    ButtonType submit = new ButtonType("Xóa", ButtonBar.ButtonData.OK_DONE);
                                    ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);

                                    dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);

                                    Optional<ButtonType> result = dialog.showAndWait();
                                    if(result.isPresent() && result.get() == submit){
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Thông báo");
                                        alert.setHeaderText("Đã xóa phòng " + room.getId());
                                        alert.showAndWait();
                                        list.remove(room);
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

        Callback<TableColumn<Room, String>, TableCell<Room, String>> FixCellFactory
                = new Callback<TableColumn<Room, String>, TableCell<Room, String>>() {
            @Override
            public TableCell call(final TableColumn<Room, String> param) {
                final TableCell<Room, String> cell = new TableCell<Room, String>() {
                    final Button delButton = new Button("Sửa");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            delButton.setOnAction(event -> {
                                Room room = getTableView().getItems().get(getIndex());

                                Dialog<Boolean> dialog = new Dialog<>();
                                dialog.setTitle("Sửa thông tin phòng " + room.getId());

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("addRoomView.fxml"));
                                AnchorPane rootPane = null;
                                try {
                                    rootPane = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                rootPane.setPrefSize(600, 300);
                                dialog.getDialogPane().setContent(rootPane);

                                ButtonType submit = new ButtonType("Áp dụng", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
                                dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);

                                TextField id = ((addRoomController)loader.getController()).getId();
                                TextField floor = ((addRoomController)loader.getController()).getFloor();
                                TextField number = ((addRoomController)loader.getController()).getNumber();
                                ChoiceBox<String> type = ((addRoomController)loader.getController()).getType();

                                id.setText(room.getId());
                                floor.setText(String.valueOf(room.getFloor()));
                                number.setText(String.valueOf(room.getNumber()));
                                type.setValue(room.getTypeString());

                                floor.setEditable(false);
                                number.setEditable(false);

                                dialog.setResultConverter(dialogButton -> {
                                    if(dialogButton == submit){
                                        room.setId(id.getText().trim());
                                        for(RoomType roomType : Main.roomTypeList){
                                            if(roomType.getName().equals(type.getValue())){
                                                room.setType(roomType);
                                                break;
                                            }
                                        }

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Thông báo");
                                        alert.setHeaderText("Sửa thông tin phòng thành công");
                                        alert.showAndWait();
                                    }
                                    return true;
                                });

                                dialog.showAndWait();
                            });
                            setGraphic(delButton);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        delColumn.setCellFactory(DelCellFactory);
        fixColumn.setCellFactory(FixCellFactory);

        tableView.setItems(list);
    }

    public void showRoomDetail(Room room) throws IOException {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Thông tin phòng");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showRoomDetail.fxml"));
        AnchorPane pane = loader.load();
        dialog.getDialogPane().setContent(pane);

        Label customerNameLabel = (Label) dialog.getDialogPane().getContent().lookup("#customerNameLabel");
        Label startTimeLabel = (Label) dialog.getDialogPane().getContent().lookup("#startTimeLabel");
        Label duringTimeLabel = (Label) dialog.getDialogPane().getContent().lookup("#duringTimeLabel");
        Label numOfCustomerLabel = (Label) dialog.getDialogPane().getContent().lookup("#numOfCustomerLabel");
        Label roomNameLabel = (Label) dialog.getDialogPane().getContent().lookup("#roomNameLabel");

        ButtonType submit = new ButtonType("Trả phòng", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Thoát", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);

        Node submitButton = dialog.getDialogPane().lookupButton(submit);
        if(room.getCustomer() == null || room.getStartTime() == null || room.getEndTime() == null){
            submitButton.setVisible(false);
        }

        Receipt receipt = new Receipt(room);
        roomNameLabel.setText(receipt.getRoom().getId());
        duringTimeLabel.setText(String.format("%d ngày - %d giờ", receipt.getDay(), receipt.getHour()));
        if(room.getCustomer() == null)
            customerNameLabel.setText("Trống");
        else
            customerNameLabel.setText(room.getCustomer().getFullName());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        if(room.getStartTime() == null)
            startTimeLabel.setText("Trống");
        else
            startTimeLabel.setText(format.format(room.getStartTime()));
        numOfCustomerLabel.setText(room.getNumberOfPeople() + " người");

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == submit){
                room.init();
                tableView.refresh();
                Main.receiptList.add(receipt);
                try {
                    Main.writeIntoDB.writeReceiptList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        });

        dialog.showAndWait();
    }

    public ObservableList<Room> getList() {
        return list;
    }

    public void setList(ObservableList<Room> list) {
        this.list = list;
    }

    public void addRoom(Room room){
        list.add(room);
    }
}
