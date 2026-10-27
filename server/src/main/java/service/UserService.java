package service;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.UnauthorizedException;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import service.requestresultclasses.*;

public class UserService {
    UserDAO userDAO;
    AuthService authService;

    public UserService(UserDAO userDao, AuthService authS) {
        this.userDAO = userDao;
        this.authService = authS;
    }

    public UserData getUser(String username) throws DataAccessException {
        return userDAO.getUser(username);
    }

    public void createUser(UserData userData) throws DataAccessException {
        userDAO.addUserData(userData);
    }

    private String generateToken(String username) throws DataAccessException {
        return authService.createAuth(username);
    }

    public RegisterResult register(RegisterRequest request) throws Exception {
        String password = request.password();
        if (request.username() == null || password == null || request.email() == null) {
            throw new BadRequestException("bad request");
        }
        UserData userData = getUser(request.username());
        if (userData != null) {
            throw new AlreadyTakenException("already taken");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        createUser(new UserData(request.username(), hashedPassword, request.email()));
        String token = generateToken(request.username());
        return new RegisterResult(request.username(), token);
    }

    public LoginResult login(LoginRequest request) throws Exception {
        String username = request.username();
        String password = request.password();
        if (username == null || password == null) {
            throw new BadRequestException("bad request");
        }
        UserData userData = userDAO.getUser(username);
        if ((userData == null) || !BCrypt.checkpw(password, getUser(username).password())) {
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
