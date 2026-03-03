package model;

import chess.ChessGame;

public class GameData {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGame game;


    public GameData(int gameID, String whiteUsername, String blackUsername, String gameName) {

        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = new ChessGame();
    }

    public String getName() {
        return this.gameName;
    }

    public int getID() {
        return this.gameID;
    }

    public boolean isAvailable(String playerColor) {
        return ((playerColor == "WHITE" && whiteUsername == "") || (playerColor == "BLACK" && blackUsername == ""));
    }
}
