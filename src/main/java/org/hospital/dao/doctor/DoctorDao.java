package org.hospital.dao.doctor;

import org.hospital.model.doctor.Doctor;

import java.util.List;

public interface DoctorDao {
    List<Doctor> getAllDoctors();

    Doctor getDoctorById(int id);

    void saveDoctor(Doctor doctor);

    void deleteDoctor(int id);
}
