package dataaccess;

import dataaccess.exceptions.*;
import model.AuthData;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDao {
    private final HashMap<String, UserData> users = new HashMap<>();

    public void clearAllUsers(){
        users.clear();
    }

    public void createUser(UserData userData) throws UserTakenException {
        if (users.get(userData.getUsername()) != null) {
            throw new UserTakenException();
        }
        users.put(userData.getUsername(), userData);
    }

    public UserData getUser(String username) {
        return users.get(username);
    }
}
