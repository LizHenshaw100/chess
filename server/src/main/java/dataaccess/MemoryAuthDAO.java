package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {
    HashMap<String, AuthData> authDataHashMap = new HashMap<>();

    @Override
    public void clear() {
        this.authDataHashMap.clear();
    }

}
