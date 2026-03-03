package service;

import dataaccess.*;
import dataaccess.exceptions.DataAccessException;

public class ClearService {
    private UserDao userDao;
    private GameDao gameDao;
    private AuthDao authDao;

    public ClearService(GameDao gameDao, UserDao userDao, AuthDao authDao) {
        this.gameDao = gameDao;
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public void clear() throws DataAccessException {
        userDao.clearAllUsers();
        gameDao.clearAllGames();
        authDao.clearAllAuths();
    }

}
