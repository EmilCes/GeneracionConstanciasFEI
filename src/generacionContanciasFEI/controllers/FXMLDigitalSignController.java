package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.dao.DigitalSignDAO;
import model.pojo.DigitalSign;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDigitalSignController implements Initializable {
    @javafx.fxml.FXML
    private AnchorPane apDigitalSign;
    @javafx.fxml.FXML
    private TextField tfDigitalSign;
    @javafx.fxml.FXML
    private Button btnUpdateDigitalSignManually;
    @javafx.fxml.FXML
    private Button btnCancel;
    @javafx.fxml.FXML
    private Button btnUpdateManually;
    @javafx.fxml.FXML
    private Button btnGenerateNewDigitalSign;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDigitalSign();
    }

    @javafx.fxml.FXML
    public void clickUpdateDigitalSignManually(ActionEvent actionEvent) {
        tfDigitalSign.setEditable(true);
    }

    @javafx.fxml.FXML
    public void clickCancel(ActionEvent actionEvent) {
        getDigitalSign();
        tfDigitalSign.setEditable(false);
    }

    @javafx.fxml.FXML
    public void clickGenerateNewDigitalSign(ActionEvent actionEvent) {
    }

    private void getDigitalSign(){
        DigitalSign digitalSign = DigitalSignDAO.getLastDigitalSignRegistered();
        tfDigitalSign.setText(digitalSign.getSign());
    }

    @FXML
    public void clickSaveDigitalSign(ActionEvent actionEvent) {
        //TODO: VALIDAR FIRMA DIGITAL
    }
}
