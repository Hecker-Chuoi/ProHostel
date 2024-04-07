package controller;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {
    public static List<Staff> staffList;
    public static List<Customer> customerList;
    public static List<RoomType> roomTypeList;
    public static List<Room> roomList;

    public static LoadFromDB loadFromDB;
    public static WriteIntoDB writeIntoDB;

    public static void main(String[] args) throws IOException {
        staffList = new ArrayList<>();
        customerList = new ArrayList<>();
        roomTypeList = new ArrayList<>();
        roomList = new ArrayList<>();

        loadFromDB = new LoadFromDB();
        writeIntoDB = new WriteIntoDB();

        loadFromDB.inputRoomTypeList();
        loadFromDB.inputRoomList();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("ProHotel - Ứng dụng quản lý khách sạn");
        Image icon = new Image(String.valueOf(Main.class.getResource("/Image/appIcon.png")));
        stage.getIcons().add(icon);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.centerOnScreen();
        stage.show();

    }
}
