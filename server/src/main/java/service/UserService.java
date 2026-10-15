package service;

import dataaccess.UserDAO;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import model.UserData;

public class UserService {
    UserDAO userDAO;

    public UserService(UserDAO userDao) {
        this.userDAO = userDao;
    }

    public void getUser(String username) throws Exception {
        UserData userData = userDAO.getUser(username);
        if (userData != null) {
            throw new AlreadyTakenException("already taken");
        }
    }

    public void createUser(UserData userData) {
        userDAO.addUserData(userData);
    }

    public RegisterResult register(RegisterRequest request) {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new BadRequestException("{"message": "Error: bad request"}")
        }
        try {
            userService.getUser(username);
        }
        catch (Exception e) {
            ctx.status(403);
            ctx.result(e.body);
        }
        userService.createUser(new UserData(username, password, email));
        String token = authService.createAuth(username);
        ctx.status(200);
        ctx.result(username + token);
    }
}
