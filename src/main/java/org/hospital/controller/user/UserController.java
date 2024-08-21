package org.hospital.controller.user;

import io.javalin.Javalin;
import org.hospital.controller.IController;
import org.hospital.exception.UnauthorizedAccessException;
import org.hospital.model.user.User;
import org.hospital.model.user.UserRole;
import org.hospital.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class UserController implements IController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    public UserController(UserService userService) {
        this.userService = userService;
    }

    private void checkUserRole(User user, UserRole requiredRole) throws UnauthorizedAccessException {
        if (user.getRole() != requiredRole) {
            logger.error("User with ID {} does not have the required role", user.getUserId());
            throw new UnauthorizedAccessException();
        }
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.post("/login", ctx -> {
            User loginUser = ctx.bodyAsClass(User.class);
            boolean isAuthenticated = userService.authenticateUser(loginUser.getUsername(), loginUser.getPassword());
            if (isAuthenticated) {
                User user = userService.findUserByUsername(loginUser.getUsername());
                ctx.sessionAttribute("currentUser", user);
                if (user.getRole() == UserRole.PATIENT) {
                    int patientId = userService.getPatientIdForUser(user.getUserId());
                    ctx.json(Map.of("role", user.getRole().toString(), "patientId", patientId));
                } else {
                    ctx.json(Map.of("role", user.getRole().toString()));
                }

                ctx.status(200);
                logger.info("User with ID {} logged in", user.getUserId());
            } else {
                ctx.status(401).json("Invalid credentials");
                logger.debug("Invalid credentials");
            }
        });

        app.get("/users/patients", ctx -> {
            List<User> patientUsers = userService.findAllPatients();
            ctx.json(patientUsers);
            logger.info("All patient users retrieved");
        });

        app.post("/register", ctx -> {
            User newUser = ctx.bodyAsClass(User.class);
            userService.registerUser(newUser);
            ctx.status(201).json("User successfully registered");
            logger.info("User with ID {} registered", newUser.getUserId());
        });

        app.post("/logout", ctx -> {
            ctx.sessionAttributeMap().clear();
            ctx.status(200).json("Logged out successfully");
            logger.info("User logged out");
        });


    }


}
