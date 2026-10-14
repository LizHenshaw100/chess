package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clear();
    void addAuth(AuthData authData);
}
