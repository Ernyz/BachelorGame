package lt.kentai.bachelorgame.database_managment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Laurynas on 2016-02-13.
 */
public class Database {



    public static void main(String args[]) throws ClassNotFoundException {
        String url = "jdbc:mysql://88.223.50.149:3306/ih_6318141_game";
        String username = "ih_6318141";
        String password = "ernestas";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("NO SHIT FOUND");
        }

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

    }



}
