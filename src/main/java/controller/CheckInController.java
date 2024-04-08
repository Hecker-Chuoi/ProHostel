package controller;

import Model.Customer;
import Model.Room;
import Model.RoomType;
import Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CheckInController implements Initializable {
    @FXML
    TextField fromHour;
    @FXML
    TextField toHour;
    @FXML
    DatePicker fromDate;
    @FXML
    DatePicker toDate;
    @FXML
    ComboBox<String> roomTypeFilter;
    @FXML
    Button roomReceivingButton;
    @FXML
    TextField name;
    @FXML
    TextField citizenId;
    @FXML
    TextField phoneNum;
    @FXML
    TextField address;
    @FXML
    TextField nationality;
    @FXML
    ComboBox<String> gender;
    @FXML
    DatePicker dateOfBirth;
    @FXML
    TableView<Room> availableRoomsTable;
    @FXML
    TableView<Room> pickedRoomsTable;
    @FXML
    TableColumn<Room, String> idCol1;
    @FXML
    TableColumn<Room, Integer> floorCol;
    @FXML
    TableColumn<Room, Integer> roomNumberCol;
    @FXML
    TableColumn<Room, String> roomTypeCol;
    @FXML
    TableColumn<Room, Integer> numberOfBedCol;
    @FXML
    TableColumn<Room, Integer> maxCapacityCol;
    @FXML
    TableColumn<Room, String> addCol;
    @FXML
    TableColumn<Room, String> idCol2;
    @FXML
    TableColumn<Room, String> customerNumCol;
    @FXML
    TableColumn<Room, String> customerCol;
    @FXML
    TableColumn<Room, String> startTimeCol;
    @FXML
    TableColumn<Room, String> finishTimeCol;
    @FXML
    TableColumn<Room, String> delColumn;
    @FXML
    Button refresh;

    Customer newCustomer;
    ObservableList<String> roomTypeStringList;
    ObservableList<Room> availableRooms;
    ObservableList<Room> pickedRooms;

    public void formatDatePicker(DatePicker datePicker){
        datePicker.setStyle( "-fx-font-size: 14px;");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gender.setStyle( "-fx-font-size: 14px;");
        roomTypeFilter.setStyle( "-fx-font-size: 14px;");
        gender.getItems().addAll("Nam", "Nữ", "Khác");

        roomTypeStringList = FXCollections.observableArrayList();
        for(RoomType roomType : Main.roomTypeList){
            roomTypeStringList.add(roomType.getName());
        }
        roomTypeStringList.add("All");
        roomTypeFilter.setItems(roomTypeStringList);
        roomTypeFilter.setValue("All");

        formatDatePicker(dateOfBirth);
        formatDatePicker(fromDate);
        formatDatePicker(toDate);

        availableRooms = FXCollections.observableArrayList();
        pickedRooms = FXCollections.observableArrayList();

        LocalTime time = LocalTime.now();
        fromHour.setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
        toHour.setText(time.plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm")));

        LocalDate date = LocalDate.now();
        fromDate.setValue(date);
        toDate.setValue(date);

        newCustomer = new Customer();
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            newCustomer.setFullName(newValue.trim());
        });
        citizenId.textProperty().addListener((observable, oldValue, newValue) -> {
            newCustomer.setCitizenId(newValue.trim());
            for(Customer customer : Main.customerList){
                if(customer.getCitizenId().equals(newValue.trim())) {
                    newCustomer = customer;
                    name.setText(customer.getFullName());
                    dateOfBirth.setValue(customer.getDateOfBirth());
                    phoneNum.setText(customer.getPhoneNumber());
                    address.setText(customer.getAddress());
                    nationality.setText(customer.getNationality());
                    gender.setValue(customer.getGender());
                }
            }
        });
        dateOfBirth.valueProperty().addListener((observable, oldValue, newValue) -> {
            newCustomer.setDateOfBirth(newValue);
        });
        phoneNum.textProperty().addListener((observable, oldValue, newValue) -> {
            newCustomer.setPhoneNumber(newValue.trim());
        });
        address.textProperty().addListener((observable, oldValue, newValue) -> {
            newCustomer.setAddress(newValue.trim());
        });
        nationality.textProperty().addListener((observable, oldValue, newValue) -> {
            newCustomer.setNationality(newValue.trim());
        });
        gender.valueProperty().addListener((observable, oldValue, newValue) -> {
            newCustomer.setGender(newValue);
        });

        initAvailableRoomsTable();
        initPickedRoomsTable();
    }

    public void updateAvailableRoomList(){
        LocalTime fromTime = LocalTime.parse(this.fromHour.getText());
        LocalTime toTime = LocalTime.parse(this.toHour.getText());
        LocalDate fromDate = this.fromDate.getValue();
        LocalDate toDate = this.toDate.getValue();

        LocalDateTime fromDateTime = LocalDateTime.of(fromDate, fromTime);
        LocalDateTime toDateTime = LocalDateTime.of(toDate, toTime);

    }

    public void onRoomTypeFilter() {
        String type = roomTypeFilter.getValue();
        if(type == null || type.equals("All")){
            availableRooms.clear();
            for(Room room : Main.roomList){
                if(room.getStatus().equals("AVAILABLE"))
                    availableRooms.add(room);
            }
        }
        else{
            availableRooms.clear();
            for(Room room : Main.roomList){
                if(room.getTypeString().equals(type) && room.getStatus().equals("AVAILABLE"))
                    availableRooms.add(room);
            }
        }
    }
    public void refreshDataTable(){
        initAvailableRoomsTable();
        roomTypeStringList = FXCollections.observableArrayList();
        roomTypeStringList.clear();
        for(RoomType roomType : Main.roomTypeList){
            roomTypeStringList.add(roomType.getName());
        }
        roomTypeStringList.add("All");
        roomTypeFilter.setItems(roomTypeStringList);
        roomTypeFilter.setValue("All");
    }
    public void onRoomReceivingButtonClicked() throws IOException {
        boolean isCustomerExist = false;
        for(Customer customer : Main.customerList){
            if(customer.getCitizenId().equals(newCustomer.getCitizenId())){
                isCustomerExist = true;
                break;
            }
        }
        if(!isCustomerExist){
            Main.customerList.add(newCustomer);
            Main.writeIntoDB.writeCustomerList();
        }

        for(Room room : pickedRooms){
            room.setStatus(Status.OCCUPIED);
            room.setCustomer(newCustomer);
            room.setStartTime(LocalDateTime.of(fromDate.getValue(), LocalTime.parse(fromHour.getText())));
            room.setEndTime(LocalDateTime.of(toDate.getValue(), LocalTime.parse(toHour.getText())));
        }
        pickedRooms.clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Nhận phòng thành công");
        if(!isCustomerExist)
            alert.setContentText("Đã thêm khách hàng vào cơ sở dữ liệu");
        alert.showAndWait();
    }

    private void initAvailableRoomsTable() {
        roomTypeFilter.setValue("All");
        availableRooms = FXCollections.observableArrayList(Main.roomList);
        availableRooms.removeIf(room -> !room.getStatus().equals("AVAILABLE"));

        idCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("typeString"));
        numberOfBedCol.setCellValueFactory(new PropertyValueFactory<>("numberOfBed"));
        maxCapacityCol.setCellValueFactory(new PropertyValueFactory<>("maxPeople"));

        idCol1.setStyle( "-fx-alignment: CENTER;");
        floorCol.setStyle( "-fx-alignment: CENTER;");
        roomNumberCol.setStyle( "-fx-alignment: CENTER;");
        roomTypeCol.setStyle( "-fx-alignment: CENTER;");
        numberOfBedCol.setStyle( "-fx-alignment: CENTER;");
        maxCapacityCol.setStyle( "-fx-alignment: CENTER;");
        addCol.setStyle( "-fx-alignment: CENTER;");

        Callback<TableColumn<Room, String>, TableCell<Room, String>> cellFactory
                = new Callback<TableColumn<Room, String>, TableCell<Room, String>>() {
            @Override
            public TableCell call(final TableColumn<Room, String> param) {
                final TableCell<Room, String> cell = new TableCell<Room, String>() {
                    final Button addButton = new Button("+");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            addButton.setOnAction(event -> {
                                Room room = getTableView().getItems().get(getIndex());
                                room.setCustomer(newCustomer);
                                room.setStartTime(LocalDateTime.of(fromDate.getValue(), LocalTime.parse(fromHour.getText())));
                                room.setEndTime(LocalDateTime.of(toDate.getValue(), LocalTime.parse(toHour.getText())));

                                pickedRooms.add(room);
                                availableRooms.remove(room);
                            });
                            setGraphic(addButton);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        addCol.setCellFactory(cellFactory);
        availableRoomsTable.setItems(availableRooms);
    }
    private void initPickedRoomsTable() {
        pickedRoomsTable.setEditable(true);
        idCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNumCol.setCellValueFactory(new PropertyValueFactory<>("numberOfPeople"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        finishTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerString"));

        idCol2.setStyle( "-fx-alignment: CENTER;");
        customerNumCol.setStyle( "-fx-alignment: CENTER;");
        startTimeCol.setStyle( "-fx-alignment: CENTER;");
        finishTimeCol.setStyle( "-fx-alignment: CENTER;");
        customerCol.setStyle( "-fx-alignment: CENTER;");
        delColumn.setStyle( "-fx-alignment: CENTER;");

        customerNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerNumCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Room, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Room, String> t) {
                        Room room = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        try {
                            int num = Integer.parseInt(t.getNewValue());
                            if(num > 0 && num <= room.getMaxPeople()){
                                room.setNumberOfPeople(t.getNewValue().trim());
                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Lỗi");
                                alert.setHeaderText("Dữ liệu không hợp lệ");
                                alert.setContentText("Số người đăng ký phải nằm trong khoảng từ 1 đến " + room.getMaxPeople() + " người");

                                ButtonType cancel = new ButtonType("Thoát", ButtonBar.ButtonData.CANCEL_CLOSE);
                                alert.getButtonTypes().setAll(cancel);
                                alert.showAndWait();
                            }
                        }
                        catch(NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Lỗi");
                            alert.setHeaderText("Dữ liệu không hợp lệ");
                            alert.setContentText("Vui lòng nhập số người đăng ký hợp lệ");

                            ButtonType cancel = new ButtonType("Thoát", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(cancel);
                            alert.showAndWait();
                        }
                    }
                }
        );

        Callback<TableColumn<Room, String>, TableCell<Room, String>> cellFactory
            = new Callback<TableColumn<Room, String>, TableCell<Room, String>>() {
            @Override
            public TableCell call(final TableColumn<Room, String> param) {
                final TableCell<Room, String> cell = new TableCell<Room, String>() {
                    final Button delButton = new Button("-");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            delButton.setOnAction(event -> {
                                Room room = getTableView().getItems().get(getIndex());
                                pickedRooms.remove(room);
                                room.init();
                                availableRooms.add(room);
                            });
                            setGraphic(delButton);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        delColumn.setCellFactory(cellFactory);
        pickedRooms = FXCollections.observableArrayList();
        pickedRoomsTable.setItems(pickedRooms);
    }
}
