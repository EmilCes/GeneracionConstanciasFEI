package model.dao;

import model.ConnectionBD;
import model.pojo.*;
import utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducationalProgramDAO {
    public static int saveEducationalProgram(EducationalProgram educationalProgram) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO ProgramasEducativos (nombre) "
                        + "VALUES (?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setString(1, educationalProgram.getEducationalProgramName());

                int affetedRows = preparedStatement.executeUpdate();
                response = (affetedRows == 1) ? Constants.OPERATION_SUCCESFUL : Constants.QUERY_ERROR;
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

    public static EducationalProgramResponse getEducationalPrograms() {
        EducationalProgramResponse educationalProgramResponse = new EducationalProgramResponse();
        educationalProgramResponse.setResponseCode(Constants.OPERATION_SUCCESFUL);
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdProgramaEducativo, nombre  FROM ProgramasEducativos";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet responseBD = preparedStatement.executeQuery();
                ArrayList<EducationalProgram> educationalProgramConsult = new ArrayList();
                while(responseBD.next()){
                    EducationalProgram educationalProgram = new EducationalProgram();
                    educationalProgram.setIdEducationalProgram(responseBD.getInt("IdProgramaEducativo"));
                    educationalProgram.setEducationalProgramName(responseBD.getString("nombre"));
                    educationalProgramConsult.add(educationalProgram);
                }
                educationalProgramResponse.setEducationalPrograms(educationalProgramConsult);
                connection.close();
            } catch(SQLException e){
                educationalProgramResponse.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            educationalProgramResponse.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return educationalProgramResponse;
    }

    public static EducationalProgram getEducationalProgramById(int educationalProgramId) {
        EducationalProgram educationalProgram = new EducationalProgram();
        educationalProgram.setResponseCode(Constants.OPERATION_SUCCESFUL);
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT nombre  FROM ProgramasEducativos WHERE IdProgramaEducativo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, educationalProgramId);

                ResultSet responseBD = preparedStatement.executeQuery();

                if(responseBD.next()){
                    educationalProgram.setEducationalProgramName(responseBD.getString("nombre"));
                }
                connection.close();
            } catch(SQLException e){
                educationalProgram.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            educationalProgram.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return educationalProgram;
    }
}
