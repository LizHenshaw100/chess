package server;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import service.ClearService;
import io.javalin.http.Context;

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

        // Register your endpoints and exception handlers here.
        this.clearService = new ClearService(authDB, gameDB, userDB);
        this.clearHandler = new ClearHandler(clearService);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void clear(Context ctx) {
        clearHandler.clear(ctx);
    }

    public void stop() {
        javalin.stop();
    }
}
