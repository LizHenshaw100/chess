package service;

import dataaccess.*;
import dataaccess.exceptions.DataAccessException;
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

    public void register(UserData userData) throws DataAccessException {
        userDao.createUser(userData);
    }

}
