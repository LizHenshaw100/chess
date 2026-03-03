package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.AuthData;
import model.UserData;

public interface UserDao {
    void clearAllUsers();

    void createUser(UserData userData) throws DataAccessException;

    UserData getUser(String username);
}
