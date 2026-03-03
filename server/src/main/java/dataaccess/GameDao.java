package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.GameData;


public interface GameDao {
    void clearAllGames();

    int createGame(String gameName) throws DataAccessException;

    GameData getGame(int gameID);

    GameData[] listGames();

    void joinGame(int gameID, chess.ChessGame.TeamColor playerColor, String username);

}
