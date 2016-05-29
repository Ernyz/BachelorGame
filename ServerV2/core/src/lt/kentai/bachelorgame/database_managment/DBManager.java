package lt.kentai.bachelorgame.database_managment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static Connection connection;


    public static Connection createConnection() {
        String url = "jdbc:mysql://88.223.50.149:3306/ih_6318141_game";
        String username = "ih_6318141";
        String password = "ernestas";
        try {
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public static Connection getInstance() {
        if (connection == null)
            connection = createConnection();
        return connection;
    }


}
