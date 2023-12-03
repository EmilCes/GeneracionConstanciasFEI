package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import model.dao.TeacherDAO;
import model.dao.UserDAO;
import model.pojo.Teacher;
import utils.Constants;
import utils.Utilities;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class FXMLTeacherFormController implements Initializable {
    @FXML
    private AnchorPane apRegisterTeacher;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfFirstLastName;
    @FXML
    private TextField tfSecondLastName;
    @FXML
    private TextField tfPersonalNumber;
    @FXML
    private TextField tfInstitutionalEmail;
    @FXML
    private TextField tfAlternateEmail;
    @FXML
    private DatePicker dpBirthdate;
    @FXML
    private Button btnRegisterTeacher;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnModifyTeacher;
    @FXML
    private Button btnRegisterInformation;

    private int idTeacher;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void cleanFields(){
        tfName.setText("");
        tfFirstLastName.setText("");
        tfSecondLastName.setText("");
        tfPersonalNumber.setText("");
        tfInstitutionalEmail.setText("");
        tfAlternateEmail.setText("");
        dpBirthdate.setValue(null);
    }

    private Teacher validateFields(){
        String name = tfName.getText().trim();
        String firstLastName = tfFirstLastName.getText().trim();
        String secondLastName = tfSecondLastName.getText().trim();
        int personalNumber = Integer.parseInt(tfPersonalNumber.getText().trim());
        String institutionalEmail = tfInstitutionalEmail.getText().trim();
        String alternativeEmail = tfAlternateEmail.getText().trim();

        String birthdate = "";
        LocalDate selectedDate = dpBirthdate.getValue();
        if (selectedDate != null) {
            birthdate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setFirstLastName(firstLastName);
        teacher.setSecondLastName(secondLastName);
        teacher.setInstitutionalEmail(institutionalEmail);
        teacher.setAlternativeEmail(alternativeEmail);
        teacher.setBirthdate(birthdate);
        teacher.setPersonalNumber(personalNumber);

        return teacher;
    }

    private void saveUser(int idTeacher){
        String username = tfName.getText() + "_" + tfFirstLastName.getText();
        username = username.replaceAll("\\s", "");
        String password = tfName.getText() + "_" + tfFirstLastName.getText();
        password = password.replaceAll("\\s", "");
        int userType = Constants.TEACHER;

        Teacher teacher = new Teacher();
        teacher.setUsername(username);
        teacher.setPassword(password);
        teacher.setIdUserType(userType);
        teacher.setIdTeacher(idTeacher);

        UserDAO.saveUser(teacher);
    }

    private void saverTeacher(Teacher teacher) {

        TeacherDAO.saveTeacher(teacher);
        Teacher lastTeacherResgistered = TeacherDAO.getLastTeacherRegistered();

        saveUser(lastTeacherResgistered.getIdTeacher());



        cleanFields();
        Utilities.showSimpleDialog("Profesor Guardado", "El profesor se guardó exitosamente", Alert.AlertType.INFORMATION);

    }

    private boolean updateTeacher(Teacher teacher){
        int rowsAffected = TeacherDAO.updateTeacher(teacher);
        return rowsAffected > 0;
    }

    @FXML
    public void clickRegisterTeacher(ActionEvent actionEvent) {
        Teacher teacher = validateFields();
        if(teacher != null){
            saverTeacher(teacher);
        }
    }

    @FXML
    public void clickCancel(ActionEvent actionEvent) {
        Utilities.changePane(apRegisterTeacher, "/views/FXMLConsultTeachers.fxml");
    }

    @FXML
    public void clickBack(ActionEvent actionEvent) {
        Utilities.changePane(apRegisterTeacher, "/views/FXMLConsultTeachers.fxml");
    }

    @FXML
    public void clickModifyTeacherInformation(ActionEvent actionEvent) {
        Teacher teacher = validateFields();
        teacher.setIdTeacher(this.idTeacher);
        boolean teacherUpdated = updateTeacher(teacher);

        if(teacherUpdated){
            Utilities.showSimpleDialog("Profesor Actualizado", "El profesor se actualizo exitosamente", Alert.AlertType.INFORMATION);
        } else{
            Utilities.showSimpleDialog("Error al Actualizar Profesor", "Hubo un error al intentar actualizar al profesor. Intentálo mas tarde.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void clickRegisterInformation(ActionEvent actionEvent) {
        FXMLRecordsFormController recordsFormController = Utilities.changePane(apRegisterTeacher, "/views/FXMLRecordsForm.fxml");
        recordsFormController.setTeacherId(idTeacher);
    }

    public void editTeacher(int idTeacher){
        Teacher teacher = TeacherDAO.getTeacherById(idTeacher);
        this.idTeacher = idTeacher;

        if(teacher != null){
            configureEditForm();
            setTextFieldsWithTeacherInformation(teacher);
        }
    }

    public void seeTeacher(int idTeacher){
        this.idTeacher = idTeacher;
        Teacher teacher = TeacherDAO.getTeacherById(idTeacher);
        if(teacher != null){
            configureSeeForm();
            setTextFieldsWithTeacherInformation(teacher);
        }
    }

    private void blockTextFields(){
        tfName.setEditable(false);
        tfFirstLastName.setEditable(false);
        tfSecondLastName.setEditable(false);
        tfPersonalNumber.setEditable(false);
        tfInstitutionalEmail.setEditable(false);
        tfAlternateEmail.setEditable(false);
        dpBirthdate.setEditable(false);
    }

    private void configureSeeForm(){
        blockTextFields();
        btnRegisterTeacher.setVisible(false);
        btnCancel.setVisible(false);
        btnBack.setVisible(true);
        btnRegisterInformation.setVisible(true);
    }

    private  void configureEditForm(){
        btnRegisterTeacher.setVisible(false);
        btnModifyTeacher.setVisible(true);
    }

    private void setTextFieldsWithTeacherInformation(Teacher teacher){
        tfName.setText(teacher.getName());
        tfFirstLastName.setText(teacher.getFirstLastName());
        tfSecondLastName.setText(teacher.getSecondLastName());
        tfPersonalNumber.setText(String.valueOf(teacher.getPersonalNumber()));
        tfInstitutionalEmail.setText(teacher.getInstitutionalEmail());
        tfAlternateEmail.setText(teacher.getAlternativeEmail());

        LocalDate date = LocalDate.parse(teacher.getBirthdate(), DateTimeFormatter.ISO_LOCAL_DATE);
        dpBirthdate.setValue(date);
    }
}
