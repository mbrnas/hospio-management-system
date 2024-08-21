package org.hospital.dao.patient;

import org.hospital.database.DatabaseConfig;
import org.hospital.exception.hospital.DatabaseOperationException;
import org.hospital.exception.hospital.PatientNotFoundException;
import org.hospital.model.patient.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {

    private DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(PatientDaoImpl.class);


    public PatientDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Patient> getAllPatients() throws DatabaseOperationException {
        String sql = "SELECT * FROM patients";
        List<Patient> patients = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("patient_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int userId = resultSet.getInt("user_id");
                Patient patient = new Patient(id, firstName, lastName, userId);
                patients.add(patient);
            }
        } catch (SQLException e) {
            logger.error("Error getting all patients", e);
            throw new DatabaseOperationException("Error getting all patients", e);
        }
        return patients;
    }

    @Override
    public Patient getPatientById(int id) throws DatabaseOperationException {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        Patient patient = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int userId = resultSet.getInt("user_id");
                    patient = new Patient(id, firstName, lastName, userId);
                } else {
                    throw new PatientNotFoundException(id);
                }
            }
        } catch (SQLException | PatientNotFoundException e) {
            logger.error("Error getting patient by id", e);
            throw new DatabaseOperationException("Error getting patient by id: " + id, e);
        }
        return patient;
    }


        @Override
        public void savePatient(Patient patient) throws DatabaseOperationException {
            String sql = "INSERT INTO patients (first_name, last_name, user_id) " +
                    "VALUES (?, ?, ?)";
            try {
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, patient.getFirstName());
                preparedStatement.setString(2, patient.getLastName());
                preparedStatement.setInt(3, patient.getUserId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Error saving patient", e);
                throw new DatabaseOperationException("Error saving patient", e);
            }
        }


    @Override
    public void editPatient(Patient patient, int id) throws DatabaseOperationException {
        String sql = "UPDATE patients SET first_name = ?, last_name = ? WHERE patient_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setInt(3, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new PatientNotFoundException(id);
            }
        } catch (SQLException | PatientNotFoundException e) {
            logger.error("Error updating patient", e);
            throw new DatabaseOperationException("Error updating patient with ID " + id, e);
        }
    }


    @Override
    public void deletePatient(int id) throws DatabaseOperationException {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new PatientNotFoundException(id);
            }
        } catch (SQLException | PatientNotFoundException e) {
            logger.error("Error deleting patient", e);
            throw new DatabaseOperationException("Error deleting patient with ID " + id, e);
        }
    }
}
