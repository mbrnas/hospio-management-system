package org.hospital.dao.medication;

import org.hospital.model.medication.Medication;

import java.util.List;

public interface MedicationDao {
    List<Medication> getAllMedications();

    Medication getMedicationById(int id);

    void saveMedication(Medication medication);

    void updateMedication(Medication medication);

    void deleteMedication(int id);
}
