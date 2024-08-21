package org.hospital.dao.doctor;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.doctor.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl implements DoctorDao {

    private final DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(DoctorDaoImpl.class);

    public DoctorDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT doctor_id, first_name, last_name, specialization_id, contact_number, email, address FROM doctors";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Doctor doctor = new Doctor(
                        resultSet.getInt("doctor_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("specialization_id"),
                        resultSet.getString("contact_number"),
                        resultSet.getString("email"),
                        resultSet.getString("address")
                );
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            logger.error("Error getting all doctors", e);
        }
        logger.info("Returned all doctors");
        return doctors;
    }

    @Override
    public Doctor getDoctorById(int id) {
        String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
        Doctor doctor = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    doctor = new Doctor(
                            resultSet.getInt("doctor_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getInt("specialization_id"),
                            resultSet.getString("contact_number"),
                            resultSet.getString("email"),
                            resultSet.getString("address")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting doctor by id", e);
        }
        logger.info("Returned doctor with id {}", id);
        return doctor;
    }

    @Override
    public void saveDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (first_name, last_name, specialization_id, contact_number, email, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setInt(3, doctor.getSpecializationId());
            preparedStatement.setString(4, doctor.getContactNumber());
            preparedStatement.setString(5, doctor.getEmail());
            preparedStatement.setString(6, doctor.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving doctor", e);
        }
        logger.info("Saved doctor with name {}", doctor.getFirstName() + " " + doctor.getLastName());
    }

    @Override
    public void deleteDoctor(int id) {
        String sql = "DELETE FROM doctors WHERE doctor_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting doctor", e);
        }
        logger.info("Deleted doctor with id {}", id);
    }
}