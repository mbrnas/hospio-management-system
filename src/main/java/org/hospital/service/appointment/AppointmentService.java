package org.hospital.service.appointment;

import org.hospital.dao.appointment.AppointmentDao;
import org.hospital.model.appointment.Appointment;

import java.util.List;

public class AppointmentService {

    private final AppointmentDao appointmentDao;

    public AppointmentService(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDao.getAllAppointments();
    }

    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        return appointmentDao.getAppointmentsByPatientId(patientId);
    }

    public Appointment getAppointmentById(int id) {
        return appointmentDao.getAppointmentById(id);
    }

    public void saveAppointment(Appointment appointment) {
        appointmentDao.saveAppointment(appointment);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentDao.updateAppointment(appointment);
    }

    public void deleteAppointment(int id) {
        appointmentDao.deleteAppointment(id);
    }
}