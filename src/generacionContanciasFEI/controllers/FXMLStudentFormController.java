package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.dao.StudentDAO;
import model.dao.TeacherDAO;
import model.pojo.Student;
import model.pojo.Teacher;
import utils.Constants;
import utils.Utilities;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class FXMLStudentFormController implements Initializable {
    @javafx.fxml.FXML
    private AnchorPane apStudentForm;
    @javafx.fxml.FXML
    private TextField tfName;
    @javafx.fxml.FXML
    private TextField tfFirstLastName;
    @javafx.fxml.FXML
    private TextField tfSecondLastName;
    @javafx.fxml.FXML
    private TextField tfTuition;
    @javafx.fxml.FXML
    private Button btnRegisterStudent;
    @javafx.fxml.FXML
    private Button btnCancel;

    private int teacherId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void clickRegisterStudent(ActionEvent actionEvent) {
        Student student = validateFields();
        if(student != null){{
            saveStudent(student);
        }}
    }

    @javafx.fxml.FXML
    public void clickCancel(ActionEvent actionEvent) {
        FXMLRecordsFormController recordsFormController = Utilities.changePane(apStudentForm, "/views/FXMLRecordsForm.fxml");
        recordsFormController.setTeacherId(this.teacherId);
    }

    private void cleanFields(){
        tfName.setText("");
        tfFirstLastName.setText("");
        tfSecondLastName.setText("");
        tfTuition.setText("");
    }

    private Student validateFields(){
        String name = tfName.getText().trim();
        String firstLastName = tfFirstLastName.getText().trim();
        String secondLastName = tfSecondLastName.getText().trim();
        String tuition = tfTuition.getText().trim();

        Student student = new Student();
        student.setName(name);
        student.setFirstLastName(firstLastName);
        student.setSecondLastName(secondLastName);
        student.setTuition(tuition);

        return student;
    }

    private void saveStudent(Student student){
        int response = StudentDAO.saveStudent(student);
        if (response == Constants.OPERATION_SUCCESFUL) {
            cleanFields();
            Utilities.showSimpleDialog("Estudiante Guardado", "El estudiante se guardó exitosamente", Alert.AlertType.INFORMATION);
        } else {
            Utilities.showSimpleDialog("Error al Guardar al Profesor", "Ha habido un error al guardar al estudiante. Intentálo mas tarde.", Alert.AlertType.ERROR);
        }
    }

    public void setTeacherId(int idTeacher){
        this.teacherId = idTeacher;
    }
}
