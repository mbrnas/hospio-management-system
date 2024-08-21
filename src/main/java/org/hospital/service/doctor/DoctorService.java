package org.hospital.service.doctor;

import org.hospital.dao.doctor.DoctorDao;
import org.hospital.model.doctor.Doctor;

import java.util.List;

public class DoctorService {

    private final DoctorDao doctorDao;

    public DoctorService(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    public List<Doctor> getAllDoctors() {
        return doctorDao.getAllDoctors();
    }

    public Doctor getDoctorById(int id) {
        return doctorDao.getDoctorById(id);
    }

    public void saveDoctor(Doctor doctor) {
        doctorDao.saveDoctor(doctor);
    }

    public void deleteDoctor(int id) {
        doctorDao.deleteDoctor(id);
    }
}
