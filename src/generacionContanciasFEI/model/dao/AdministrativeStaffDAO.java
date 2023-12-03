package model.dao;

import model.ConnectionBD;
import model.pojo.AdministrativeStaff;
import model.pojo.AdministrativeStaffResponse;
import model.pojo.Teacher;
import model.pojo.TeacherResponse;
import utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdministrativeStaffDAO {
    public static int saveAdministrative(AdministrativeStaff administrativeStaff) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO Administrativos (nombre, apellidoPaterno, apellidoMaterno, correo)" +
                        " VALUES (?, ?, ?, ?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setString(1, administrativeStaff.getName());
                preparedStatement.setString(2, administrativeStaff.getFirstLastName());
                preparedStatement.setString(3, administrativeStaff.getSecondLastName());
                preparedStatement.setString(4, administrativeStaff.getInstitutionalEmail());
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

    public static AdministrativeStaff getLastAdministrativeRegistered() {
        AdministrativeStaff administrativeStaff = new AdministrativeStaff();
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdAdministrativo FROM Administrativos ORDER BY IdAdministrativo DESC LIMIT 1";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet result = preparedStatement.executeQuery();
                administrativeStaff.setResponseCode(Constants.OPERATION_SUCCESFUL);
                if (result.next()) {
                    administrativeStaff.setIdAdministrativeStaff(result.getInt("IdAdministrativo"));
                }
                connection.close();
            } catch (SQLException e) {
                administrativeStaff.setResponseCode(Constants.QUERY_ERROR);
            }
        } else {
            administrativeStaff.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return administrativeStaff;
    }

    public static AdministrativeStaffResponse getAdminstratives() {
        AdministrativeStaffResponse administrativeStaffResponse = new AdministrativeStaffResponse();
        administrativeStaffResponse.setResponseCode(Constants.OPERATION_SUCCESFUL);
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String query = "SELECT IdAdministrativo, nombre, apellidoPaterno, apellidoMaterno, correo  FROM Administrativos";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet responseBD = preparedStatement.executeQuery();
                ArrayList<AdministrativeStaff> administrativeStaffConsult = new ArrayList();
                while(responseBD.next()){
                    AdministrativeStaff administrativeStaff = new AdministrativeStaff();
                    administrativeStaff.setIdAdministrativeStaff(responseBD.getInt("IdAdministrativo"));
                    administrativeStaff.setName(responseBD.getString("nombre"));
                    administrativeStaff.setFirstLastName(responseBD.getString("apellidoPaterno"));
                    administrativeStaff.setSecondLastName(responseBD.getString("apellidoMaterno"));
                    administrativeStaff.setInstitutionalEmail(responseBD.getString("correo"));
                    administrativeStaffConsult.add(administrativeStaff);
                }
                administrativeStaffResponse.setAdministrativeStaffs(administrativeStaffConsult);
                connection.close();
            } catch(SQLException e){
                administrativeStaffResponse.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            administrativeStaffResponse.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return administrativeStaffResponse;
    }

    public static int deleteAdministrativeStaff(int idAdministrative) {
        int response;
        Connection connection = ConnectionBD.openConeectionBD();
        if (connection != null) {
            try {
                String sentence = "DELETE FROM Administrativos WHERE IdAdministrativo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sentence);
                preparedStatement.setInt(1, idAdministrative);
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

    public static AdministrativeStaff getAdministrativeById(int idAdministrative) {
        AdministrativeStaff administrativeStaff = null;
        Connection connectionBD = ConnectionBD.openConeectionBD();

        if (connectionBD != null) {
            try {
                String query = "SELECT IdAdministrativo, nombre, apellidoPaterno, apellidoMaterno, correo " +
                        "FROM Administrativos WHERE IdAdministrativo = ?";
                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setInt(1, idAdministrative);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int idAdministrativeBD = resultSet.getInt("IdAdministrativo");
                    String name = resultSet.getString("nombre");
                    String firstLastName = resultSet.getString("apellidoPaterno");
                    String secondLastName = resultSet.getString("apellidoMaterno");
                    String institutionalEmail = resultSet.getString("correo");

                    administrativeStaff = new AdministrativeStaff(idAdministrativeBD, name, firstLastName, secondLastName, institutionalEmail);
                }

                resultSet.close();
                connectionBD.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }

        return administrativeStaff;
    }

    public static int updateAdministrativeStaff(AdministrativeStaff administrativeStaff) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();

        if (connectionBD != null) {
            try {
                String query = "UPDATE Administrativos SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, " +
                        "correo = ? WHERE IdAdministrativo = ?";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(query);
                preparedStatement.setString(1, administrativeStaff.getName());
                preparedStatement.setString(2, administrativeStaff.getFirstLastName());
                preparedStatement.setString(3, administrativeStaff.getSecondLastName());
                preparedStatement.setString(4, administrativeStaff.getInstitutionalEmail());
                preparedStatement.setInt(5, administrativeStaff.getIdAdministrativeStaff());

                int affectedRows = preparedStatement.executeUpdate();
                response = (affectedRows > 0) ? Constants.OPERATION_SUCCESFUL : Constants.QUERY_ERROR;

                connectionBD.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                response = Constants.QUERY_ERROR;
            }
        } else {
            response = Constants.CONNECTION_ERROR;
        }

        return response;
    }
}
