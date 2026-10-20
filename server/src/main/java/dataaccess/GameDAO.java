package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    void clear();
    int addGameData(GameData gameData);
    ArrayList<GameData> listGames();
}
