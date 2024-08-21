package org.hospital.controller.patient;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.patient.Patient;
import org.hospital.service.patient.PatientService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PatientController implements IController {
    private final PatientService patientService;
    private final Logger logger = LoggerFactory.getLogger(PatientController.class);

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/patients", ctx -> {
            ctx.json(patientService.getAllPatients());
            logger.info("All patients retrieved");
        });

        app.get("/patient/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if(id <= 0){
                ctx.status(400).json("Invalid patient ID");
                logger.debug("Invalid patient ID");
                return;
            }
            Patient patient = patientService.getPatientById(id);
            if (patient == null) {
                ctx.status(404).json("Patient with ID " + id + " not found");
                logger.debug("Patient with ID {} not found", id);
            } else {
                ctx.json(patient);
                logger.info("Patient with ID {} retrieved successfully", id);
            }
        });

        app.post("/insert-patient", ctx -> {
            Patient newPatient = ctx.bodyAsClass(Patient.class);
            patientService.savePatient(newPatient);
            ctx.status(201).json("Patient successfully inserted");
            logger.info("Patient successfully inserted");
        });

        app.put("/edit/patient/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if(id <= 0){
                ctx.status(400).json("Invalid patient ID");
                logger.debug("Invalid patient ID");
                return;
            }
            Patient patient = ctx.bodyAsClass(Patient.class);
            patientService.editPatient(patient, id);
            ctx.status(200).json("Patient successfully edited");
            logger.info("Patient with ID {} successfully updated", id);
        });

        app.delete("/delete-patient/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if(id <= 0){
                ctx.status(400).json("Invalid patient ID");
                logger.debug("Invalid patient ID");
                return;
            }
            Patient patient = patientService.getPatientById(id);
            if (patient == null) {
                ctx.status(404).json("Patient with ID " + id + " not found");
                logger.debug("Patient with ID {} not found", id);
            } else {
                patientService.deletePatient(id);
                ctx.json("Patient with ID " + id + " deleted");
                logger.info("Patient with ID {} deleted", id);
            }
        });
    }
}