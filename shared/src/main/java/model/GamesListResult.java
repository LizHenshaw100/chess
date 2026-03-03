package model;

import chess.ChessGame;

import java.util.ArrayList;

public class GamesListResult {
    private GameData[] games;

    public GamesListResult() {
        this.games = new GameData[0];
    }

    public GameData[] getGames() {
        return games;
    }

    public GamesListResult(GameData[] games) {
        this.games = (games != null) ? games : new GameData[0];
    }

}
