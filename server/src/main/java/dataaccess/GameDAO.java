package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    void clear();
    int addGameData(GameData gameData);
    ArrayList<GameData> listGames();
    GameData getGame(int gameID);
    void join(String username, String playerColor, int gameID);
}
