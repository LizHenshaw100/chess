package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    void clear() throws DataAccessException;
    int addGameData(String whiteUsername, String blackUsername, String gameName, ChessGame game) throws DataAccessException;
    ArrayList<GameData> listGames();
    GameData getGame(int gameID) throws DataAccessException;
    void join(String username, String playerColor, int gameID);
}
