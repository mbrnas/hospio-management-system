package org.hospital.controller.staff;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.staff.Staff;
import org.hospital.service.staff.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StaffController implements IController {
    private final StaffService staffService;

    private final Logger logger = LoggerFactory.getLogger(StaffController.class);


    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/staff", ctx -> {
            ctx.json(staffService.getAllStaff());
            logger.info("Staff list retrieved");
        });

        app.get("/staff/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Staff staff = staffService.getStaffById(id);
            if (staff != null) {
                ctx.json(staff);
                logger.info("Staff with ID {} retrieved", id);
            } else {
                ctx.status(404).json("Staff not found");
                logger.debug("Staff with ID {} not found", id);
            }
        });

        app.post("/insert-staff", ctx -> {
            Staff staff = ctx.bodyAsClass(Staff.class);
            staffService.saveStaff(staff);
            ctx.status(201).json("Staff added successfully");
            logger.info("Staff added successfully");
        });

        app.put("/update-staff/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Staff staff = ctx.bodyAsClass(Staff.class);
            staff.setStaffId(id); // Ensure the staff ID is set correctly
            staffService.updateStaff(staff);
            ctx.status(200).json("Staff updated successfully");
            logger.info("Staff with ID {} updated successfully", id);
        });


        app.delete("/delete-staff/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            staffService.deleteStaff(id);
            ctx.status(200).json("Staff deleted successfully");
            logger.info("Staff with ID {} deleted successfully", id);
        });
    }
}
