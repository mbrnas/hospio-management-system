package org.hospital.dao.patient;

import org.hospital.exception.hospital.DatabaseOperationException;
import org.hospital.model.patient.Patient;

import java.sql.SQLException;
import java.util.List;

public interface PatientDao {
    List<Patient> getAllPatients() throws DatabaseOperationException;

    Patient getPatientById(int id) throws SQLException, DatabaseOperationException;

    void savePatient(Patient patient) throws DatabaseOperationException;

    void editPatient(Patient patient, int id) throws DatabaseOperationException;

    void deletePatient(int id) throws SQLException, DatabaseOperationException;
}
