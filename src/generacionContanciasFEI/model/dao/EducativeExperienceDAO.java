package model.dao;

import model.ConnectionBD;
import model.pojo.EducativeExperience;
import model.pojo.EducativeExperienceResponse;
import model.pojo.Project;
import model.pojo.ProjectResponse;
import utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducativeExperienceDAO {
    public static int saveEducativeExperience(EducativeExperience educativeExperience) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO ExperienciasEducativas (nombre, IdProgramaEducativo, bloque, seccion, creditos, IdProfesor)" +
                        " VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setString(1, educativeExperience.getName());
                preparedStatement.setInt(2, educativeExperience.getIdEducationalProgram());
                preparedStatement.setString(3, educativeExperience.getBlock());
                preparedStatement.setString(4, educativeExperience.getSection());
                preparedStatement.setString(5, educativeExperience.getCredits());
                preparedStatement.setInt(6, educativeExperience.getIdTeacher());


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

    public static EducativeExperienceResponse getEducativeExperiences(int idTeacher) {
        EducativeExperienceResponse educativeExperienceResponse = new EducativeExperienceResponse();
        educativeExperienceResponse.setResponseCode(Constants.OPERATION_SUCCESFUL);
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdExperienciaEducativa, nombre, IdProgramaEducativo, bloque, seccion, creditos FROM ExperienciasEducativas WHERE IdProfesor = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idTeacher);
                ResultSet responseBD = preparedStatement.executeQuery();
                ArrayList<EducativeExperience> educativeExperiencesConsult = new ArrayList();

                while(responseBD.next()){
                    EducativeExperience educativeExperience = new EducativeExperience();
                    educativeExperience.setIdEducativeExperience(responseBD.getInt("IdExperienciaEducativa"));
                    educativeExperience.setName(responseBD.getString("nombre"));
                    educativeExperience.setIdEducationalProgram(responseBD.getInt("IdProgramaEducativo"));
                    educativeExperience.setBlock(responseBD.getString("bloque"));
                    educativeExperience.setSection(responseBD.getString("seccion"));
                    educativeExperience.setCredits(responseBD.getString("creditos"));
                    educativeExperiencesConsult.add(educativeExperience);
                }

                educativeExperienceResponse.setEducativeExperiences(educativeExperiencesConsult);
                connection.close();
            } catch(SQLException e){
                educativeExperienceResponse.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            educativeExperienceResponse.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return educativeExperienceResponse;
    }
}
