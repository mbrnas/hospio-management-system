package org.hospital.controller;

import io.javalin.Javalin;

public interface IController {
    void registerEndpoints(Javalin app);
}
