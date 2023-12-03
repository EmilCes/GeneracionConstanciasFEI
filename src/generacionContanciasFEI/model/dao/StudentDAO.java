package model.dao;

import model.ConnectionBD;
import model.pojo.Student;
import model.pojo.StudentResponse;
import model.pojo.Teacher;
import model.pojo.TeacherResponse;
import utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {
    public static StudentResponse getStudents() {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setResponseCode(Constants.OPERATION_SUCCESFUL);
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdEstudiante, nombre, apellidoPaterno, apellidoMaterno, matricula  FROM Estudiantes";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet responseBD = preparedStatement.executeQuery();
                ArrayList<Student> studentConsult = new ArrayList();
                while(responseBD.next()){
                    Student student = new Student();
                    student.setIdEstudiante(responseBD.getInt("IdEstudiante"));
                    student.setName(responseBD.getString("nombre"));
                    student.setFirstLastName(responseBD.getString("apellidoPaterno"));
                    student.setSecondLastName(responseBD.getString("apellidoMaterno"));
                    student.setTuition(responseBD.getString("matricula"));
                    studentConsult.add(student);
                }
                studentResponse.setStudents(studentConsult);
                connection.close();
            } catch(SQLException e){
                studentResponse.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            studentResponse.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return studentResponse;
    }

    public static int saveStudent(Student student) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO Estudiantes (nombre, apellidoPaterno, apellidoMaterno, matricula)" +
                        " VALUES (?, ?, ?, ?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setString(1, student.getName());
                preparedStatement.setString(2, student.getFirstLastName());
                preparedStatement.setString(3, student.getSecondLastName());
                preparedStatement.setString(4, student.getTuition());

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

    public static Student getStudentById(int idStudent) {
        Student student = null;
        Connection connectionBD = ConnectionBD.openConeectionBD();

        if (connectionBD != null) {
            try {
                String query = "SELECT nombre, apellidoPaterno, apellidoMaterno, matricula " +
                        "FROM Estudiantes WHERE IdEstudiante = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setInt(1, idStudent);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("nombre");
                    String firstLastName = resultSet.getString("apellidoPaterno");
                    String secondLastName = resultSet.getString("apellidoMaterno");
                    String tuition = resultSet.getString("matricula");

                    student = new Student(name, firstLastName, secondLastName, tuition);
                }

                resultSet.close();
                connectionBD.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }

        return student;
    }
}
