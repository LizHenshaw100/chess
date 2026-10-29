package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;

import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement("DELETE FROM auth")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQL Exception", e);
        }
    }

    @Override
    public void addAuth(AuthData authData) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }
}
