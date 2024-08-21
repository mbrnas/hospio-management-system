package org.hospital.controller.department;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.model.department.Department;
import org.hospital.service.department.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepartmentController implements IController {
    private final DepartmentService departmentService;

    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get("/departments", ctx -> {
            ctx.json(departmentService.getAllDepartments());
            logger.info("Departments retrieved successfully");
        });

        app.get("/department/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Department department = departmentService.getDepartmentById(id);
            if (department != null) {
                ctx.json(department);
                logger.info("Department retrieved successfully");
            } else {
                ctx.status(404).json("Department not found");
                logger.debug("Department not found");
            }
        });

        app.post("/insert-department", ctx -> {
            Department department = ctx.bodyAsClass(Department.class);
            departmentService.saveDepartment(department);
            ctx.status(201).json("Department added successfully");
            logger.info("Department added successfully");
        });


        app.put("/update-department/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Department department = ctx.bodyAsClass(Department.class);
            department.setDepartmentId(id);
            departmentService.updateDepartment(department);
            ctx.status(200).json("Department updated successfully");
            logger.info("Department updated successfully with ID {}", id);
        });



        app.delete("/delete-department/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            departmentService.deleteDepartment(id);
            ctx.status(200).json("Department deleted successfully");
            logger.info("Department deleted successfully with ID {}", id);
        });
    }
}
