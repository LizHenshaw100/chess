package dataaccess;

import model.AuthData;
import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDao {
    private final HashMap<Integer, GameData> games = new HashMap<>();

    public void clearAllGames(){
        games.clear();
    }

    public int createGame(String gameName) {
        int gameId = 10000 + new java.util.Random().nextInt(90000);
        GameData gameData = new GameData(gameId, null, null, gameName);
        games.put(gameId, gameData);
        return gameId;
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    public GameData[] listGames() {
        return games.values().toArray(new GameData[0]);
    }

    public void updateGame(GameData game) {
        games.put(game.getID(), game);
    }

    public void joinGame(int gameID, chess.ChessGame.TeamColor playerColor, String username) {
        if (playerColor == chess.ChessGame.TeamColor.WHITE) {
            games.get(gameID).setWhiteUsername(username);
        }
        else {
            games.get(gameID).setBlackUsername(username);
        }

    }
}
