package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    HashMap<String, UserData> userDataHashMap = new HashMap<>();

    @Override
    public void clear() {
        userDataHashMap.clear();
    }
}
