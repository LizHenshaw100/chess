package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.AuthData;

public interface AuthDao {
    void clearAllAuths();

    AuthData createAuth(String username) throws DataAccessException;

    AuthData getAuth(String auth);

    void deleteAuth(String auth);
}
