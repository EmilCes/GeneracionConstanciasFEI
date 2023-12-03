package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.dao.*;
import model.pojo.*;
import utils.EeRecordGenerator;
import utils.ProjectRecordGenerator;
import utils.UserSingleton;
import utils.Utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLGenerateRecordController implements Initializable {
    @javafx.fxml.FXML
    private AnchorPane apRecordsForm;
    @javafx.fxml.FXML
    private RadioButton rbProject;
    @javafx.fxml.FXML
    private ToggleGroup tgRecords;
    @javafx.fxml.FXML
    private RadioButton rbEducationalExperience;
    @javafx.fxml.FXML
    private Button btnDonwloadRecord;
    @javafx.fxml.FXML
    private TableView<Project> tvProjects;
    @javafx.fxml.FXML
    private TableColumn<Project, String> tcProjectsName;
    @javafx.fxml.FXML
    private Pane pnProjects;
    @javafx.fxml.FXML
    private TextField tfProjectDoneName;
    @javafx.fxml.FXML
    private Pane pnEducativeExperiences;
    @javafx.fxml.FXML
    private Button btnDonwloadEducativeExperienceRecord;
    @javafx.fxml.FXML
    private TableView<EducativeExperience> tvEducativeExperiences;
    @javafx.fxml.FXML
    private TableColumn<EducativeExperience, String> tcEducativeExperiences;
    @javafx.fxml.FXML
    private TextField tfEducativeExperience;

    private FilteredList<Project> projectFiltered;
    private FilteredList<EducativeExperience> educativeExperienceFiltered;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeProjectsTableView();
        initializeEducativeExperiencesTableView();
        setListenerToTableView();
        setListenerToTextField();
        getProjects();
        getEducativeExperiences();
        initializeToggleGroup();
    }

    @javafx.fxml.FXML
    public void clickDownloadRecord(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnDonwloadRecord.getScene().getWindow();
        Project selectedProject = tvProjects.getSelectionModel().getSelectedItem();
        ProjectStudentResponse projectStudentResponse = ProjectStudentDAO.getProjectsStudents(selectedProject.getIdProject());
        List<Student> students = new ArrayList<>();

        for(ProjectStudent projectStudent : projectStudentResponse.getProjectStudents()){
            Student studentBD = StudentDAO.getStudentById(projectStudent.getIdStudent());
            students.add(studentBD);
        }

        ProjectRecordGenerator.openDirectoryChooserAndConvert(currentStage, selectedProject, students);
    }

    private void initializeProjectsTableView(){
        tcProjectsName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProjectDone()));
    }

    private void initializeEducativeExperiencesTableView(){
        tcEducativeExperiences.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    }

    private void getProjects(){
        int idTeacher = UserSingleton.getInstance().getUser().getIdTeacher();
        ProjectResponse projectResponse = ProjectDAO.getProjects(idTeacher);
        ObservableList<Project> observableProjects = FXCollections.observableArrayList(projectResponse.getProjects());
        projectFiltered = new FilteredList<>(observableProjects, t -> true);

        tvProjects.setItems(projectFiltered);
    }

    private void getEducativeExperiences(){
        int idTeacher = UserSingleton.getInstance().getUser().getIdTeacher();
        EducativeExperienceResponse educativeExperienceResponse = EducativeExperienceDAO.getEducativeExperiences(idTeacher);
        ObservableList<EducativeExperience> observableEducativeExperiences = FXCollections.observableArrayList(educativeExperienceResponse.getEducativeExperiences());
        educativeExperienceFiltered = new FilteredList<>(observableEducativeExperiences, t -> true);

        tvEducativeExperiences.setItems(educativeExperienceFiltered);
    }

    private void setListenerToTableView(){
        SelectionModel<Project> selectionModel = tvProjects.getSelectionModel();

        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnDonwloadRecord.setDisable(false);
            } else {
                btnDonwloadRecord.setDisable(true);
            }
        });

        SelectionModel<EducativeExperience> experienceSelectionModel = tvEducativeExperiences.getSelectionModel();

        experienceSelectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnDonwloadEducativeExperienceRecord.setDisable(false);
            } else {
                btnDonwloadEducativeExperienceRecord.setDisable(true);
            }
        });
    }

    private void setListenerToTextField(){
        tfProjectDoneName.textProperty().addListener((observable, oldValue, newValue) -> {
            projectFiltered.setPredicate(teacher -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filter = newValue.toLowerCase();

                return teacher.getProjectDone().toLowerCase().contains(filter);
            });
        });

        tfEducativeExperience.textProperty().addListener((observable, oldValue, newValue) -> {
            educativeExperienceFiltered.setPredicate(educativeExperience -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filter = newValue.toLowerCase();

                return educativeExperience.getName().toLowerCase().contains(filter);
            });
        });
    }

    private void initializeToggleGroup(){
        tgRecords.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) {
                if (tgRecords.getSelectedToggle() != null) {
                    RadioButton selectedRadioButton = (RadioButton) tgRecords.getSelectedToggle();
                    if(selectedRadioButton.getText().equals("Proyecto de Campo")){
                        showProjects();
                    } else{
                        showEducativeExperiences();
                    }
                }
            }
        });
    }

    private void showProjects(){
        pnEducativeExperiences.setVisible(false);
        pnProjects.setVisible(true);
    }

    private void showEducativeExperiences(){
        pnProjects.setVisible(false);
        pnEducativeExperiences.setVisible(true);
    }

    @javafx.fxml.FXML
    public void clickDownloadEducativeExperienceRecord(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnDonwloadEducativeExperienceRecord.getScene().getWindow();
        EducativeExperience selectedEducationalExperience = tvEducativeExperiences.getSelectionModel().getSelectedItem();
        EducationalProgram educationalProgram = EducationalProgramDAO.getEducationalProgramById(selectedEducationalExperience.getIdEducationalProgram());

        EeRecordGenerator.openDirectoryChooserAndConvert(currentStage, selectedEducationalExperience, educationalProgram);
    }
}
