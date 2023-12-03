package model.dao;

import model.ConnectionBD;
import model.pojo.*;
import utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectStudentDAO {
    public static int saveProjectStudent(ProjectStudent projectStudent) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO ProyectosDeCampoEstudiantes (IdProyectoCampo, IdEstudiante)" +
                        " VALUES (?, ?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setInt(1, projectStudent.getIdProject());
                preparedStatement.setInt(2, projectStudent.getIdStudent());

                int affectedRows = preparedStatement.executeUpdate();
                response = (affectedRows == 1) ? Constants.OPERATION_SUCCESFUL : Constants.QUERY_ERROR;
                connectionBD.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                response = Constants.QUERY_ERROR;
            }
        } else {
            response = Constants.CONNECTION_ERROR;
        }

        return response;
    }

    public static ProjectStudentResponse getProjectsStudents(int idProject) {
        ProjectStudentResponse projectStudentResponse = new ProjectStudentResponse();
        projectStudentResponse.setResponseCode(Constants.OPERATION_SUCCESFUL);
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdProyectoCampoEstudiante, IdProyectoCampo, IdEstudiante FROM ProyectosDeCampoEstudiantes WHERE IdProyectoCampo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idProject);
                ResultSet responseBD = preparedStatement.executeQuery();
                ArrayList<ProjectStudent> projectStudentConsult = new ArrayList();
                while(responseBD.next()){
                    ProjectStudent projectStudent = new ProjectStudent();
                    projectStudent.setIdProjectStudent(responseBD.getInt("IdProyectoCampoEstudiante"));
                    projectStudent.setIdProject(responseBD.getInt("IdProyectoCampo"));
                    projectStudent.setIdStudent(responseBD.getInt("IdEstudiante"));

                    projectStudentConsult.add(projectStudent);
                }
                projectStudentResponse.setProjectStudents(projectStudentConsult);
                connection.close();
            } catch(SQLException e){
                projectStudentResponse.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            projectStudentResponse.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return projectStudentResponse;
    }
}
