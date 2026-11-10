package client;

import java.util.Arrays;
import java.util.Scanner;

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
            var tokens = input.toLowerCase().split("");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "quit" -> serverFacade.quit();
                case "login" -> serverFacade.login(params);
                case "register" -> serverFacade.register(params);
                case "logout" -> serverFacade.logout();
                case "creategame" -> serverFacade.createGame(params);
                case "listgames" -> serverFacade.listGames(params);
                case "playgame" -> serverFacade.playGame(params);
                case "observegame" -> serverFacade.observeGame(params);
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws Exception {
        if (params.length >= 1) {

        }
    }

    public void run() {
        System.out.println(LOGO + " Welcome to the pet store. Sign in to start.");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }


    public String eval(String input) {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "signin" -> signIn(params);
                case "rescue" -> rescuePet(params);
                case "list" -> listPets();
                case "signout" -> signOut();
                case "adopt" -> adoptPet(params);
                case "adoptall" -> adoptAllPets();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
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