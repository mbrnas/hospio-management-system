package org.hospital.dao.department;

import org.hospital.dao.appointment.AppointmentDaoImpl;
import org.hospital.database.DatabaseConfig;
import org.hospital.model.department.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {

    private final DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);


    public DepartmentDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
       return dataSource.getConnection();
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Department department = new Department(
                        resultSet.getInt("department_id"),
                        resultSet.getString("department_name")
                );
                departments.add(department);
            }
        } catch (SQLException e) {
            logger.error("Error getting all departments", e);
        }
        logger.info("Returned all departments");
        return departments;
    }

    @Override
    public Department getDepartmentById(int id) {
        String sql = "SELECT * FROM departments WHERE department_id = ?";
        Department department = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    department = new Department(
                            resultSet.getInt("department_id"),
                            resultSet.getString("department_name")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting department by id", e);
        }
        logger.info("Returned department with id {}", id);
        return department;
    }


    @Override
    public void saveDepartment(Department department) {
        String sql = "INSERT INTO departments (department_name) VALUES (?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, department.getDepartmentName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving department", e);
        }
        logger.info("Saved department with name {}", department.getDepartmentName());
    }

    @Override
    public void updateDepartment(Department department) {
        String sql = "UPDATE departments SET department_name = ? WHERE department_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, department.getDepartmentName());
            preparedStatement.setInt(2, department.getDepartmentId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating department failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error updating department", e);
        }
        logger.info("Updated department with id {}", department.getDepartmentId());
    }


    @Override
    public void deleteDepartment(int id) {
        String sql = "DELETE FROM departments WHERE department_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting department", e);
        }
        logger.info("Deleted department with id {}", id);
    }
}
