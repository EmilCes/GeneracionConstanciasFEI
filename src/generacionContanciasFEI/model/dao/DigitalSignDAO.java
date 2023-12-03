package model.dao;

import model.ConnectionBD;
import model.pojo.DigitalSign;
import model.pojo.Teacher;
import model.pojo.User;
import utils.Constants;

import java.sql.*;

public class DigitalSignDAO {

    public static int saveDigitalSign(DigitalSign sign) {
        int response;
        Connection connectionBD = ConnectionBD.openConeectionBD();
        if (connectionBD != null) {
            try {
                String sentence = "INSERT INTO FirmasDigitales (firma, fechaExpiracion) "
                        + "VALUES (?, ?)";

                PreparedStatement preparedStatement = connectionBD.prepareStatement(sentence);
                preparedStatement.setString(1, sign.getSign());
                preparedStatement.setString(2, sign.getExpirationDate());

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

    public static DigitalSign getLastDigitalSignRegistered() {
        DigitalSign digitalSign = new DigitalSign();
        Connection connection = ConnectionBD.openConeectionBD();

        if (connection != null) {
            try {
                String query = "SELECT IdFirmaDigital, firma, fechaExpiracion FROM FirmasDigitales " +
                        "ORDER BY IdFirmaDigital DESC LIMIT 1";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet result = preparedStatement.executeQuery();
                digitalSign.setResponseCode(Constants.OPERATION_SUCCESFUL);

                if (result.next()) {
                    digitalSign.setIdDigitalSign(result.getInt("IdFirmaDigital"));
                    digitalSign.setSign(result.getString("firma"));
                    digitalSign.setExpirationDate(result.getString("fechaExpiracion"));
                }

                connection.close();
            } catch (SQLException e) {
                digitalSign.setResponseCode(Constants.QUERY_ERROR);
            }
        } else {
            digitalSign.setResponseCode(Constants.CONNECTION_ERROR);
        }
        return digitalSign;
    }
}
