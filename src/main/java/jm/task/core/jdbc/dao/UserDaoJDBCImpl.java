package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection conn = Util.getConnect();
    private static final String createUsersTableSql = "CREATE TABLE IF NOT EXISTS USERS (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(255), age TINYINT)";
    private static final String dropUsersTableSql = "DROP TABLE IF EXISTS USERS";
    private static final String saveUserSql = "INSERT INTO USERS (name, lastname, age) VALUES (?, ?, ?)";
    private static final String removeUserByIdSql = "DELETE FROM USERS WHERE  ? ";
    private static final String getAllUsersSql = "SELECT * FROM USERS";
    private static final String cleanUsersTableSql = "TRUNCATE TABLE USERS";
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(createUsersTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(dropUsersTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(saveUserSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(removeUserByIdSql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(getAllUsersSql)) {
            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                result.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(cleanUsersTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
