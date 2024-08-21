package org.hospital.controller.appointment;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.dao.medical_records.MedicalRecordDaoImpl;
import org.hospital.model.appointment.Appointment;
import org.hospital.service.appointment.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AppointmentController implements IController {
    private final AppointmentService appointmentService;

    private final Logger logger = LoggerFactory.getLogger(AppointmentController.class);


    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/appointments", ctx -> {
            ctx.json(appointmentService.getAllAppointments());
            logger.info("Getting all appointments from controller endpoint");
        });

        app.get("/appointments/patient/{patientId}", ctx -> {
            int patientId = Integer.parseInt(ctx.pathParam("patientId"));
            List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
            if (appointments.isEmpty()) {
                ctx.json(new ArrayList<>());
            } else {
                ctx.json(appointments);
            }
            logger.info("Returned all appointments for patient ID {}", patientId);
        });

        app.get("/appointment/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            logger.info("Getting appointment with ID {}", id);
            Appointment appointment = appointmentService.getAppointmentById(id);
            if (appointment != null) {
                ctx.json(appointment);
                logger.info("Returned appointment with ID {}", id);
            } else {
                ctx.status(404).json("Appointment not found");
                logger.debug("Appointment with ID {} not found", id);
            }
        });

        app.post("/insert-appointment", ctx -> {
            Appointment appointment = ctx.bodyAsClass(Appointment.class);
            appointmentService.saveAppointment(appointment);
            ctx.status(201).json("Appointment added successfully");
            logger.info("Appointment added successfully");
        });

        app.put("/update-appointment/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Appointment appointment = ctx.bodyAsClass(Appointment.class);
            appointment.setAppointmentId(id);
            appointmentService.updateAppointment(appointment);
            ctx.status(200).json("Appointment updated successfully");
            logger.info("Appointment updated successfully with ID {}", id);
        });

        app.delete("/delete-appointment/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            appointmentService.deleteAppointment(id);
            ctx.status(200).json("Appointment deleted successfully");
            logger.info("Appointment deleted successfully with ID {}", id);
        });
    }
}
