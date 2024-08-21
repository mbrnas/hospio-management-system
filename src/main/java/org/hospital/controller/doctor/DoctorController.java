package org.hospital.controller.doctor;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.doctor.Doctor;
import org.hospital.service.doctor.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoctorController implements IController {
    private final DoctorService doctorService;

    private final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/doctors", ctx -> {
            ctx.json(doctorService.getAllDoctors());
            logger.info("Doctors list retrieved");
        });

        app.get("/doctor/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Doctor doctor = doctorService.getDoctorById(id);
            if (doctor != null) {
                ctx.json(doctor);
                logger.info("Doctor retrieved with id {}", id);
            } else {
                ctx.status(404).json("Doctor not found");
                logger.debug("Doctor not found with id {}", id);
            }
        });

        app.post("/insert-doctor", ctx -> {
            Doctor doctor = ctx.bodyAsClass(Doctor.class);
            doctorService.saveDoctor(doctor);
            ctx.status(201).json("Doctor added successfully");
            logger.info("Doctor added successfully");
        });

        app.delete("/delete-doctor/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            doctorService.deleteDoctor(id);
            ctx.status(200).json("Doctor deleted successfully");
            logger.info("Doctor deleted successfully with id {}", id);
        });
    }
}
