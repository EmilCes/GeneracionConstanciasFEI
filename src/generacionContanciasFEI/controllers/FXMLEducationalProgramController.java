package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.dao.EducationalProgramDAO;
import model.pojo.EducationalProgram;
import utils.Constants;
import utils.Utilities;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLEducationalProgramController implements Initializable {
    @javafx.fxml.FXML
    private TextField tfName;
    @javafx.fxml.FXML
    private Button btnRegisterEducationalProgram;
    @javafx.fxml.FXML
    private Button btnCancel;
    @javafx.fxml.FXML
    private AnchorPane apEducationalProgram;

    int idTeacher;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void clickRegisterEducationalProgram(ActionEvent actionEvent) {
        EducationalProgram educationalProgram = validateFields();

        if(educationalProgram != null){
            int rowsAffected = EducationalProgramDAO.saveEducationalProgram(educationalProgram);
            if(rowsAffected == Constants.OPERATION_SUCCESFUL){
                Utilities.showSimpleDialog("Programa Educativo Registrado Correctamente",
                        "El programa educativo fue registrado correctamente", Alert.AlertType.INFORMATION);
            }
        }
    }

    @javafx.fxml.FXML
    public void clickCancel(ActionEvent actionEvent) {
        FXMLRecordsFormController recordsFormController = Utilities.changePane(apEducationalProgram, "/views/FXMLRecordsForm.fxml");
        recordsFormController.setTeacherId(idTeacher);
    }

    private EducationalProgram validateFields(){
        String educationalProgramName = tfName.getText();

        EducationalProgram educationalProgram = new EducationalProgram();
        educationalProgram.setEducationalProgramName(educationalProgramName);

        return educationalProgram;
    }

    public void setTeacherId(int teacherId){
        this.idTeacher = teacherId;
    }
}
