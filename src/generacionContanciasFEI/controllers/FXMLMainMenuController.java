package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.dao.TeacherDAO;
import model.pojo.Teacher;
import model.pojo.User;
import utils.Constants;
import utils.UserSingleton;
import utils.Utilities;

public class FXMLMainMenuController implements Initializable {
    @javafx.fxml.FXML
    private Button btnCobro;
    @javafx.fxml.FXML
    private Button btnTarjetas;
    @javafx.fxml.FXML
    private Button btnInfoMenuPrincipal;
    @FXML
    private AnchorPane apMainMenu;
    @FXML
    private AnchorPane apAdministrativeStaff;
    @FXML
    private AnchorPane apTeacher;
    @FXML
    private Button btnCobro1;
    @FXML
    private Label lbAdministrativeName;
    @FXML
    private Label lbTeacherName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureMainMenu();
    }

    @FXML
    public void clickTeachers(ActionEvent actionEvent) {
        Utilities.changePane(apMainMenu, "/views/FXMLConsultTeachers.fxml");
    }

    @FXML
    public void clickAdministartiveStaff(ActionEvent actionEvent) {
        Utilities.changePane(apMainMenu, "/views/ConsultAdministrativeStaff.fxml");
    }

    @FXML
    public void clickGenerateRecord(ActionEvent actionEvent) {
        Utilities.changePane(apMainMenu, "/views/FXMLGenerateRecord.fxml");
    }

    @FXML
    public void clickLogout(ActionEvent actionEvent) {
        goToLogin();
    }

    @FXML
    public void clickUpdateDigitalSign(ActionEvent actionEvent) {
        Utilities.changePane(apMainMenu, "/views/FXMLDigitalSign.fxml");
    }

    private void goToLogin(){
        Stage baseStage = (Stage) apTeacher.getScene().getWindow();
        baseStage.setScene(Utilities.initializeScene("/views/FXMLLogin.fxml"));
        Utilities.centerStage(baseStage);
        baseStage.setTitle("Inicio de Sesión");
        baseStage.show();
    }

    private void configureMainMenu(){
        User user = UserSingleton.getInstance().getUser();
        if(user.getIdUserType() == Constants.TEACHER){
            Teacher currentTeacher = TeacherDAO.getTeacherById(user.getIdTeacher());
            UserSingleton.getInstance().setUser(currentTeacher);
            apTeacher.setVisible(true);
            lbTeacherName.setText(currentTeacher.getName());
        } else {
            apAdministrativeStaff.setVisible(true);
            lbAdministrativeName.setText(user.getUsername());
        }
    }
}
