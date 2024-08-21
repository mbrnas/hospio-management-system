package org.hospital.dao.specialization;

import org.hospital.model.specialization.Specialization;

import java.util.List;

public interface SpecializationDao {
    List<Specialization> getAllSpecializations();

    Specialization getSpecializationById(int id);

}
