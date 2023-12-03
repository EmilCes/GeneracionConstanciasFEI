package model.dao;

import model.ConnectionBD;
import model.pojo.Teacher;
import model.pojo.TeacherResponse;
import model.pojo.User;
import utils.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class TeacherDAO {
    public static int saveTeacher(Teacher teacher) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO Profesores (nombre, apellidoPaterno, apellidoMaterno, numeroPersonal, correoInstitucional, " +
                                  "correoAlterno, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setString(1, teacher.getName());
                preparedStatement.setString(2, teacher.getFirstLastName());
                preparedStatement.setString(3, teacher.getSecondLastName());
                preparedStatement.setInt(4, teacher.getPersonalNumber());
                preparedStatement.setString(5, teacher.getInstitutionalEmail());
                preparedStatement.setString(6, teacher.getAlternativeEmail());
                preparedStatement.setString(7, teacher.getBirthdate());
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

    public static Teacher getLastTeacherRegistered() {
        Teacher teacher = new Teacher();
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdProfesor FROM Profesores ORDER BY IdProfesor DESC LIMIT 1";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet result = preparedStatement.executeQuery();
                teacher.setResponseCode(Constants.OPERATION_SUCCESFUL);
                if (result.next()) {
                    teacher.setIdTeacher(result.getInt("IdProfesor"));
                }
                connection.close();
            } catch (SQLException e) {
                teacher.setResponseCode(Constants.QUERY_ERROR);
            }
        } else {
            teacher.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return teacher;
    }

    public static TeacherResponse getTeachers() {
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setResponseCode(Constants.OPERATION_SUCCESFUL);
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdProfesor, nombre, apellidoPaterno, apellidoMaterno, numeroPersonal  FROM Profesores";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet responseBD = preparedStatement.executeQuery();
                ArrayList<Teacher> teachersConsult = new ArrayList();
                while(responseBD.next()){
                    Teacher teacher = new Teacher();
                    teacher.setIdTeacher(responseBD.getInt("IdProfesor"));
                    teacher.setName(responseBD.getString("nombre"));
                    teacher.setFirstLastName(responseBD.getString("apellidoPaterno"));
                    teacher.setSecondLastName(responseBD.getString("apellidoMaterno"));
                    teacher.setPersonalNumber(responseBD.getInt("numeroPersonal"));
                    teachersConsult.add(teacher);
                }
                teacherResponse.setTeachers(teachersConsult);
                connection.close();
            } catch(SQLException e){
                teacherResponse.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            teacherResponse.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return teacherResponse;
    }

    public static int deleteTeacher(int idTeacher) {
        int response;
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String sentence = "DELETE FROM Profesores WHERE IdProfesor = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sentence);
                preparedStatement.setInt(1, idTeacher);
                int rowsAffected = preparedStatement.executeUpdate();
                response = (rowsAffected == 1) ? Constants.OPERATION_SUCCESFUL : Constants.QUERY_ERROR;
            } catch (SQLException e) {
                response = Constants.QUERY_ERROR;
            }
        } else {
            response = Constants.CONNECTION_ERROR;
        }
        return response;
    }

    public static Teacher getTeacherById(int idTeacher) {
        Teacher teacher = null;
        Connection connectionBD = ConnectionBD.openConeectionBD();

        if (connectionBD != null) {
            try {
                String query = "SELECT IdProfesor, nombre, apellidoPaterno, apellidoMaterno, numeroPersonal, correoInstitucional, correoAlterno, fechaNacimiento " +
                        "FROM Profesores WHERE IdProfesor = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setInt(1, idTeacher);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int idTeacherBD = resultSet.getInt("IdProfesor");
                    String name = resultSet.getString("nombre");
                    String firstLastName = resultSet.getString("apellidoPaterno");
                    String secondLastName = resultSet.getString("apellidoMaterno");
                    String institutionalEmail = resultSet.getString("correoInstitucional");
                    String alternativeEmail = resultSet.getString("correoAlterno");
                    String birthdate = resultSet.getString("fechaNacimiento");
                    int personalNumber = resultSet.getInt("numeroPersonal");


                    teacher = new Teacher(idTeacherBD, name, firstLastName, secondLastName, institutionalEmail,
                            personalNumber, alternativeEmail, birthdate);
                }

                resultSet.close();
                connectionBD.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }

        return teacher;
    }

    public static int updateTeacher(Teacher teacher) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();

        if (connectionBD != null) {
            try {
                String query = "UPDATE Profesores SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, " +
                        "correoInstitucional = ?, correoAlterno = ?, fechaNacimiento = ?, numeroPersonal = ? WHERE IdProfesor = ?";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, teacher.getName());
                preparedStatement.setString(2, teacher.getFirstLastName());
                preparedStatement.setString(3, teacher.getSecondLastName());
                preparedStatement.setString(4, teacher.getInstitutionalEmail());
                preparedStatement.setString(5, teacher.getAlternativeEmail());
                preparedStatement.setString(6, teacher.getBirthdate());
                preparedStatement.setInt(7, teacher.getPersonalNumber());
                preparedStatement.setInt(8, teacher.getIdTeacher());

                int affectedRows = preparedStatement.executeUpdate();
                response = (affectedRows > 0) ? Constants.OPERATION_SUCCESFUL : Constants.QUERY_ERROR;

                connectionBD.close();
            } catch (SQLException e) {
                response = Constants.QUERY_ERROR;
            }
        } else {
            response = Constants.CONNECTION_ERROR;
        }

        return response;
    }

}
