package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    HashMap<Integer, GameData> gameDataHashMap = new HashMap<>();
    int gameNumber = 0;

    @Override
    public void clear() {
        gameDataHashMap.clear();
    }

    @Override
    public int addGameData(GameData gameData) {
        gameDataHashMap.put(gameNumber, gameData);
        gameNumber++;
        return gameNumber;
    }
}
