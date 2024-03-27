package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("ProHotel - Ứng dụng quản lý khách sạn");
        Image icon = new Image(String.valueOf(Main.class.getResource("appIcon.png")));
        stage.getIcons().add(icon);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
