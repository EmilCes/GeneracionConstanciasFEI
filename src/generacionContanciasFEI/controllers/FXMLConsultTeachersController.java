package controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.dao.TeacherDAO;
import model.pojo.Teacher;
import model.pojo.TeacherResponse;
import utils.Constants;
import utils.ImportTeachers;
import utils.Utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLConsultTeachersController implements Initializable {

    private ObservableList<Teacher> observableTeachers;
    private FilteredList<Teacher> teachersFiltered;

    @FXML
    private AnchorPane apConsultTeachers;
    @FXML
    private TableColumn<Teacher, Integer> tcPersonalNumber;
    @FXML
    private TableColumn<Teacher, String> tcTeachers;
    @FXML
    private TableView<Teacher> tvTeachers;
    @FXML
    private TextField tfTeacherNameSearch;
    @FXML
    private TextField tfPersonalNumberSearch;
    @FXML
    private Button btnModifyTeacher;
    @FXML
    private Button btnDeleteTeacher;
    @FXML
    private Button btnSeeTeacher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTeachers();
        setListenerToTextFieldS();
        setListenerToTableView();
    }

    private void getTeachers(){
        TeacherResponse teacherResponse = TeacherDAO.getTeachers();
        switch (teacherResponse.getResponseCode()) {
            case Constants.CONNECTION_ERROR:
                Utilities.showSimpleDialog("Error de Conexión", "No se pudo conectar con la base de datos. "
                        + "Intente de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case (Constants.QUERY_ERROR):
                Utilities.showSimpleDialog("Error de Consulta", "Error en la consulta", Alert.AlertType.WARNING);
                break;
            case (Constants.OPERATION_SUCCESFUL):
                ArrayList<Teacher> teachers = teacherResponse.getTeachers();
                this.observableTeachers = FXCollections.observableArrayList(teachers);
                teachersFiltered = new FilteredList<>(this.observableTeachers, t -> true);


                tcPersonalNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPersonalNumber()).asObject());
                tcTeachers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));

                tvTeachers.setItems(this.teachersFiltered);
                break;
        }
    }

    private void setListenerToTextFieldS(){
        tfTeacherNameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            teachersFiltered.setPredicate(teacher -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filter = newValue.toLowerCase();

                return teacher.getFullName().toLowerCase().contains(filter);
            });
        });

        tfPersonalNumberSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            teachersFiltered.setPredicate(teacher -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                try{
                    int filter = Integer.parseInt(newValue);
                    return String.valueOf(teacher.getPersonalNumber()).contains(String.valueOf(filter));
                } catch (NumberFormatException ex){
                    return false;
                }
            });
        });
    }

    private void setListenerToTableView(){
        SelectionModel<Teacher> selectionModel = tvTeachers.getSelectionModel();

        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnSeeTeacher.setDisable(false);
                btnModifyTeacher.setDisable(false);
                btnDeleteTeacher.setDisable(false);
            } else {
                btnSeeTeacher.setDisable(true);
                btnModifyTeacher.setDisable(true);
                btnDeleteTeacher.setDisable(true);
            }
        });

    }

    @Deprecated
    public void clickRegisterTeacher(ActionEvent actionEvent) {
        Utilities.changePane(apConsultTeachers, "/views/FXMLTeacherForm.fxml");
    }

    @FXML
    public void clickSeeTeacher(ActionEvent actionEvent) {
        Teacher selectedTeacher = tvTeachers.getSelectionModel().getSelectedItem();
        FXMLTeacherFormController teacherFormController = Utilities.changePane(apConsultTeachers, "/views/FXMLTeacherForm.fxml");
        teacherFormController.seeTeacher(selectedTeacher.getIdTeacher());
    }

    @FXML
    public void clickModifyTeacher(ActionEvent actionEvent) {
        Teacher selectedTeacher = tvTeachers.getSelectionModel().getSelectedItem();
        FXMLTeacherFormController teacherFormController = Utilities.changePane(apConsultTeachers, "/views/FXMLTeacherForm.fxml");
        teacherFormController.editTeacher(selectedTeacher.getIdTeacher());
    }

    @FXML
    public void clickDeleteTeacher(ActionEvent actionEvent) {
        int dialogResponse = Utilities.showQuestionDialog("Eliminar Profesor", "¿Estás seguro de eliminar al profesor?");

        if(dialogResponse ==Constants.BTN_YES){
            Teacher selectedTeacher = tvTeachers.getSelectionModel().getSelectedItem();

            int response = TeacherDAO.deleteTeacher(selectedTeacher.getIdTeacher());

            if(response == Constants.OPERATION_SUCCESFUL){
                observableTeachers.remove(selectedTeacher);
                teachersFiltered.getSource().remove(selectedTeacher);

                Utilities.showSimpleDialog("Profesor Eliminado",
                        "El profesor fue eliminado con éxito.",
                        Alert.AlertType.INFORMATION);
            } else{
                Utilities.showSimpleDialog("Error al Eliminar",
                        "Hubo un error al eliinar el profesor. Intentálo mas tarde.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void clickImportTeachers(ActionEvent actionEvent) {
        ImportTeachers importTeachers = new ImportTeachers();
        importTeachers.importTeachers();
        Utilities.changePane(apConsultTeachers, "/views/FXMLConsultTeachers.fxml");
    }

}
