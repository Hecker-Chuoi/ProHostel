package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
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

    public boolean onLoginButtonClicked(ActionEvent event) throws IOException {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        LoadFromDB loadFromDB = new LoadFromDB();
        if(loadFromDB.isAccountTypedExists(usernameInput, passwordInput)){
            System.out.println("Login successful!");
            return true;
        } else {
            wrongPasswordAlert();
            initComponent();
            return false;
        }
    }
}
