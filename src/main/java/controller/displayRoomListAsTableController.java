package controller;

import Model.Room;
import Model.RoomType;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Callback;

import java.net.URL;
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

                                    Dialog dialog = new Dialog();
                                    dialog.setTitle("Xóa phòng");
                                    dialog.setHeaderText("Xác nhận xóa phòng " + room.getId());
                                    dialog.setContentText("Bạn có chắc chắn muốn xóa phòng này không?\n" +
                                            "Dữ liệu sau khi xóa sẽ không thể khôi phục");
                                    ButtonType submit = new ButtonType("Xóa", ButtonBar.ButtonData.OK_DONE);
                                    ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
                                    list.remove(room);
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

                                Dialog<Room> dialog = new Dialog<>();
                                dialog.setTitle("Thông tin phòng");
                                dialog.setHeaderText("Sửa thông tin phòng");

                                ButtonType submit = new ButtonType("Áp dụng", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancel = new ButtonType("Thoát", ButtonBar.ButtonData.CANCEL_CLOSE);
                                dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);

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
