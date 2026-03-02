package dataaccess;

import dataaccess.exceptions.DataAccessException;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDao {
    private final HashMap<String, UserData> users = new HashMap<>();

    public void clearAllUsers(){
        users.clear();
    }

    public void createUser(UserData userData) throws DataAccessException {
        if (users.get(userData.getUsername()) != null) {
            throw new DataAccessException("Username already taken");
        }
        users.put(userData.getUsername(), userData);
    }

    public UserData getUser(String username) {
        return users.get(username);
    }
}
