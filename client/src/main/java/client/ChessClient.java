package client;

import java.util.Arrays;
import java.util.Collection;

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
    private String auth = null;
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
                case "logout" -> logout();
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join" -> join(params);
                //case "observe" -> serverFacade.observeGame(params);
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
        RegisterResult result = serverFacade.register(request);
        state = State.SIGNEDIN;
        visitorName = params[0];
        auth = result.authToken();
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
            visitorName = result.username();
            auth = result.authToken();
            state = State.SIGNEDIN;
            System.out.println("Auth: " + auth);
            return "Welcome back " + visitorName;
        }
        catch(Exception e) {
            throw new Exception("Incorrect username or password");
        }
    }

    public String logout() throws Exception{
        if (state==State.SIGNEDOUT) {
            throw new Exception("You can't log out when you're not signed in silly");
        }
        LogoutRequest request = new LogoutRequest(auth);
        serverFacade.logout(request);
        auth = null;
        visitorName = null;
        state = State.SIGNEDOUT;
        return "Thanks for playing!";
    }

    public String createGame(String... params) throws Exception{
        if (state==State.SIGNEDOUT) {
            throw new Exception("You can't log out when you're not signed in silly");
        }
        if(params.length != 1) {
            throw new Exception("Expected 1 parameter. Try again with just the command create and the the name of the game (no white space)");
        }
        CreateGameRequest request = new CreateGameRequest(auth, params[0]);
        CreateGameResult result = serverFacade.createGame(request, auth);
        return "You have successfully created a game with id " + result.gameID();
    }

    public String listGames() throws Exception{
        if (state!=State.SIGNEDIN) {
            throw new Exception("Sign in first!");
        }
        ListGamesResult result = serverFacade.listGames(auth);
        Collection<GameSummary> games = result.games();
        if (games==null || games.isEmpty()) {
            return "Sorry, there are no games currently. Use create to make one";
        }
        String returnString = "";
        for (GameSummary game : games) {
            returnString = returnString + "\n" + game.gameName() + ": " + game.gameID() + "\n";
            if (game.whiteUsername() != null) {
                returnString = returnString + "white: " + game.whiteUsername();
            }
            else {
                returnString = returnString + "white: AVAILABLE";
            }
            if (game.blackUsername()!=null) {
                returnString = returnString + "\nblack: " + game.blackUsername();
            }
            else {
                returnString = returnString + "\nblack: AVAILABLE";
            }
            returnString = returnString + "\n";
        }
        return returnString ;
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