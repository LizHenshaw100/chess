package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.AuthData;
import model.UserData;

public interface AuthDao {
    void clearAllAuths();

    void createAuth(AuthData authData) throws DataAccessException;

    String getAuth(AuthData authData);
}
