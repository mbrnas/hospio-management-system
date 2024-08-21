package org.hospital.service.patient;

import org.hospital.dao.patient.PatientDaoImpl;
import org.hospital.exception.hospital.DatabaseOperationException;
import org.hospital.model.patient.Patient;

import java.util.List;

public class PatientService {

    private final PatientDaoImpl patientDao;

    public PatientService(PatientDaoImpl patientDao) {
        this.patientDao = patientDao;
    }

    public List<Patient> getAllPatients() throws DatabaseOperationException {
        return patientDao.getAllPatients();
    }

    public Patient getPatientById(int id) throws DatabaseOperationException {
        return patientDao.getPatientById(id);
    }

    public void savePatient(Patient patient) throws DatabaseOperationException {
        patientDao.savePatient(patient);
    }

    public void editPatient(Patient patient, int id) throws DatabaseOperationException {
        patientDao.editPatient(patient, id);
    }

    public void deletePatient(int id) throws DatabaseOperationException {
        patientDao.deletePatient(id);
    }


}