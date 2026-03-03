package model;

import chess.ChessGame;
import chess.ChessGame.TeamColor;

import java.util.Objects;

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

    public boolean isAvailable(TeamColor playerColor) {
        return ((playerColor == TeamColor.WHITE && Objects.equals(whiteUsername, null)) || (playerColor == TeamColor.BLACK && Objects.equals(blackUsername, null)));
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
