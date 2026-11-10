package client;

import java.util.Arrays;

import requestresultclasses.*;

import com.google.gson.Gson;

/*
import exception.ResponseException;
import client.websocket.NotificationHandler;
import server.ServerFacade;
import client.websocket.WebSocketFacade;
import webSocketMessages.Notification;

import static client.EscapeSequences.*;
 */

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade serverFacade;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;
    private Repl repl;

    public ChessClient(String serverUrl, Repl repl) throws Exception {
        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.repl = repl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split("\\s");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                /*case "logout" -> serverFacade.logout();
                case "creategame" -> serverFacade.createGame(params);
                case "listgames" -> serverFacade.listGames(params);
                case "playgame" -> serverFacade.playGame(params);
                case "observegame" -> serverFacade.observeGame(params);*/
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws Exception {
        if (state==State.SIGNEDIN) {
            throw new Exception("You don't need to create an account, you're already signed in!");
        }
        if (params.length != 3) {
            throw new Exception("Incorrect number of parameters, expected 3");
        }
        RegisterRequest request = new RegisterRequest(params[0], params[1], params[2]);
        serverFacade.register(request);
        state = State.SIGNEDIN;
        return "You have successfully registered and have been logged in!";
    }

    public String login(String... params) throws Exception {
        if (state==State.SIGNEDIN) {
            throw new Exception("You don't need to login, you're already signed in!");
        }
        if (params.length != 2) {
            throw new Exception("Incorrect number of parameters, expected 2");
        }
        try {
            LoginRequest request = new LoginRequest(params[0], params[1]);
            LoginResult result = serverFacade.login(request);
            return "Welcome back " + result.username();
        }
        catch(Exception e) {
            throw new Exception("Incorrect username or password");
        }

    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - help - with possible commands
                    - quit - playing chess
                    - login <USERNAME> <PASSWORD> - to play chess
                    - register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    """;
        }
        return """
                - create <NAME> - a game
                - list - games
                - join <ID> [BLACK|WHITE] - a game
                - observe <ID> - a game
                - logout - when you are done
                - quit - playing chess
                - help - with possible commands
                """;
    }

    private void assertSignedIn() throws Exception {
        if (state == State.SIGNEDOUT) {
            throw new Exception();
        }
    }
}