package org.hospital.dao.medical_records;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.mrecord.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDaoImpl implements MedicalRecordDao {

    private final DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(MedicalRecordDaoImpl.class);

    public MedicalRecordDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        String sql = "SELECT medical_records.*, CONCAT(patients.first_name, ' ', patients.last_name) AS patientName " +
                "FROM medical_records " +
                "INNER JOIN patients ON medical_records.patient_id = patients.patient_id";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                MedicalRecord medicalRecord = new MedicalRecord(
                        resultSet.getInt("record_id"),
                        resultSet.getInt("patient_id"),
                        resultSet.getString("patientName"),
                        resultSet.getTimestamp("admission_date").toLocalDateTime(),
                        resultSet.getObject("discharge_date") != null ? resultSet.getTimestamp("discharge_date").toLocalDateTime() : null,
                        resultSet.getString("diagnosis"),
                        resultSet.getString("treatment")
                );
                medicalRecords.add(medicalRecord);
            }
        } catch (SQLException e) {
            logger.error("Error occurred while fetching all medical records", e);
        }
        logger.info("Fetched all medical records");
        return medicalRecords;
    }

    @Override
    public List<MedicalRecord> getMedicalRecordsByPatientId(int patientId) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        String sql = "SELECT medical_records.*, CONCAT(patients.first_name, ' ', patients.last_name) AS patientName " +
                "FROM medical_records " +
                "INNER JOIN patients ON medical_records.patient_id = patients.patient_id " +
                "WHERE medical_records.patient_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, patientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MedicalRecord medicalRecord = new MedicalRecord(
                            resultSet.getInt("record_id"),
                            resultSet.getInt("patient_id"),
                            resultSet.getString("patientName"),
                            resultSet.getTimestamp("admission_date").toLocalDateTime(),
                            resultSet.getObject("discharge_date") != null ? resultSet.getTimestamp("discharge_date").toLocalDateTime() : null,
                            resultSet.getString("diagnosis"),
                            resultSet.getString("treatment")
                    );
                    medicalRecords.add(medicalRecord);
                }

            }
        } catch (SQLException e) {
            logger.error("Error getting medical records by patient id");
        }
        return medicalRecords;
    }


    @Override
    public MedicalRecord getMedicalRecordById(int id) {
        String sql = "SELECT * FROM medical_records WHERE record_id = ?";
        MedicalRecord medicalRecord = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    medicalRecord = new MedicalRecord(
                            resultSet.getInt("record_id"),
                            resultSet.getInt("patient_id"),
                            resultSet.getString("patientName"),
                            resultSet.getTimestamp("admission_date").toLocalDateTime(),
                            resultSet.getObject("discharge_date") != null ? resultSet.getTimestamp("discharge_date").toLocalDateTime() : null,
                            resultSet.getString("diagnosis"),
                            resultSet.getString("treatment")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error occurred while fetching medical record by id", e);
        }
        logger.info("Fetched medical record by id {}", id);
        return medicalRecord;
    }

    @Override
    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        String sql = "INSERT INTO medical_records (patient_id, admission_date, discharge_date, diagnosis, treatment) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, medicalRecord.getPatientId());

            preparedStatement.setTimestamp(2, Timestamp.valueOf(medicalRecord.getAdmissionDate()));
            preparedStatement.setTimestamp(3, medicalRecord.getDischargeDate() != null ? Timestamp.valueOf(medicalRecord.getDischargeDate()) : null);
            preparedStatement.setString(4, medicalRecord.getDiagnosis());
            preparedStatement.setString(5, medicalRecord.getTreatment());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while saving medical record", e);
        }
        logger.info("Saved medical record");
    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        String sql = "UPDATE medical_records SET patient_id=?, admission_date=?, discharge_date=?, diagnosis=?, treatment=? WHERE record_id=?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, medicalRecord.getPatientId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(medicalRecord.getAdmissionDate()));
            preparedStatement.setTimestamp(3, medicalRecord.getDischargeDate() != null ? Timestamp.valueOf(medicalRecord.getDischargeDate()) : null);
            preparedStatement.setString(4, medicalRecord.getDiagnosis());
            preparedStatement.setString(5, medicalRecord.getTreatment());
            preparedStatement.setInt(6, medicalRecord.getRecordId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while updating medical record", e);
        }
        logger.info("Updated medical record with ID {}", medicalRecord.getRecordId());
    }

    @Override
    public void deleteMedicalRecord(int id) {
        String sql = "DELETE FROM medical_records WHERE record_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while deleting medical record", e);
        }
        logger.info("Deleted medical record by id {}", id);
    }
}