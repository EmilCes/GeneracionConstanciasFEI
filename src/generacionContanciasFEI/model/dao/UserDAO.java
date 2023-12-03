package model.dao;

import model.ConnectionBD;
import model.pojo.User;
import utils.Constants;

import java.sql.*;


public class UserDAO {

    public static User verifyUserSession(String username, String password){
        User userVerified = new User();
        Connection connection = ConnectionBD.openConeectionBD();

        if(connection != null){
            try{
                String query = "SELECT IdUsuario, nombreUsuario, contraseña, idTipoUsuario, idProfesor, idAdministrativo " +
                               "FROM Usuarios WHERE nombreUsuario = ? AND contraseña = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet result = preparedStatement.executeQuery();
                userVerified.setResponseCode(Constants.OPERATION_SUCCESFUL);

                if(result.next()){
                    userVerified.setIdUser(result.getInt("IdUsuario"));
                    userVerified.setUsername(result.getString("nombreUsuario"));
                    userVerified.setPassword(result.getString("contraseña"));
                    userVerified.setIdUserType(result.getInt("idTipoUsuario"));
                    userVerified.setIdTeacher(result.getInt(("idProfesor")));
                    userVerified.setIdAdministrativeStaff(result.getInt(("idAdministrativo")));
                }
                connection.close();
            } catch(SQLException ex){
                System.out.println((ex.getMessage()));
                userVerified.setResponseCode(Constants.QUERY_ERROR);
            }
        } else{
            userVerified.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return userVerified;
    }
    public static int saveUser(User user) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO Usuarios (nombreUsuario, contraseña, idTipoUsuario, idProfesor, idAdministrativo) "
                        + "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setInt(3, user.getIdUserType());

                if(user.getIdTeacher() != 0){
                    preparedStatement.setInt(4, user.getIdTeacher());
                } else{
                    preparedStatement.setNull(4, Types.INTEGER);
                }

                if(user.getIdAdministrativeStaff() != 0){
                    preparedStatement.setInt(5, user.getIdAdministrativeStaff());
                } else{
                    preparedStatement.setNull(5, Types.INTEGER);
                }

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
}