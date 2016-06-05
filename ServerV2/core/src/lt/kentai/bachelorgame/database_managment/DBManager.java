package lt.kentai.bachelorgame.database_managment;

import lt.kentai.bachelorgame.database_managment.dto.User;
import lt.kentai.bachelorgame.database_managment.service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public class DBManager {

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/mydb";
        String username = "postgres";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();
        System.out.println(userService.loginUser(new User("lauris","lauris","lauris")));;
        userService.registerUser(new User("ernis","ernis","ernis"));
    }
}
