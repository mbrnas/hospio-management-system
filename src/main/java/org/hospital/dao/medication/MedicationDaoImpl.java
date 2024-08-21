package org.hospital.dao.medication;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.medication.Medication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationDaoImpl implements MedicationDao {

    private final DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(MedicationDaoImpl.class);

    public MedicationDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Medication> getAllMedications() {
        List<Medication> medications = new ArrayList<>();
        String sql = "SELECT * FROM medications";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Medication medication = new Medication(
                        resultSet.getInt("medication_id"),
                        resultSet.getString("medication_name"),
                        resultSet.getString("description")
                );
                medications.add(medication);
            }
        } catch (SQLException e) {
            logger.error("Error occurred while fetching all medications", e);
        }
        logger.info("Fetched all medications");
        return medications;
    }

    @Override
    public Medication getMedicationById(int id) {
        String sql = "SELECT * FROM medications WHERE medication_id = ?";
        Medication medication = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    medication = new Medication(
                            resultSet.getInt("medication_id"),
                            resultSet.getString("medication_name"),
                            resultSet.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error occurred while fetching medication by id", e);
        }
        logger.info("Fetched medication by id");
        return medication;
    }

    @Override
    public void saveMedication(Medication medication) {
        String sql = "INSERT INTO medications (medication_name, description) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, medication.getMedicationName());
            preparedStatement.setString(2, medication.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while saving medication", e);
        }
    }

    @Override
    public void updateMedication(Medication medication) {
        String sql = "UPDATE medications SET medication_name = ?, description = ? WHERE medication_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, medication.getMedicationName());
            preparedStatement.setString(2, medication.getDescription());
            preparedStatement.setInt(3, medication.getMedicationId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while updating medication", e);
        }
    }


    @Override
    public void deleteMedication(int id) {
        String sql = "DELETE FROM medications WHERE medication_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
