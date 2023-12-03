package controllers;

import com.mysql.jdbc.Util;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import model.dao.*;
import model.pojo.*;
import utils.Constants;
import utils.Utilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLRecordsFormController implements Initializable {
    @javafx.fxml.FXML
    private AnchorPane apRecordsForm;
    @javafx.fxml.FXML
    private RadioButton rbProject;
    @javafx.fxml.FXML
    private ToggleGroup tgRecords;
    @javafx.fxml.FXML
    private Button btnBack;
    @javafx.fxml.FXML
    private RadioButton rbEducationalExperience;
    @javafx.fxml.FXML
    private TableView<Project> tvProjects;
    @javafx.fxml.FXML
    private TableColumn<Project, String> tcProjectsName;
    @javafx.fxml.FXML
    private Pane pnNewProject;
    @javafx.fxml.FXML
    private TextField tfProjectDone;
    @javafx.fxml.FXML
    private TextField tfDuration;
    @javafx.fxml.FXML
    private TextField tfDevelopmentPlace;
    @javafx.fxml.FXML
    private TextArea tfImpactObtained;
    @javafx.fxml.FXML
    private TableView<Student> tvStudents;
    @javafx.fxml.FXML
    private TableColumn<Student, String> tcStudentName;
    @javafx.fxml.FXML
    private ComboBox<Student> cbStudents;
    @javafx.fxml.FXML
    private Button btnNewStudent;
    @javafx.fxml.FXML
    private Button btnRegisteProject;
    @javafx.fxml.FXML
    private Button btnAddStudent;
    @javafx.fxml.FXML
    private Pane pnEducativeExperience;
    @javafx.fxml.FXML
    private TextField tfEducativeExperienceName;
    @javafx.fxml.FXML
    private TextField tfBlock;
    @javafx.fxml.FXML
    private TextField tfSection;
    @javafx.fxml.FXML
    private ComboBox<EducationalProgram> cbEducativePrograms;
    @javafx.fxml.FXML
    private TextField tfCredits;
    @javafx.fxml.FXML
    private TableView<EducativeExperience> tvEducativeExperiences;
    @javafx.fxml.FXML
    private TableColumn<EducativeExperience, String> tcEducativeExperience;
    @javafx.fxml.FXML
    private Button btnNewEducativeExperience;

    private ObjectProperty<Student> selectedOption;
    private int teacherId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeStudentsComboBox();
        initializeEducationalProgramsComboBox();
        initializeStudentsTableView();
        initializeProjectsTableView();
        initializeToggleGroup();
    }

    @javafx.fxml.FXML
    public void clickBack(ActionEvent actionEvent) {
        FXMLTeacherFormController teacherFormController = Utilities.changePane(apRecordsForm, "/views/FXMLTeacherForm.fxml");
        teacherFormController.seeTeacher(teacherId);
    }

    @javafx.fxml.FXML
    public void clickNewStudent(ActionEvent actionEvent) {
        FXMLStudentFormController studentFormController = Utilities.changePane(apRecordsForm, "/views/FXMLStudentForm.fxml");
        studentFormController.setTeacherId(this.teacherId);
    }

    @javafx.fxml.FXML
    public void clickRegisterProject(ActionEvent actionEvent) {
        Project project = validateProjectFields();
        boolean isEmptyTable = studentsTableIsEmpty();
        if(project != null && !isEmptyTable){
            int response = ProjectDAO.saveProject(project);

            if(response == Constants.OPERATION_SUCCESFUL){
                Project projectResponse = ProjectDAO.getLastProjectRegisteredByIdTeacher(teacherId);
                if(projectResponse.getResponseCode() == Constants.OPERATION_SUCCESFUL){
                    List<Student> studentsList = tvStudents.getItems();
                    for (Student student : studentsList){
                        ProjectStudentDAO.saveProjectStudent(new ProjectStudent(projectResponse.getIdProject(), student.getIdEstudiante()));
                    }
                    tvProjects.getItems().add(project);
                    Utilities.showSimpleDialog("Proyecto Registrado",
                            "El Proyecto de Campo fue registrado correctamente.", Alert.AlertType.INFORMATION);
                } else{
                    Utilities.showSimpleDialog("Error con el Servidor",
                            "Hubo un error de conexión con la base de datos.", Alert.AlertType.ERROR);
                }
            } else{
                Utilities.showSimpleDialog("Error con el Servidor",
                        "Hubo un error de conexión con la base de datos.", Alert.AlertType.ERROR);
            }
        }
    }

    @javafx.fxml.FXML
    public void clickAddStudent(ActionEvent actionEvent) {
        Student selectedStudent = cbStudents.getSelectionModel().getSelectedItem();
        boolean alreadyInTable = studentIsInTheTable(selectedStudent);

        if(!alreadyInTable){
            tvStudents.getItems().add(selectedStudent);
        } else {
            Utilities.showSimpleDialog("Estudiante en la tabla",
                    "El estudiante ya se encuentra en la tabla.", Alert.AlertType.ERROR);
        }
    }

    private void initializeEducationalProgramsComboBox(){
        EducationalProgramResponse educationalProgramResponse = EducationalProgramDAO.getEducationalPrograms();

        if (educationalProgramResponse.getResponseCode() == Constants.OPERATION_SUCCESFUL) {
            List<EducationalProgram> educationalProgramList = educationalProgramResponse.getEducationalPrograms();
            cbEducativePrograms.setItems(FXCollections.observableArrayList(educationalProgramList));
        } else {
            Utilities.showSimpleDialog("Error al Recuperar Información",
                    "Hubo un error al intentar recuperar la información.", Alert.AlertType.ERROR);
        }
    }

    private void initializeToggleGroup(){
        tgRecords.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) {
                if (tgRecords.getSelectedToggle() != null) {
                    RadioButton selectedRadioButton = (RadioButton) tgRecords.getSelectedToggle();
                    if(selectedRadioButton.getText().equals("Proyecto de Campo")){
                        showProjectFields();
                    } else{
                        showEducativeExperiencesFields();
                    }
                }
            }
        });
    }

    private void clearRecordsViews(){
        pnNewProject.setVisible(false);
        tvProjects.setVisible(false);
        pnEducativeExperience.setVisible(false);
        tvEducativeExperiences.setVisible(false);
    }

    private void showProjectFields(){
        clearRecordsViews();
        pnNewProject.setVisible(true);
        tvProjects.setVisible(true);
    }

    private void showEducativeExperiencesFields(){
        clearRecordsViews();
        pnEducativeExperience.setVisible(true);
        tvEducativeExperiences.setVisible(true);
    }

    private void initializeProjectsTableView(){
        tcProjectsName.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        cellData.getValue().getProjectDone()));

        tcEducativeExperience.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        cellData.getValue().getName()));
    }
    private void initializeStudentsTableView() {
        tcStudentName.setCellValueFactory(cellData ->
                Bindings.createStringBinding(() ->
                        cellData.getValue().getFullName()));
    }

    private void initializeStudentsComboBox() {
        StudentResponse studentResponse = StudentDAO.getStudents();

        if (studentResponse.getResponseCode() == Constants.OPERATION_SUCCESFUL) {
            List<Student> studentsList = studentResponse.getStudents();
            cbStudents.setItems(FXCollections.observableArrayList(studentsList));

            cbStudents.setEditable(true);

            cbStudents.setConverter(new StringConverter<Student>() {
                @Override
                public String toString(Student student) {
                    return student == null ? "" : student.getFullName();
                }

                @Override
                public Student fromString(String string) {
                    return findStudentByString(string, studentsList);
                }
            });

            cbStudents.getEditor().addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                String filter = cbStudents.getEditor().getText();
                updateStudentsComboBox(cbStudents, filter, studentsList);
            });

            cbStudents.valueProperty().addListener((observable, oldValue, newValue) -> {
                selectedOption.set(newValue);
            });

            selectedOption = new SimpleObjectProperty<>();
            btnAddStudent.disableProperty().bind(Bindings.isNull(selectedOption));
        } else {
            Utilities.showSimpleDialog("Error al Recuperar Información",
                    "Hubo un error al intentar recuperar la información.", Alert.AlertType.ERROR);
        }
    }

    private Student findStudentByString(String searchString, List<Student> studentsList) {
        for (Student student : studentsList) {
            if (student.toString().equalsIgnoreCase(searchString)) {
                return student;
            }
        }
        return null;
    }

    private void updateStudentsComboBox(ComboBox<Student> comboBox, String filter, List<Student> studentsList) {
        comboBox.getItems().clear();

        for (Student student : studentsList) {
            if (student.getFullName().toLowerCase().contains(filter.toLowerCase())) {
                comboBox.getItems().add(student);
            }
        }

        comboBox.show();
    }

    private boolean studentIsInTheTable(Student student){
        return tvStudents.getItems().stream()
                .anyMatch(existingStudent -> existingStudent.equals(student));
    }

    public void setTeacherId(int teacherId){
        this.teacherId = teacherId;
        getProjects();
        getEducativeExperiences();
    }

    private void getProjects(){
        ProjectResponse projectResponse = ProjectDAO.getProjects(teacherId);
        switch (projectResponse.getResponseCode()) {
            case Constants.CONNECTION_ERROR:
                Utilities.showSimpleDialog("Error de Conexión", "No se pudo conectar con la base de datos. "
                        + "Intente de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case (Constants.QUERY_ERROR):
                Utilities.showSimpleDialog("Error de Consulta", "Error en la consulta", Alert.AlertType.WARNING);
                break;
            case (Constants.OPERATION_SUCCESFUL):
                ArrayList<Project> projects = projectResponse.getProjects();
                ObservableList<Project> observableProjects = FXCollections.observableArrayList(projects);

                tvProjects.setItems(observableProjects);
                break;
        }
    }

    private void getEducativeExperiences(){
        EducativeExperienceResponse educativeExperienceResponse = EducativeExperienceDAO.getEducativeExperiences(teacherId);
        switch (educativeExperienceResponse.getResponseCode()) {
            case Constants.CONNECTION_ERROR:
                Utilities.showSimpleDialog("Error de Conexión", "No se pudo conectar con la base de datos. "
                        + "Intente de nuevo o hágalo más tarde", Alert.AlertType.ERROR);
                break;
            case (Constants.QUERY_ERROR):
                Utilities.showSimpleDialog("Error de Consulta", "Error en la consulta", Alert.AlertType.WARNING);
                break;
            case (Constants.OPERATION_SUCCESFUL):
                ArrayList<EducativeExperience> educativeExperiences = educativeExperienceResponse.getEducativeExperiences();
                ObservableList<EducativeExperience> observableExperiences = FXCollections.observableArrayList(educativeExperiences);

                tvEducativeExperiences.setItems(observableExperiences);
                break;
        }
    }

    private Project validateProjectFields(){
        String projectDone = tfProjectDone.getText();
        String duration = tfDuration.getText();
        String place = tfDevelopmentPlace.getText();
        String impactObtained = tfImpactObtained.getText();

        //VALIDACIONES AQUI

        return new Project(projectDone, duration, place, impactObtained, this.teacherId);
    }

    private boolean studentsTableIsEmpty(){
        boolean isEmpty = false;
        if(tvStudents.getItems().isEmpty()){
            isEmpty = true;
            Utilities.showSimpleDialog("Sin estudiante en la tabla",
                    "Necesitas añadir al menos un estudiante.", Alert.AlertType.ERROR);
        }

        return isEmpty;
    }

    @javafx.fxml.FXML
    public void clickNewEducativeExperience(ActionEvent actionEvent) {
        FXMLEducationalProgramController educationalProgramController = Utilities.changePane(apRecordsForm, "/views/FXMLEducationalProgram.fxml");
        educationalProgramController.setTeacherId(this.teacherId);
    }

    @javafx.fxml.FXML
    public void clickRegisterExperienceEducative(ActionEvent actionEvent) {
        EducationalProgram educationalProgram = validateEducationalProgramComboBox();

        if(educationalProgram != null){
            EducativeExperience educativeExperience = validateEducativeExperiences();
            if(educativeExperience != null){
                educativeExperience.setIdEducationalProgram(educationalProgram.getIdEducationalProgram());
                int response = EducativeExperienceDAO.saveEducativeExperience(educativeExperience);
                if(response == Constants.OPERATION_SUCCESFUL){
                    tvEducativeExperiences.getItems().add(educativeExperience);
                    Utilities.showSimpleDialog("Experiencia Educativa Registrada",
                            "Experiencia educativa registrada con éxito.", Alert.AlertType.INFORMATION);
                }
            }
        }

    }

    private EducativeExperience validateEducativeExperiences(){
        String educativeExperienceName = tfEducativeExperienceName.getText();
        String block = tfBlock.getText();
        String section = tfSection.getText();
        String credits = tfCredits.getText();

        //TODO. VALIDAR

        EducativeExperience educativeExperience = new EducativeExperience();
        educativeExperience.setName(educativeExperienceName);
        educativeExperience.setBlock(block);
        educativeExperience.setSection(section);
        educativeExperience.setCredits(credits);
        educativeExperience.setIdTeacher(this.teacherId);

        return educativeExperience;
    }

    private EducationalProgram validateEducationalProgramComboBox(){
        EducationalProgram educationalProgram = cbEducativePrograms.getSelectionModel().getSelectedItem();
        if(educationalProgram != null){
            return educationalProgram;
        } else{
            Utilities.showSimpleDialog("Programa Educativo",
                    "Debes seleccionar un programa educativo.", Alert.AlertType.ERROR);
        }

        return null;
    }
}
