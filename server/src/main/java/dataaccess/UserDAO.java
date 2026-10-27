package dataaccess;

import model.UserData;

public interface UserDAO {
    void clear() throws DataAccessException;
    UserData getUser(String username);
    void addUserData(UserData userData);
}
