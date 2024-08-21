package org.hospital.dao.prescription;

import org.hospital.model.prescription.Prescription;

import java.util.List;

public interface PrescriptionDao {
    List<Prescription> getAllPrescriptions();

    Prescription getPrescriptionById(int id);

    void updatePrescription(Prescription prescription);


    void savePrescription(Prescription prescription);

    void deletePrescription(int id);
}