package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {
    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("DELETE FROM users")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
    }

    @Override
    public UserData getUser(String username1) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username1);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    return new UserData(username, password, email);
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
    public void addUserData(UserData userData) {
        // userDataHashMap.put(userData.username(), userData);
    }
}
