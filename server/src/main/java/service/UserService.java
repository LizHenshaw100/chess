package service;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.UnauthorizedException;
import model.AuthData;
import model.UserData;
import service.RequestResultClasses.*;

public class UserService {
    UserDAO userDAO;
    AuthService authService;

    public UserService(UserDAO userDao, AuthService authS) {
        this.userDAO = userDao;
        this.authService = authS;
    }

    public UserData getUser(String username) {
        return userDAO.getUser(username);
    }

    public void createUser(UserData userData) {
        userDAO.addUserData(userData);
    }

    private String generateToken(String username) throws DataAccessException {
        return authService.createAuth(username);
    }

    public RegisterResult register(RegisterRequest request) throws Exception {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new BadRequestException("bad request");
        }
        UserData userData = getUser(request.username());
        if (userData != null) {
            throw new AlreadyTakenException("already taken");
        }
        createUser(new UserData(request.username(), request.password(), request.email()));
        String token = generateToken(request.username());
        return new RegisterResult(request.username(), token);
    }

    public LoginResult login(LoginRequest request) throws Exception {
        String username = request.username();
        String password = request.password();
        if (username == null || password == null) {
            throw new BadRequestException("bad request");
        }
        UserData userData = getUser(username);
        if (userData == null || !userData.password().equals(password)) {
            throw new UnauthorizedException("unauthorized");
        }
        String authToken = generateToken(username);
        return new LoginResult(username, authToken);
    }

    public void logout(LogoutRequest request) throws Exception{
        AuthData authData = authService.getAuthData(request.authToken());
        if (authData == null) {
            throw new UnauthorizedException("unauthorized");
        }
        authService.deleteAuth(request.authToken());
    }
}
