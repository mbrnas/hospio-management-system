package org.hospital.controller.prescription;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.prescription.Prescription;
import org.hospital.service.prescription.PrescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrescriptionController implements IController {
    private final PrescriptionService prescriptionService;
    private final Logger logger = LoggerFactory.getLogger(PrescriptionController.class);


    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/prescriptions", ctx -> {
            ctx.json(prescriptionService.getAllPrescriptions());
            logger.info("All prescriptions retrieved");
        });

        app.get("/prescription/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            if (prescription != null) {
                ctx.json(prescription);
                logger.info("Prescription with ID {} retrieved successfully", id);
            } else {
                ctx.status(404).json("Prescription not found");
                logger.debug("Prescription with ID {} not found", id);
            }
        });

        app.post("/insert-prescription", ctx -> {
            Prescription prescription = ctx.bodyAsClass(Prescription.class);
            prescriptionService.savePrescription(prescription);
            ctx.status(201).json("Prescription added successfully");
            logger.info("Prescription added successfully");
        });

        app.put("/update-prescription/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Prescription existingPrescription = prescriptionService.getPrescriptionById(id);
            if (existingPrescription != null) {
                Prescription updatedPrescription = ctx.bodyAsClass(Prescription.class);
                updatedPrescription.setPrescriptionId(id);
                prescriptionService.updatePrescription(updatedPrescription);
                ctx.status(200).json("Prescription updated successfully");
                logger.info("Prescription with ID {} updated successfully", id);
            } else {
                ctx.status(404).json("Prescription not found");
                logger.debug("Prescription with ID {} not found", id);
            }
        });


        app.delete("/delete-prescription/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            prescriptionService.deletePrescription(id);
            ctx.status(200).json("Prescription deleted successfully");
            logger.info("Prescription with ID {} deleted successfully", id);
        });
    }
}
