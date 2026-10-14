package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    HashMap<Integer, GameData> gameDataHashMap = new HashMap<>();

    @Override
    public void clear() {
        gameDataHashMap.clear();
    }
}
