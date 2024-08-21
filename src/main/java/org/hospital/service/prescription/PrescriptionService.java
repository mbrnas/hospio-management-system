package org.hospital.service.prescription;

import org.hospital.dao.prescription.PrescriptionDao;
import org.hospital.model.prescription.Prescription;

import java.util.List;

public class PrescriptionService {

    private final PrescriptionDao prescriptionDao;

    public PrescriptionService(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionDao.getAllPrescriptions();
    }

    public Prescription getPrescriptionById(int id) {
        return prescriptionDao.getPrescriptionById(id);
    }

    public void savePrescription(Prescription prescription) {
        prescriptionDao.savePrescription(prescription);
    }

    public void updatePrescription(Prescription prescription) {
        prescriptionDao.updatePrescription(prescription);
    }


    public void deletePrescription(int id) {
        prescriptionDao.deletePrescription(id);
    }
}
