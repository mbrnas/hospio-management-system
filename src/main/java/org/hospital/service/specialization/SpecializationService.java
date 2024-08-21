package org.hospital.service.specialization;

import org.hospital.dao.specialization.SpecializationDaoImpl;
import org.hospital.model.specialization.Specialization;

import java.util.List;

public class SpecializationService {

    private final SpecializationDaoImpl specializationDao;

    public SpecializationService(SpecializationDaoImpl specializationDao) {
        this.specializationDao = specializationDao;
    }
    public List<Specialization> getAllSpecializations() {
        return specializationDao.getAllSpecializations();
    }

    public Specialization getSpecializationById(int id) {
        return specializationDao.getSpecializationById(id);
    }
}
