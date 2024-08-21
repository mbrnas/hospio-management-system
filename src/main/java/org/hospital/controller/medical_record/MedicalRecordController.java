package org.hospital.controller.medical_record;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.mrecord.MedicalRecord;
import org.hospital.service.medical_record.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordController implements IController {
    private final MedicalRecordService medicalRecordService;
    private final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);


    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/medical-records", ctx -> {
            ctx.json(medicalRecordService.getAllMedicalRecords());
            logger.info("Medical records retrieved successfully");
        });

        app.get("/medical-record/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);
            if (medicalRecord != null) {
                ctx.json(medicalRecord);
                logger.info("Medical record retrieved successfully with ID {}", id);
            } else {
                ctx.status(404).json("Medical record not found");
                logger.debug("Medical record not found with ID {}", id);
            }
        });

        app.get("/medical-records/patient/{patientId}", ctx -> {
            int patientId = Integer.parseInt(ctx.pathParam("patientId"));
            List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPatientId(patientId);
            if (medicalRecords.isEmpty()) {
                ctx.json(new ArrayList<>());
            } else {
                ctx.json(medicalRecords);
            }
            logger.info("Medical records retrieved successfully for patient with ID {}", patientId);
        });

        app.post("/insert-medical-record", ctx -> {
            MedicalRecord medicalRecord = ctx.bodyAsClass(MedicalRecord.class);
            medicalRecordService.saveMedicalRecord(medicalRecord);
            ctx.status(201).json("Medical record added successfully");
            logger.info("Medical record added successfully");
        });

        app.put("/update-medical-record/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            MedicalRecord medicalRecord = ctx.bodyAsClass(MedicalRecord.class);
            medicalRecord.setRecordId(id); // Assuming you want to set the recordId in the MedicalRecord object
            medicalRecordService.updateMedicalRecord(medicalRecord);
            ctx.status(200).json("Medical record updated successfully");
            logger.info("Medical record updated successfully with ID {}", id);
        });



        app.delete("/delete-medical-record/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            medicalRecordService.deleteMedicalRecord(id);
            ctx.status(200).json("Medical record deleted successfully");
            logger.info("Medical record deleted successfully with ID {}", id);
        });
    }
}
