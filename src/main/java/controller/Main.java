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
    private List<Staff> staffList;
    private List<Customer> customerList;
    private List<RoomType> roomTypeList;
    private List<Room> roomList;

    public Main() throws IOException {
        List<Staff> staffList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        List<RoomType> roomTypeList = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();

        LoadFromDB loadFromDB = new LoadFromDB();

    }
    public static void main(String[] args) {
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
