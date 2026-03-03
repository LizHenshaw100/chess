package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.AuthData;

public interface AuthDao {
    void clearAllAuths();

    AuthData createAuth(String username) throws DataAccessException;

    String getAuth(AuthData authData);
}
