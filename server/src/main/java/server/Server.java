package server;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.UnauthorizedException;
import io.javalin.*;
import io.javalin.http.UnauthorizedResponse;
import service.AuthService;
import service.ClearService;
import io.javalin.http.Context;
import service.UserService;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    MemoryAuthDAO authDB;
    MemoryGameDAO gameDB;
    MemoryUserDAO userDB;

    AuthService authService;
    ClearService clearService;
    UserService userService;

    ClearHandler clearHandler;
    UserHandler userHandler;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        //initialize mock dbs when the server starts
        this.authDB = new MemoryAuthDAO();
        this.gameDB = new MemoryGameDAO();
        this.userDB = new MemoryUserDAO();

        this.clearService = new ClearService(authDB, gameDB, userDB);
        this.authService = new AuthService(authDB);
        this.userService = new UserService(userDB, authService);

        this.clearHandler = new ClearHandler(clearService);
        this.userHandler = new UserHandler(userService);

        // Register your endpoints and exception handlers here.
        javalin.exception(BadRequestException.class, (e, ctx) -> sendError(ctx, 400, e));
        javalin.exception(UnauthorizedException.class, (e, ctx) -> sendError(ctx, 401, e));
        javalin.exception(AlreadyTakenException.class, (e, ctx) -> sendError(ctx, 403, e));
        javalin.exception(Exception.class, (e, ctx) -> sendError(ctx, 500, e));

        javalin.delete("/db", clearHandler::clear);
        javalin.post("/user", userHandler::register);
        javalin.post("/session", userHandler::login);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    private void sendError(Context ctx, int status, Exception e) {
        var body = new Gson().toJson(Map.of("message", "Error: " + e.getMessage()));
        ctx.status(status);
        ctx.result(body);
    }

    public void stop() {
        javalin.stop();
    }
}
