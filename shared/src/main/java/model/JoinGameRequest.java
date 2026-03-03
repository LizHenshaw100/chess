package model;
import chess.ChessGame.TeamColor;

public class JoinGameRequest {
    TeamColor playerColor;
    int gameID;
    public JoinGameRequest() {}

    public JoinGameRequest(TeamColor playerColor, int gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public TeamColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(TeamColor playerColor) {
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
