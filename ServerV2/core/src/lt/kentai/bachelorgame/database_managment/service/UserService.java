package lt.kentai.bachelorgame.database_managment.service;

import lt.kentai.bachelorgame.database_managment.DBManager;
import lt.kentai.bachelorgame.database_managment.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserService {

    public User registerUser(User user) {
        try {
            Connection connection = DBManager.createConnection();
            String createUserQuery = "insert into user values(?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(createUserQuery);
            user.setId(UUID.randomUUID().timestamp());
            connection.setAutoCommit(false);
            statement.setLong(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.execute();
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean loginUser(User user) {
        try {
            Connection connection = DBManager.createConnection();
            String createUserQuery = "SELECT from user where username = ? and password = ?";
            PreparedStatement statement = connection.prepareStatement(createUserQuery);
            connection.setAutoCommit(false);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            statement.close();
            connection.close();
            if (resultSet.next()) {
                return true;
            } else return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
