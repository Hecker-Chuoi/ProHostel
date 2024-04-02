package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController{
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Button loginButton;

    private void initComponent(){
        username.clear();
        password.clear();
        //loginButton.setDisable(true);
    }

    private void wrongPasswordAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Sai tài khoản hoặc mật khẩu!");
        alert.setContentText("Vui lòng kiểm tra lại hoặc liên hệ admin để được hỗ trợ");

        Optional<ButtonType> result = alert.showAndWait();
        initComponent();
    }

    public void onPasswordType(){

    }

    public void onLoginButtonClicked(ActionEvent event) throws IOException {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        LoadFromDB loadFromDB = new LoadFromDB();
        if(loadFromDB.isAccountTypedExists(usernameInput, passwordInput)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setX(10); stage.setY(10);
            stage.show();
        } else {
            wrongPasswordAlert();
            initComponent();
        }
    }
}
