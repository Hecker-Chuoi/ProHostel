package controller;

import Model.Receipt;
import Model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReceiptListViewController implements Initializable {
    @FXML
    TableView<Receipt> tableView;
    @FXML
    TextField fromTime;
    @FXML
    DatePicker fromDate;
    @FXML
    Button refresh;
    @FXML
    ComboBox<String> roomTypeFilter;
    @FXML
    TableColumn<Receipt, String> idCol;
    @FXML
    TableColumn<Receipt, String> customerNameCol;
    @FXML
    TableColumn<Receipt, String> roomIdCol;
    @FXML
    TableColumn<Receipt, Integer> numOfPeopleCol;
    @FXML
    TableColumn<Receipt, String> fromTimeCol;
    @FXML
    TableColumn<Receipt, String> toTimeCol;
    @FXML
    TableColumn<Receipt, Double> totalCol;
    @FXML
    TableColumn<Receipt, String> delCol;

    ObservableList<Receipt> list = FXCollections.observableArrayList();

    public void onRefreshButtonClicked(){
        initRoomTypeFilter();
        list = FXCollections.observableArrayList(Main.receiptList);
        tableView.setItems(list);
    }

    public void onRoomTypeFilterApply(){
        String type = roomTypeFilter.getValue();
        if(type == null || type.equals("All")){
            list.clear();
            list.addAll(Main.receiptList);
        }
        else{
            list.clear();
            for(Receipt receipt : Main.receiptList){
                Room room = receipt.getRoom();
                if(room.getType().getName().equals(type)){
                    list.add(receipt);
                }
            }
        }
    }

    public void onSaveButtonClicked() throws IOException {
        Main.receiptList.clear();
        Main.receiptList.addAll(list);
        Main.writeIntoDB.writeReceiptList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initRoomTypeFilter();
        initTable();
    }

    private void initRoomTypeFilter(){
        roomTypeFilter.getItems().clear();
        for(int i = 0; i < Main.roomTypeList.size(); i++){
            roomTypeFilter.getItems().add(Main.roomTypeList.get(i).getName());
        }
        roomTypeFilter.getItems().add("All");
        roomTypeFilter.setValue("All");
    }

    private void initTable(){
        list = FXCollections.observableArrayList(Main.receiptList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        numOfPeopleCol.setCellValueFactory(new PropertyValueFactory<>("numOfPeople"));
        fromTimeCol.setCellValueFactory(new PropertyValueFactory<>("fromTimeString"));
        toTimeCol.setCellValueFactory(new PropertyValueFactory<>("toTimeString"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        idCol.setStyle( "-fx-alignment: CENTER;");
        customerNameCol.setStyle( "-fx-alignment: CENTER;");
        roomIdCol.setStyle( "-fx-alignment: CENTER;");
        numOfPeopleCol.setStyle( "-fx-alignment: CENTER;");
        fromTimeCol.setStyle( "-fx-alignment: CENTER;");
        toTimeCol.setStyle( "-fx-alignment: CENTER;");
        totalCol.setStyle( "-fx-alignment: CENTER;");

        Callback<TableColumn<Receipt, String>, TableCell<Receipt, String>> DelCellFactory
                = new Callback<TableColumn<Receipt, String>, TableCell<Receipt, String>>() {
            @Override
            public TableCell call(final TableColumn<Receipt, String> param) {
                final TableCell<Receipt, String> cell = new TableCell<Receipt, String>() {
                    final Button delButton = new Button("Xóa");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            delButton.setOnAction(event -> {
                                Receipt receipt = getTableView().getItems().get(getIndex());

                                Dialog<ButtonType> dialog = new Dialog();
                                dialog.setTitle("Confirmation Dialog");
                                dialog.setHeaderText("Xóa hóa đơn");
                                dialog.setContentText("Bạn có chắc chắn muốn xóa hóa đơn " + receipt.getReceiptId() + " không?\n" +
                                        "Dữ liệu sau khi bị xóa không thể khôi phục!");
                                ButtonType submit = new ButtonType("Xóa", ButtonBar.ButtonData.OK_DONE);
                                ButtonType cancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);

                                dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);

                                Optional<ButtonType> result = dialog.showAndWait();
                                if(result.isPresent() && result.get() == submit){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information Dialog");
                                    alert.setHeaderText("Đã xóa hóa đơn " + receipt.getReceiptId());
                                    alert.showAndWait();
                                    list.remove(receipt);
                                    try {
                                        onSaveButtonClicked();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

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

        delCol.setCellFactory(DelCellFactory);
        tableView.setItems(list);
    }
}
