package org.hospital.dao.prescription;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.prescription.Prescription;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrescriptionDaoImpl implements PrescriptionDao {

    private final DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(PrescriptionDaoImpl.class);

    public PrescriptionDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescription";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Prescription prescription = new Prescription(
                        resultSet.getInt("prescription_id"),
                        resultSet.getInt("record_id"),
                        resultSet.getInt("medication_id"),
                        resultSet.getString("dosage"),
                        resultSet.getString("frequency"),
                        resultSet.getString("notes")
                );
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
           logger.error("Error getting all prescriptions", e);
        }
        return prescriptions;
    }

    @Override
    public Prescription getPrescriptionById(int id) {
        String sql = "SELECT * FROM prescription WHERE prescription_id = ?";
        Prescription prescription = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    prescription = new Prescription(
                            resultSet.getInt("prescription_id"),
                            resultSet.getInt("record_id"),
                            resultSet.getInt("medication_id"),
                            resultSet.getString("dosage"),
                            resultSet.getString("frequency"),
                            resultSet.getString("notes")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting prescription by id", e);
        }
        return prescription;
    }

    @Override
    public void savePrescription(Prescription prescription) {
        String sql = "INSERT INTO prescription (record_id, medication_id, dosage, frequency, notes) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, prescription.getRecordId());
            preparedStatement.setInt(2, prescription.getMedicationId());
            preparedStatement.setString(3, prescription.getDosage());
            preparedStatement.setString(4, prescription.getFrequency());
            preparedStatement.setString(5, prescription.getNotes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving prescription", e);
        }
    }

    @Override
    public void updatePrescription(Prescription prescription) {
        String sql = "UPDATE prescription SET record_id = ?, medication_id = ?, dosage = ?, frequency = ?, notes = ? WHERE prescription_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, prescription.getRecordId());
            preparedStatement.setInt(2, prescription.getMedicationId());
            preparedStatement.setString(3, prescription.getDosage());
            preparedStatement.setString(4, prescription.getFrequency());
            preparedStatement.setString(5, prescription.getNotes());
            preparedStatement.setInt(6, prescription.getPrescriptionId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating prescription", e);
        }
    }


    @Override
    public void deletePrescription(int id) {
        String sql = "DELETE FROM prescription WHERE prescription_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
           logger.error("Error deleting prescription", e);
        }
    }
}