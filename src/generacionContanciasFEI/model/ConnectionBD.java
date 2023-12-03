package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionBD {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String dataBaseName = "SGACP";
    private static final String hostname = "localhost";
    private static final String port = "3306";
    private static final String user = "sgacpUser";
    private static final String password = "sgacpUser123456";

    private static final String urlConection = "jdbc:mysql://" + hostname + ":" + port + "/" + dataBaseName +
            "?allowPublicKeyRetrieval=true&useSSL=false";

    public static Connection openConeectionBD(){
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(urlConection, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error de conexión con BD: " + ex.getMessage());
        }

        return connection;
    }
}