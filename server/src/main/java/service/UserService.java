package service;

import dataaccess.UserDAO;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import model.UserData;

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

    public RegisterResult register(RegisterRequest request) throws Exception {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new BadRequestException("bad request");
        }
        UserData userData = getUser(request.username());
        if (userData != null) {
            throw new AlreadyTakenException("already taken");
        }
        createUser(new UserData(request.username(), request.password(), request.email()));
        String token = authService.createAuth(request.username());
        return new RegisterResult(request.username(), token);
    }
}
