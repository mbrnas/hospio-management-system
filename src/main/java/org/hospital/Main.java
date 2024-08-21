package org.hospital;

import io.javalin.Javalin;
import org.hospital.config.DependencyInjector;
import org.hospital.controller.IController;

import org.hospital.database.DatabaseConfig;

public class Main {
    public static void main(String[] args) {
        DatabaseConfig dbConfig = new DatabaseConfig();
        DependencyInjector injector = new DependencyInjector(dbConfig);

        Javalin app = Javalin.create().start(7070);
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "http://localhost:3000");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            ctx.header("Access-Control-Allow-Credentials", "true");
        });


        app.options("/*", ctx -> {
            ctx.header("Access-Control-Allow-Origin", "http://localhost:3000");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            ctx.status(200);
        });

        for (IController controller : injector.getControllers()) {
            controller.registerEndpoints(app);
        }

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}