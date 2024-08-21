package org.hospital.service.medical_record;

import org.hospital.dao.medical_records.MedicalRecordDao;
import org.hospital.model.mrecord.MedicalRecord;

import java.util.List;

public class MedicalRecordService {

    private final MedicalRecordDao medicalRecordDao;

    public MedicalRecordService(MedicalRecordDao medicalRecordDao) {
        this.medicalRecordDao = medicalRecordDao;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordDao.getAllMedicalRecords();
    }

    public MedicalRecord getMedicalRecordById(int id) {
        return medicalRecordDao.getMedicalRecordById(id);
    }

    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordDao.saveMedicalRecord(medicalRecord);
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordDao.updateMedicalRecord(medicalRecord);
    }
    public void deleteMedicalRecord(int id) {
        medicalRecordDao.deleteMedicalRecord(id);
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(int patientId) {
        return medicalRecordDao.getMedicalRecordsByPatientId(patientId);
    }
}
