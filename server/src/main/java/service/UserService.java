package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import model.AuthData;
import model.UserData;

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

}
