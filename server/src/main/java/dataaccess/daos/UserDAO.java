package dataaccess.daos;

import dataaccess.exceptions.UserTakenException;
import model.UserData;

import java.util.HashMap;

public class UserDAO {
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
