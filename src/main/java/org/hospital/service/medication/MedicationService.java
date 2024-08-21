package org.hospital.service.medication;

import org.hospital.dao.medication.MedicationDao;
import org.hospital.model.medication.Medication;

import java.util.List;


public class MedicationService {

    private final MedicationDao medicationDao;

    public MedicationService(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }

    public List<Medication> getAllMedications() {
        return medicationDao.getAllMedications();
    }

    public Medication getMedicationById(int id) {
        return medicationDao.getMedicationById(id);
    }

    public void saveMedication(Medication medication) {
        medicationDao.saveMedication(medication);
    }

    public void updateMedication(Medication medication) {
        medicationDao.updateMedication(medication);
    }

    public void deleteMedication(int id) {
        medicationDao.deleteMedication(id);
    }
}