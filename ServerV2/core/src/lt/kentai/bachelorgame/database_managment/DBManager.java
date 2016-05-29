package lt.kentai.bachelorgame.database_managment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/gam_db";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }



}
