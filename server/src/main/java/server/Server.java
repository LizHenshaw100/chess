package server;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        //initialize mock dbs when the server starts
        MemoryAuthDAO authDB = new MemoryAuthDAO();
        MemoryGameDAO gameDB = new MemoryGameDAO();
        MemoryUserDAO userDB = new MemoryUserDAO();

        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
