package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    HashMap<Integer, GameData> gameDataHashMap = new HashMap<>();
    int gameNumber = 0;

    @Override
    public void clear() {
        gameDataHashMap.clear();
    }

    @Override
    public int addGameData(String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        gameNumber++;
        gameDataHashMap.put(gameNumber, new GameData(gameNumber, whiteUsername, blackUsername, gameName, game));
        return gameNumber;
    }

    @Override
    public ArrayList<GameData> listGames() {
        return new ArrayList<>(gameDataHashMap.values());
    }

    @Override
    public GameData getGame(int gameID) {
        return gameDataHashMap.get(gameID);
    }

    @Override
    public void join(String username, String playerColor, int gameID) {
        GameData old = getGame(gameID);
        if ("WHITE".equals(playerColor)) {
            gameDataHashMap.put(gameID, new GameData(gameID, username, old.blackUsername(), old.gameName(), old.game()));
        }
        else {
            gameDataHashMap.put(gameID, new GameData(gameID, old.whiteUsername(), username, old.gameName(), old.game()));
        }
    }
}
