package org.hospital.controller.specialization;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.specialization.Specialization;
import org.hospital.service.specialization.SpecializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecializationController implements IController {
    private final SpecializationService specializationService;
    private final Logger logger = LoggerFactory.getLogger(SpecializationController.class);

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/specializations", ctx -> {
            ctx.json(specializationService.getAllSpecializations());
            logger.info("Specializations list retrieved");
        });

        app.get("/specializations/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            logger.info("Getting specialization with ID {}", id);
            Specialization specialization = specializationService.getSpecializationById(id);
            if (specialization != null) {
                ctx.json(specialization);
                logger.info("Returned specialization with ID {}", id);
            } else {
                ctx.status(404).json("Specialization not found");
                logger.debug("Specialization with ID {} not found", id);
            }
        });

    }
}
