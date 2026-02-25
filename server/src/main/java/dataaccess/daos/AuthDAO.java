package dataaccess.daos;

import dataaccess.exceptions.UserTakenException;
import model.AuthData;

import java.util.HashMap;

public class AuthDAO {
    private final HashMap<AuthData, String> auths = new HashMap<>();

    public void clearAllAuths(){
        auths.clear();
    }

    public void createAuth(AuthData authData) {
        auths.put(authData, authData.getUsername());
    }

    public String getAuth(AuthData authData) {
        return auths.get(authData);
    }
}
