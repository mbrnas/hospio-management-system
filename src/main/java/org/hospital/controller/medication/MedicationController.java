package org.hospital.controller.medication;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.medication.Medication;
import org.hospital.service.medication.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MedicationController implements IController {
    private final MedicationService medicationService;

    private final Logger logger = LoggerFactory.getLogger(MedicationController.class);


    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/medications", ctx -> {
            ctx.json(medicationService.getAllMedications());
            logger.info("Medications retrieved successfully");
        });

        app.get("/medication/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Medication medication = medicationService.getMedicationById(id);
            if (medication != null) {
                ctx.json(medication);
                logger.info("Medication retrieved successfully with ID {}", id);
            } else {
                ctx.status(404).json("Medication not found");
                logger.debug("Medication not found with ID {}", id);
            }
        });

        app.post("/insert-medication", ctx -> {
            Medication medication = ctx.bodyAsClass(Medication.class);
            medicationService.saveMedication(medication);
            ctx.status(201).json("Medication added successfully");
            logger.info("Medication added successfully");
        });

        app.put("/update-medication/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Medication medication = ctx.bodyAsClass(Medication.class);
            medication.setMedicationId(id);
            medicationService.updateMedication(medication);
            ctx.status(200).json("Medication updated successfully");
            logger.info("Medication updated successfully with ID {}", id);
        });


        app.delete("/delete-medication/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            medicationService.deleteMedication(id);
            ctx.status(200).json("Medication deleted successfully");
            logger.info("Medication deleted successfully with ID {}", id);
        });
    }
}
