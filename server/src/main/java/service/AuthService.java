package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

import java.util.UUID;

public class AuthService {
    AuthDAO authDAO;
    public AuthService(AuthDAO authDao) {
        this.authDAO = authDao;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String createAuth(String username) throws DataAccessException {
        String authToken = generateToken();
        AuthData authData = new AuthData(authToken, username);
        authDAO.addAuth(authData);
        return authToken;
    }

    public AuthData getAuthData(String authToken) throws DataAccessException {
        return authDAO.getAuth(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        authDAO.deleteAuth(authToken);
    }
}
