package org.hospital.dao.medical_records;

import org.hospital.model.mrecord.MedicalRecord;

import java.util.List;

public interface MedicalRecordDao {
    List<MedicalRecord> getAllMedicalRecords();

    MedicalRecord getMedicalRecordById(int id);

    void saveMedicalRecord(MedicalRecord medicalRecord);

    void updateMedicalRecord(MedicalRecord medicalRecord);

    void deleteMedicalRecord(int id);

    List<MedicalRecord> getMedicalRecordsByPatientId(int patientId);

}