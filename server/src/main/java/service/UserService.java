package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import model.*;

import java.util.Objects;

public class UserService {
    private UserDao userDao;
    private GameDao gameDao;
    private AuthDao authDao;

    public UserService(GameDao gameDao, UserDao userDao, AuthDao authDao) {
        this.gameDao = gameDao;
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public AuthData register(UserData userData) throws DataAccessException {
        if (userData.getUsername() == null ||
                userData.getPassword() == null ||
                userData.getEmail() == null) {
            throw new BadRequestException();
        }
        userDao.createUser(userData);
        return authDao.createAuth(userData.getUsername());
    }

    public AuthData login(LoginRequest loginRequest) throws DataAccessException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (username == null || password == null) {
            throw new BadRequestException();
        }
        else if (userDao.getUser(username) == null || !Objects.equals(userDao.getUser(username).getPassword(), password)) {
            throw new UnauthorizedException();
        }
        else
        {
            return authDao.createAuth(username);
        }

    }

}
