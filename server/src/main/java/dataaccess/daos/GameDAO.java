package dataaccess.daos;

import model.GameData;

import java.util.HashMap;

public class GameDAO {
    private final HashMap<String, GameData> games = new HashMap<>();

    public void clearAllGames(){
        games.clear();
    }

    public void createGame(GameData gameData) {
        games.put(gameData.getID(), gameData);
    }

    public GameData getGame(String gameID) {
        return games.get(gameID);
    }

    public GameData[] listGames() {
        return games.values().toArray(new GameData[0]);
    }

    public void updateGame(GameData game) {
        games.put(game.getID(), game);
    }
}
