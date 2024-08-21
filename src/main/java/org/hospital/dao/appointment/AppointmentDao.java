package org.hospital.dao.appointment;


import org.hospital.model.appointment.Appointment;

import java.util.List;

public interface AppointmentDao {
    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(int id);

    void saveAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    void deleteAppointment(int id);

    List<Appointment> getAppointmentsByPatientId(int patientId);
}
