package server;

import dataaccess.*;
import dataaccess.exceptions.DataAccessException;
import io.javalin.*;
import service.*;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    private UserDao userDao = new MemoryUserDAO();
    private AuthDao authDao = new MemoryAuthDAO();
    private GameDao gameDao = new MemoryGameDAO();

    private ClearService clearService = new ClearService(gameDao,userDao, authDao);
    private UserService userService = new UserService(gameDao,userDao, authDao);

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        javalin.get("/", ctx -> ctx.redirect("/index.html"));
        // Register your endpoints and exception handlers here.
        registerEndpoints();
        registerExceptionHandlers();
    }

    private void registerEndpoints() {
        // Clear
        javalin.delete("/db", ctx -> {clearService.clear(); ctx.status(200); ctx.json(Map.of());});
        // Register
        javalin.post("/user", ctx -> {
            try {
                RegisterRequest request = ctx.bodyAsClass(RegisterRequest.class);
                RegisterResult result = registerService.register(request);

                ctx.status(200);
                ctx.json(result);
            } catch (DataAccessException e) {
                ctx.status(400);
                ctx.json(Map.of("message", "Error: " + e.getMessage()));
            }
        });}

    private void registerExceptionHandlers() {
        javalin.exception(DataAccessException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.json(Map.of("message", "Error: " + e.getMessage()));
        });

        javalin.exception(Exception.class, (e, ctx) -> {
            ctx.status(500);
            ctx.json(Map.of("message", "Error: " + e.getMessage()));
        });
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
