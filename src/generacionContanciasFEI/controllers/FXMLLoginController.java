package controllers;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utils.Utilities;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLLoginController implements Initializable
{
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tbxPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickLogin(ActionEvent event) {
        goToMainMenu();
    }

    private void goToMainMenu(){
        Stage baseStage = (Stage) tfEmail.getScene().getWindow();
        baseStage.setScene(Utilities.initializeScene("/views/FXMLMainMenu.fxml"));
        Utilities.centerStage(baseStage);
        baseStage.setTitle("Menu Principal");
        baseStage.show();
    }
}