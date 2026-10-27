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
    public UserData getUser(String username) {
        // return userDataHashMap.get(username);
        return new UserData("","","");
    }

    @Override
    public void addUserData(UserData userData) {
        // userDataHashMap.put(userData.username(), userData);
    }
}
