package server;

import com.google.gson.Gson;
import dataaccess.*;
import dataaccess.sql.*;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.UnauthorizedException;
import io.javalin.*;
import service.AuthService;
import service.ClearService;
import io.javalin.http.Context;
import service.GameService;
import service.UserService;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    AuthDAO authDB;
    GameDAO gameDB;
    UserDAO userDB;

    AuthService authService;
    ClearService clearService;
    UserService userService;
    GameService gameService;

    ClearHandler clearHandler;
    UserHandler userHandler;
    GameHandler gameHandler;

    DatabaseManager databaseManager;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        //initialize db
        try {
            databaseManager.createDatabase();
            databaseManager.createTables();
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Couldn't initialize db", e);
        }

        //initialize daos when the server starts
        this.authDB = new MemoryAuthDAO();
        this.gameDB = new SQLGameDAO();
        this.userDB = new SQLUserDAO();

        this.clearService = new ClearService(authDB, gameDB, userDB);
        this.authService = new AuthService(authDB);
        this.userService = new UserService(userDB, authService);
        this.gameService = new GameService(authService, gameDB);

        this.clearHandler = new ClearHandler(clearService);
        this.userHandler = new UserHandler(userService);
        this.gameHandler = new GameHandler(gameService);

        this.databaseManager = new DatabaseManager();

        // Register your endpoints and exception handlers here.
        javalin.exception(BadRequestException.class, (e, ctx) -> sendError(ctx, 400, e));
        javalin.exception(UnauthorizedException.class, (e, ctx) -> sendError(ctx, 401, e));
        javalin.exception(AlreadyTakenException.class, (e, ctx) -> sendError(ctx, 403, e));
        javalin.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            sendError(ctx, 500, e);
        });

        javalin.delete("/db", clearHandler::clear);
        javalin.post("/user", userHandler::register);
        javalin.post("/session", userHandler::login);
        javalin.delete("/session", userHandler::logout);
        javalin.post("/game", gameHandler::createGame);
        javalin.get("/game", gameHandler::listGames);
        javalin.put("/game", gameHandler::joinGame);
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
