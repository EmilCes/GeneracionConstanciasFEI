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
import model.dao.UserDAO;
import model.pojo.User;
import utils.Constants;
import utils.UserSingleton;
import utils.Utilities;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLLoginController implements Initializable
{
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void validateLoginCredentials(String username, String password) {
        User userResponse = UserDAO.verifyUserSession(username, password);

        switch (userResponse.getResponseCode()) {
            case Constants.CONNECTION_ERROR:
                Utilities.showSimpleDialog("Error de conexión",
                        "Por el momento no hay conexión, por favor intentelo mas tarde.",
                        Alert.AlertType.ERROR);
                break;
            case Constants.QUERY_ERROR:
                Utilities.showSimpleDialog("Error en la solicitud",
                        "Por el momento no se puede procesar la solicitud de verificación.",
                        Alert.AlertType.ERROR);
                break;
            case Constants.OPERATION_SUCCESFUL:
                if (userResponse.getIdUser() > 0) {
                    UserSingleton.getInstance().setUser(userResponse);
                    goToMainMenu();
                } else {
                    Utilities.showSimpleDialog("Credenciales incorrectas",
                            "El usuario y/o contraseña no son correctos, por favor verifica la información.",
                            Alert.AlertType.WARNING);
                }

                break;
            default:
                Utilities.showSimpleDialog("Error de petición",
                        "El sistema no esta disponible por el momento.",
                        Alert.AlertType.ERROR);
                throw new AssertionError();
        }
    }

    @FXML
    private void clickLogin(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        validateLoginCredentials(username,password);
    }

    private void goToMainMenu(){
        Stage baseStage = (Stage) tfUsername.getScene().getWindow();
        baseStage.setScene(Utilities.initializeScene("/views/FXMLMainMenu.fxml"));
        Utilities.centerStage(baseStage);
        baseStage.setTitle("Menu Principal");
        baseStage.show();
    }


}