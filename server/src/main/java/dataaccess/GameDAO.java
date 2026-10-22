package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    void clear();
    int addGameData(String whiteUsername, String blackUsername, String gameName, ChessGame game);
    ArrayList<GameData> listGames();
    GameData getGame(int gameID);
    void join(String username, String playerColor, int gameID);
}
