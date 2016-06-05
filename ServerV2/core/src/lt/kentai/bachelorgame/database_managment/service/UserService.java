package lt.kentai.bachelorgame.database_managment.service;

import com.mysql.jdbc.StringUtils;
import lt.kentai.bachelorgame.database_managment.DBManager;
import lt.kentai.bachelorgame.database_managment.dto.User;

import java.sql.*;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class UserService {

    public User registerUser(User user) {
        try {
            Connection connection = DBManager.createConnection();
            String createUserQuery = "insert into public.user values(?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(createUserQuery);
            user.setId(Calendar.getInstance().getTimeInMillis());
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
            String selectQ = "SELECT * from public.USER";
            Statement statement = connection.createStatement();
            ResultSet  resultSet= statement.executeQuery(selectQ);
            System.out.println(resultSet);
            while (resultSet.next()){
                String u = resultSet.getString("name").trim();
                String p = resultSet.getString("password").trim();
                System.out.println(resultSet.getString(4));
                if(user.getUsername().equals(u)){
                    if(user.getPassword().equals(p)){
                        closeConnection(connection,statement);
                        return true;
                    }
                }
            }
            connection.commit();
            closeConnection(connection,statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void closeConnection(Connection connection, Statement statement) throws SQLException {
        statement.close();
        connection.close();
    }

}
