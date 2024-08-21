package org.hospital.dao.user;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.user.User;
import org.hospital.model.user.UserRole;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoImpl implements UserDao {

    private final DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public User findUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String password = resultSet.getString("password");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String roleString = resultSet.getString("role");
                    UserRole role = UserRole.valueOf(roleString.toUpperCase());
                    user = new User(userId, username, password, role, firstName, lastName, email);

                }
            }
        } catch (SQLException e) {
            logger.error("Error finding user by username", e);
        }
        return user;
    }

    @Override
    public boolean saveUser(User user) {
        String sql = "INSERT INTO users (username, password, role, first_name, last_name, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving user", e);
        }
        return true;
    }

    @Override
    public int getPatientIdForUser(int userId) {
        String sql = "SELECT patient_id FROM patients WHERE user_id = ?";
        int patientId = -1;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    patientId = resultSet.getInt("patient_id");
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting patient id for user", e);
        }
        return patientId;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                UserRole role = UserRole.valueOf(resultSet.getString("role").toUpperCase());

                User user = new User(userId, username, password, role, firstName, lastName, email);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error finding all users", e);
        }
        return users;
    }
}
