package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDao{
    private final HashMap<AuthData, String> auths = new HashMap<>();

    public void clearAllAuths(){
        auths.clear();
    }

    public AuthData createAuth(String username) {
        AuthData authData = new AuthData(UUID.randomUUID().toString(), username);
        auths.put(authData, username);
        return authData;
    }

    public String getAuth(AuthData authData) {
        return auths.get(authData);
    }
}
