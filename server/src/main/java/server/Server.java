package server;

import dataaccess.*;
import dataaccess.exceptions.*;
import io.javalin.*;
import model.*;
import service.*;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    private UserDao userDao = new MemoryUserDAO();
    private AuthDao authDao = new MemoryAuthDAO();
    private GameDao gameDao = new MemoryGameDAO();

    private ClearService clearService = new ClearService(gameDao, userDao, authDao);
    private UserService userService = new UserService(gameDao, userDao, authDao);
    private GameService gameService = new GameService(gameDao, userDao, authDao);

    public Server() {

        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        javalin.get("/", ctx -> ctx.redirect("/index.html"));

        // Register your endpoints and exception handlers here.
        registerEndpoints();
        registerExceptionHandlers();
    }

    private void registerEndpoints() {
        // Clear
        javalin.delete("/db", ctx -> {
            clearService.clear();
            ctx.status(200);
            ctx.json(Map.of());
        });
        // Register
        javalin.post("/user", ctx -> {
            UserData userData = ctx.bodyAsClass(UserData.class);
            AuthData authData = userService.register(userData);

            ctx.status(200);
            ctx.json(authData);
        });
        // Login
        javalin.post("/session", ctx -> {
            LoginRequest loginRequest = ctx.bodyAsClass(LoginRequest.class);
            AuthData authData = userService.login(loginRequest);

            ctx.status(200);
            ctx.json(authData);
        });
        // Logout
        javalin.delete("/session", ctx -> {
            String authToken = ctx.header("authorization");
            userService.logout(authToken);

            ctx.status(200);
            ctx.json(Map.of());
        });
        // List Games
        javalin.get("/game", ctx -> {
            String authToken = ctx.header("authorization");
            GamesListResult gamesListResult = gameService.getGamesList(authToken);

            ctx.status(200);
            ctx.json(gamesListResult);
        });
        // Create Game
        javalin.post("/game", ctx -> {
            String authToken = ctx.header("authorization");
            CreateGameRequest createGameRequest = ctx.bodyAsClass(CreateGameRequest.class);
            CreateGameResult createGameResult = new CreateGameResult(gameService.createGame(authToken, createGameRequest.getGameName()));

            ctx.status(200);
            ctx.json(createGameResult);
        });
        // Join Game
        javalin.put("/game", ctx -> {
            String authToken = ctx.header("authorization");
            JoinGameRequest joinGameRequest = ctx.bodyAsClass(JoinGameRequest.class);
            gameService.joinGame(authToken, joinGameRequest.getPlayerColor(), joinGameRequest.getGameID());

            ctx.status(200);
        });
    }

    private void registerExceptionHandlers() {
        javalin.exception(UserTakenException.class, (e, ctx) -> {
            ctx.status(403);
            ctx.json(Map.of("message", "Error: " + e.getMessage()));
        });

        javalin.exception(AlreadyTakenException.class, (e, ctx) -> {
            ctx.status(403);
            ctx.json(Map.of("message", "Error: " + e.getMessage()));
        });

        javalin.exception(UnauthorizedException.class, (e, ctx) -> {
            ctx.status(401);
            ctx.json(Map.of("message", "Error: " + e.getMessage()));
        });

        javalin.exception(BadRequestException.class, (e, ctx) -> {
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
