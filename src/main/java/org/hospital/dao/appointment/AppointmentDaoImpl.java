package org.hospital.dao.appointment;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.appointment.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppointmentDaoImpl implements AppointmentDao {
    private final Logger logger = LoggerFactory.getLogger(AppointmentDaoImpl.class);
    private final DataSource dataSource;

    public AppointmentDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Appointment> getAllAppointments() {
        logger.info("Getting all appointments");
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT appointments.*, patients.first_name AS patientFirstName, patients.last_name AS patientLastName, " +
                "doctors.first_name AS doctorFirstName, doctors.last_name AS doctorLastName " +
                "FROM appointments " +
                "INNER JOIN patients ON appointments.patient_id = patients.patient_id " +
                "INNER JOIN doctors ON appointments.doctor_id = doctors.doctor_id";


        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("appointment_id"),
                        resultSet.getInt("patient_id"),
                        resultSet.getString("patientFirstName") + " " + resultSet.getString("patientLastName"),
                        resultSet.getInt("doctor_id"),
                        resultSet.getString("doctorFirstName") + " " + resultSet.getString("doctorLastName"),
                        resultSet.getTimestamp("appointment_date").toLocalDateTime(),
                        resultSet.getString("notes")
                );
                appointments.add(appointment);
            }

        } catch (SQLException e) {
            logger.error("Error getting all appointments", e);
        }
        logger.info("Returned all appointments");
        return appointments;
    }


    @Override
    public Appointment getAppointmentById(int id) {
        String sql = "SELECT appointments.*, " +
                "CONCAT(patients.first_name, ' ', patients.last_name) AS patientName, " +
                "CONCAT(doctors.first_name, ' ', doctors.last_name) AS doctorName " +
                "FROM appointments " +
                "INNER JOIN patients ON appointments.patient_id = patients.patient_id " +
                "INNER JOIN doctors ON appointments.doctor_id = doctors.doctor_id " +
                "WHERE appointments.appointment_id = ?";
        Appointment appointment = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    appointment = new Appointment(
                            resultSet.getInt("appointment_id"),
                            resultSet.getInt("patient_id"),
                            resultSet.getString("patientName"),
                            resultSet.getInt("doctor_id"),
                            resultSet.getString("doctorName"),
                            resultSet.getTimestamp("appointment_date").toLocalDateTime(),
                            resultSet.getString("notes")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting appointment by id", e);
        }
        return appointment;
    }



    @Override
    public void saveAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, notes) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointment.getPatientId());
            preparedStatement.setInt(2, appointment.getDoctorId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
            preparedStatement.setString(4, appointment.getNotes());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving appointment");
        }
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, notes = ? WHERE appointment_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointment.getPatientId());
            preparedStatement.setInt(2, appointment.getDoctorId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
            preparedStatement.setString(4, appointment.getNotes());
            preparedStatement.setInt(5, appointment.getAppointmentId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating appointment failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error updating appointment", e);
        }
    }


    @Override
    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting appointment");
        }
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT appointments.*, patients.first_name AS patientFirstName, patients.last_name AS patientLastName, " +
                "doctors.first_name AS doctorFirstName, doctors.last_name AS doctorLastName " +
                "FROM appointments " +
                "INNER JOIN patients ON appointments.patient_id = patients.patient_id " +
                "INNER JOIN doctors ON appointments.doctor_id = doctors.doctor_id " +
                "WHERE appointments.patient_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, patientId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment(
                            resultSet.getInt("appointment_id"),
                            resultSet.getInt("patient_id"),
                            resultSet.getString("patientFirstName") + " " + resultSet.getString("patientLastName"),
                            resultSet.getInt("doctor_id"),
                            resultSet.getString("doctorFirstName") + " " + resultSet.getString("doctorLastName"),
                            resultSet.getTimestamp("appointment_date").toLocalDateTime(),
                            resultSet.getString("notes")
                    );
                    appointments.add(appointment);
                }

            }
        } catch (SQLException e) {
            logger.error("Error getting appointment/s by patient id");
        }
        return appointments;
    }

}