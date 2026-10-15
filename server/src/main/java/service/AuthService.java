package service;

import dataaccess.AuthDAO;
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

    public String createAuth(String username) {
        String authToken = generateToken();
        AuthData authData = new AuthData(authToken, username);
        authDAO.addAuth(authData);
        return authToken;
    }
}
