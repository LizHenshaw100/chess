package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDao{
    private final HashMap<String, AuthData> auths = new HashMap<>();

    public void clearAllAuths(){
        auths.clear();
    }

    public AuthData createAuth(String username) {
        String auth = UUID.randomUUID().toString();
        AuthData authData = new AuthData(auth, username);
        auths.put(auth, authData);
        return authData;
    }

    public AuthData getAuth(String auth) {
        return auths.get(auth);
    }

    public void deleteAuth(String auth) {
        auths.remove(auth);
    }
}
