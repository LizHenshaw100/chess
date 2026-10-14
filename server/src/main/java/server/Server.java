package server;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import service.ClearService;
import io.javalin.http.Context;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    MemoryAuthDAO authDB;
    MemoryGameDAO gameDB;
    MemoryUserDAO userDB;

    ClearService clearService;
    ClearHandler clearHandler;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        //initialize mock dbs when the server starts
        this.authDB = new MemoryAuthDAO();
        this.gameDB = new MemoryGameDAO();
        this.userDB = new MemoryUserDAO();

        this.clearService = new ClearService(authDB, gameDB, userDB);
        this.clearHandler = new ClearHandler(clearService);

        // Register your endpoints and exception handlers here.
        javalin.exception(Exception.class, (e, ctx) -> sendError(ctx, 500, e));

        javalin.delete("/db", clearHandler::clear);
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
