package org.hospital.dao.staff;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.staff.Staff;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffDaoImpl implements StaffDao {

    private final DataSource dataSource;
    private final Logger logger = LoggerFactory.getLogger(StaffDaoImpl.class);
    public StaffDaoImpl() {
       this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT s.*, d.department_name FROM staff s INNER JOIN departments d ON s.department_id = d.department_id";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Staff staff = new Staff(
                        resultSet.getInt("staff_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("role"),
                        resultSet.getInt("department_id"),
                        resultSet.getString("department_name"),
                        resultSet.getString("contact_number"),
                        resultSet.getString("address")
                );
                staffList.add(staff);
            }
        } catch (SQLException e) {
            logger.error("Error occurred while fetching all staff", e);
        }
        return staffList;
    }

    @Override
    public Staff getStaffById(int id) {
        String sql = "SELECT s.*, d.department_name FROM staff s INNER JOIN departments d ON s.department_id = d.department_id WHERE s.staff_id = ?";
        Staff staff = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    staff = new Staff(
                            resultSet.getInt("staff_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("role"),
                            resultSet.getInt("department_id"),
                            resultSet.getString("department_name"),
                            resultSet.getString("contact_number"),
                            resultSet.getString("address")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error occurred while fetching staff by id", e);
        }
        return staff;
    }


    @Override
    public void saveStaff(Staff staff) {
        String sql = "INSERT INTO staff (first_name, last_name, role, department_id, contact_number, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, staff.getFirstName());
            preparedStatement.setString(2, staff.getLastName());
            preparedStatement.setString(3, staff.getRole());
            preparedStatement.setInt(4, staff.getDepartmentId());
            preparedStatement.setString(5, staff.getContactNumber());
            preparedStatement.setString(6, staff.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while saving staff", e);
        }
    }

    @Override
    public void updateStaff(Staff staff) {
        String sql = "UPDATE staff SET first_name = ?, last_name = ?, role = ?, department_id = ?, contact_number = ?, address = ? WHERE staff_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, staff.getFirstName());
            preparedStatement.setString(2, staff.getLastName());
            preparedStatement.setString(3, staff.getRole());
            preparedStatement.setInt(4, staff.getDepartmentId());
            preparedStatement.setString(5, staff.getContactNumber());
            preparedStatement.setString(6, staff.getAddress());
            preparedStatement.setInt(7, staff.getStaffId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while updating staff", e);
        }
    }


    @Override
    public void deleteStaff(int id) {
        String sql = "DELETE FROM staff WHERE staff_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while deleting staff", e);
        }
    }
}
