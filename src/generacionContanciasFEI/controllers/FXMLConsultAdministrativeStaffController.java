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
import model.dao.AdministrativeStaffDAO;
import model.dao.TeacherDAO;
import model.pojo.AdministrativeStaff;
import model.pojo.AdministrativeStaffResponse;
import model.pojo.Teacher;
import model.pojo.TeacherResponse;
import utils.Constants;
import utils.Utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLConsultAdministrativeStaffController implements Initializable {
    @javafx.fxml.FXML
    private AnchorPane apConsultAdministrativeStaff;
    @javafx.fxml.FXML
    private TextField tfName;
    @javafx.fxml.FXML
    private TableView<AdministrativeStaff> tvAdministrativeStaff;
    @javafx.fxml.FXML
    private TableColumn<AdministrativeStaff, String> tcAdministrativeStaff;
    @FXML
    private Button btnRegisterAdministrativeStaff;
    @FXML
    private Button btnModifyUser;
    @FXML
    private Button btnDeleteUser;
    @FXML
    private Button btnSeeUser;

    private ObservableList<AdministrativeStaff> observableAdministratives;
    private FilteredList<AdministrativeStaff> administraticesFiltered;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAdministratives();
        setListenerToTextFields();
        setListenerToTableView();
    }

    @FXML
    public void clickModifyUser(ActionEvent actionEvent) {
        AdministrativeStaff selectedUser = tvAdministrativeStaff.getSelectionModel().getSelectedItem();
        FXMLAdministrativeStaffFormController administrativeStaffFormController = Utilities.changePane(apConsultAdministrativeStaff, "/views/FXMLAdministrativeStaffForm.fxml");
        administrativeStaffFormController.editUser(selectedUser.getIdAdministrativeStaff());
    }

    @FXML
    public void clickDeleteUser(ActionEvent actionEvent) {
        int dialogResponse = Utilities.showQuestionDialog("Eliminar Personal", "¿Estás seguro de eliminar al personal?");

        if(dialogResponse == Constants.BTN_YES){
            AdministrativeStaff selectedUser = tvAdministrativeStaff.getSelectionModel().getSelectedItem();

            int response = AdministrativeStaffDAO.deleteAdministrativeStaff(selectedUser.getIdAdministrativeStaff());

            if(response == Constants.OPERATION_SUCCESFUL){
                observableAdministratives.remove(selectedUser);
                administraticesFiltered.getSource().remove(selectedUser);

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
    public void clickRegisterAdministrativeStaff(ActionEvent actionEvent) {
        Utilities.changePane(apConsultAdministrativeStaff, "/views/FXMLAdministrativeStaffForm.fxml");
    }

    @FXML
    public void clickSeeUser(ActionEvent actionEvent) {
        AdministrativeStaff selectedUser = tvAdministrativeStaff.getSelectionModel().getSelectedItem();
        FXMLAdministrativeStaffFormController administrativeStaffFormController = Utilities.changePane(apConsultAdministrativeStaff, "/views/FXMLAdministrativeStaffForm.fxml");
        administrativeStaffFormController.seeUser(selectedUser.getIdAdministrativeStaff());
    }

    @Deprecated
    public void clickImportAdministrativeStaff(ActionEvent actionEvent) {
    }

    private void getAdministratives(){
        AdministrativeStaffResponse administrativeStaffResponse = AdministrativeStaffDAO.getAdminstratives();
        switch (administrativeStaffResponse.getResponseCode()) {
            case Constants.CONNECTION_ERROR:
                Utilities.showSimpleDialog("Error de Conexión", "No se pudo conectar con la base de datos. "
                        + "Intente de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case (Constants.QUERY_ERROR):
                Utilities.showSimpleDialog("Error de Consulta", "Error en la consulta", Alert.AlertType.WARNING);
                break;
            case (Constants.OPERATION_SUCCESFUL):
                ArrayList<AdministrativeStaff> administrativeStaffs = administrativeStaffResponse.getAdministrativeStaffs();
                this.observableAdministratives = FXCollections.observableArrayList(administrativeStaffs);
                administraticesFiltered = new FilteredList<>(this.observableAdministratives, t -> true);
                tcAdministrativeStaff.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
                tvAdministrativeStaff.setItems(this.administraticesFiltered);
                break;
        }
    }

    private void setListenerToTextFields(){
        tfName.textProperty().addListener((observable, oldValue, newValue) -> {
            administraticesFiltered.setPredicate(teacher -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filter = newValue.toLowerCase();

                return teacher.getFullName().toLowerCase().contains(filter);
            });
        });
    }

    private void setListenerToTableView(){
        SelectionModel<AdministrativeStaff> selectionModel = tvAdministrativeStaff.getSelectionModel();

        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnSeeUser.setDisable(false);
                btnModifyUser.setDisable(false);
                btnDeleteUser.setDisable(false);
            } else {
                btnSeeUser.setDisable(true);
                btnModifyUser.setDisable(true);
                btnDeleteUser.setDisable(true);
            }
        });

    }
}
