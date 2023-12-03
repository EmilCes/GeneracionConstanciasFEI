package model.dao;

import model.ConnectionBD;
import model.pojo.*;
import utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectDAO {
    public static int saveProject(Project project) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO ProyectosDeCampo (proyectoRealizado, duracion, lugar, impactoObtenido, IdProfesor)" +
                        " VALUES (?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setString(1, project.getProjectDone());
                preparedStatement.setString(2, project.getDuration());
                preparedStatement.setString(3, project.getProjectPlace());
                preparedStatement.setString(4, project.getImpactObtained());
                preparedStatement.setInt(5, project.getIdTeacher());

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

    public static Project getLastProjectRegisteredByIdTeacher(int idTeacher) {
        Project project = new Project();
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdProyectoCampo FROM ProyectosDeCampo WHERE IdProfesor = ? " +
                        "ORDER BY IdProyectoCampo DESC LIMIT 1";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idTeacher);

                ResultSet result = preparedStatement.executeQuery();
                project.setResponseCode(Constants.OPERATION_SUCCESFUL);
                if (result.next()) {
                    project.setIdProject(result.getInt("IdProyectoCampo"));
                }
                connection.close();
            } catch (SQLException e) {
                project.setResponseCode(Constants.QUERY_ERROR);
            }
        } else {
            project.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return project;
    }

    public static ProjectResponse getProjects(int idTeacher) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setResponseCode(Constants.OPERATION_SUCCESFUL);
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdProyectoCampo, proyectoRealizado, duracion, lugar, impactoObtenido FROM ProyectosDeCampo WHERE IdProfesor = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idTeacher);
                ResultSet responseBD = preparedStatement.executeQuery();
                ArrayList<Project> projectsConsult = new ArrayList();

                while(responseBD.next()){
                    Project student = new Project();
                    student.setIdProject(responseBD.getInt("IdProyectoCampo"));
                    student.setProjectDone(responseBD.getString("proyectoRealizado"));
                    student.setDuration(responseBD.getString("duracion"));
                    student.setProjectPlace(responseBD.getString("lugar"));
                    student.setImpactObtained(responseBD.getString("impactoObtenido"));
                    projectsConsult.add(student);
                }

                projectResponse.setProjects(projectsConsult);
                connection.close();
            } catch(SQLException e){
                projectResponse.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            projectResponse.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return projectResponse;
    }
}
