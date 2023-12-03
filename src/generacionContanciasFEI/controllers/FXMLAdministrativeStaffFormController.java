package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.dao.AdministrativeStaffDAO;
import model.dao.TeacherDAO;
import model.dao.UserDAO;
import model.pojo.AdministrativeStaff;
import model.pojo.Teacher;
import utils.Constants;
import utils.Utilities;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class FXMLAdministrativeStaffFormController implements Initializable {
    @javafx.fxml.FXML
    private AnchorPane apAdministrative;
    @javafx.fxml.FXML
    private TextField tfName;
    @javafx.fxml.FXML
    private TextField tfFirstLastName;
    @javafx.fxml.FXML
    private TextField tfSecondLastName;
    @javafx.fxml.FXML
    private TextField tfInstitutionalEmail;
    @javafx.fxml.FXML
    private Button btnCancel;
    @javafx.fxml.FXML
    private Button btnModifyAdministrative;
    @javafx.fxml.FXML
    private Button btnRegisterAdministrative;

    private int idAdministrativeStaff;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @javafx.fxml.FXML
    public void clickRegisterAdministrative(ActionEvent actionEvent) {
        AdministrativeStaff administrativeStaff = validateFields();
        if(administrativeStaff != null){
            saveAdministrativeStaff(administrativeStaff);
        }
    }

    @javafx.fxml.FXML
    public void clickCancel(ActionEvent actionEvent) {
        Utilities.changePane(apAdministrative, "/views/ConsultAdministrativeStaff.fxml");
    }

    @javafx.fxml.FXML
    public void clickModifyAdministrativeInformation(ActionEvent actionEvent) {
        AdministrativeStaff administrativeStaff = validateFields();
        administrativeStaff.setIdAdministrativeStaff(this.idAdministrativeStaff);
        boolean administartiveUpdated = updateAdministrative(administrativeStaff);

        if(administartiveUpdated){
            Utilities.showSimpleDialog("Personal Actualizado", "El personal se actualizo exitosamente", Alert.AlertType.INFORMATION);
        } else{
            Utilities.showSimpleDialog("Error al Actualizar Personal", "Hubo un error al intentar actualizar al personal. Intentálo mas tarde.", Alert.AlertType.ERROR);
        }
    }

    private boolean updateAdministrative(AdministrativeStaff administrativeStaff){
        int rowsAffected = AdministrativeStaffDAO.updateAdministrativeStaff(administrativeStaff);
        return rowsAffected > 0;
    }

    private AdministrativeStaff validateFields(){
        String name = tfName.getText().trim();
        String firstLastName = tfFirstLastName.getText().trim();
        String secondLastName = tfSecondLastName.getText().trim();
        String institutionalEmail = tfInstitutionalEmail.getText().trim();

        AdministrativeStaff administrativeStaff = new AdministrativeStaff();
        administrativeStaff.setName(name);
        administrativeStaff.setFirstLastName(firstLastName);
        administrativeStaff.setSecondLastName(secondLastName);
        administrativeStaff.setInstitutionalEmail(institutionalEmail);

        return administrativeStaff;
    }

    private void blockTextFields(){
        tfName.setEditable(false);
        tfFirstLastName.setEditable(false);
        tfSecondLastName.setEditable(false);
        tfInstitutionalEmail.setEditable(false);
    }

    private void configureSeeForm(){
        blockTextFields();
        btnRegisterAdministrative.setVisible(false);
    }

    private void setTextFieldsWithAdministrativeInformation(AdministrativeStaff administrativeStaff){
        tfName.setText(administrativeStaff.getName());
        tfFirstLastName.setText(administrativeStaff.getFirstLastName());
        tfSecondLastName.setText(administrativeStaff.getSecondLastName());
        tfInstitutionalEmail.setText(administrativeStaff.getInstitutionalEmail());
    }

    public void seeUser(int idUser){
        this.idAdministrativeStaff = idUser;
        AdministrativeStaff administrativeStaff = AdministrativeStaffDAO.getAdministrativeById(idUser);
        if(administrativeStaff != null){
            configureSeeForm();
            setTextFieldsWithAdministrativeInformation(administrativeStaff);
        }
    }

    private void saveUser(int idAdministrative){
        String username = tfName.getText() + "_" + tfFirstLastName.getText();
        username = username.replaceAll("\\s", "");
        String password = tfName.getText() + "_" + tfFirstLastName.getText();
        password = password.replaceAll("\\s", "");
        int userType = Constants.ADMINISTRATIVE_STAFF;

        AdministrativeStaff administrativeStaff = new AdministrativeStaff();
        administrativeStaff.setUsername(username);
        administrativeStaff.setPassword(password);
        administrativeStaff.setIdUserType(userType);
        administrativeStaff.setIdAdministrativeStaff(idAdministrative);

        UserDAO.saveUser(administrativeStaff);
    }

    private void saveAdministrativeStaff(AdministrativeStaff administrativeStaff) {

        AdministrativeStaffDAO.saveAdministrative(administrativeStaff);
        AdministrativeStaff lastAdministrativeRegistered = AdministrativeStaffDAO.getLastAdministrativeRegistered();

        saveUser(lastAdministrativeRegistered.getIdAdministrativeStaff());

        cleanFields();
        Utilities.showSimpleDialog("Administrativo Guardado", "El personal administrativo se guardó exitosamente", Alert.AlertType.INFORMATION);

    }

    private void cleanFields(){
        tfName.setText("");
        tfFirstLastName.setText("");
        tfSecondLastName.setText("");
        tfInstitutionalEmail.setText("");
    }

    public void editUser(int idAdministrativeStaff){
        AdministrativeStaff administrativeStaff = AdministrativeStaffDAO.getAdministrativeById(idAdministrativeStaff);
        this.idAdministrativeStaff = idAdministrativeStaff;

        if(administrativeStaff != null){
            configureEditForm();
            setTextFieldsWithAdministrativeInformation(administrativeStaff);
        }
    }

    private  void configureEditForm(){
        btnRegisterAdministrative.setVisible(false);
        btnModifyAdministrative.setVisible(true);
    }
}
