package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDAO implements GameDAO {
    Gson gson = new Gson();

    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("DELETE FROM game")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
    }

    @Override
    public int addGameData(String whiteUsername, String blackUsername, String gameName, ChessGame game) throws DataAccessException {
        String gameString = gson.toJson(game);

        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setString(1, whiteUsername);
            preparedStatement.setString(2, blackUsername);
            preparedStatement.setString(3, gameName);
            preparedStatement.setString(4, gameString);
            int returnGeneratedKeys = preparedStatement.RETURN_GENERATED_KEYS;
            preparedStatement.executeUpdate();
            return returnGeneratedKeys;
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
    }

    @Override
    public ArrayList<GameData> listGames() {
        return null;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT whiteUsername, blackUsername, gameName, game FROM game WHERE gameID = ?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    String gameName = rs.getString("gameName");
                    String gameString = rs.getString("game");
                    ChessGame game = gson.fromJson(gameString, ChessGame.class);
                    return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
                }
            } catch (SQLException e) {
                throw new DataAccessException("SQL Exception", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
        return null;
    }

    @Override
    public void join(String username, String playerColor, int gameID) {

    }
}
