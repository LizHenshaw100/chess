package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.GameData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLGameDAO implements GameDAO {
    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

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
             var preparedStatement = conn.prepareStatement("INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, whiteUsername);
            preparedStatement.setString(2, blackUsername);
            preparedStatement.setString(3, gameName);
            preparedStatement.setString(4, gameString);
            preparedStatement.executeUpdate();
            try (var keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            catch (SQLException e) {
                throw new DataAccessException("SQL Exception", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
        return 0;
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        ArrayList<GameData> games = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int gameID = rs.getInt("gameID");
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    String gameName = rs.getString("gameName");
                    String gameString = rs.getString("game");
                    ChessGame game = gson.fromJson(gameString, ChessGame.class);
                    games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
                }
                return games;
            } catch (SQLException e) {
                throw new DataAccessException("SQL Exception", e);
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
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
    public void join(String username, String playerColor, int gameID) throws DataAccessException {
        String sqlString;
        if ("WHITE".equals(playerColor)) {
            sqlString = "UPDATE game SET whiteUsername = ? WHERE gameID = ?;";
        } else {
            sqlString = "UPDATE game SET blackUsername = ? WHERE gameID = ?;";
        }
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(sqlString)) {

            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
    }
}
