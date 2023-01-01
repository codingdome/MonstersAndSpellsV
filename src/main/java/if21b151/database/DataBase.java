package if21b151.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase extends AbstractDataBase {
    //connection itself of Java Type Connection
    private static Connection _connection = null;

    //get connection function (use of openConnection) returning Type Connection
    public static Connection getConnection() {
        if (_connection == null) {
            try {
                //link zur Datenbank inkl port und datenbank name (egal in welchem img)
                openConnection("jdbc:postgresql://localhost:5432/monsterDB3", "postgres", "password");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return _connection;
    }

    public static void openConnection(String url, String user, String password) throws SQLException {
        _connection = DriverManager.getConnection(url, user, password);
    }

    public static void closeConnection() throws SQLException {
        _connection.close();
        _connection = null;
    }
}
