package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    HashMap<String, UserData> userDataHashMap = new HashMap<>();

    @Override
    public void clear() {
        userDataHashMap.clear();
    }

    @Override
    public UserData getUser(String username) {
        return userDataHashMap.get(username);
    }

    @Override
    public void addUserData(UserData userData) {
        userDataHashMap.put(userData.username(), userData);
    }
}
