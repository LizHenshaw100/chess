package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.GameData;


public interface GameDao {
    void clearAllGames();

    void createGame(GameData gameData) throws DataAccessException;

    GameData getGame(String gameID);

    GameData[] listGames();

    void updateGame(GameData game);
}
