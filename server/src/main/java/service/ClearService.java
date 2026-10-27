package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

import javax.xml.crypto.Data;

public class ClearService {
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserDAO userDAO;

    public ClearService(AuthDAO authDao, GameDAO gameDao, UserDAO userDao) {
        this.authDAO = authDao;
        this.gameDAO = gameDao;
        this.userDAO = userDao;
    }

    public void clear() throws DataAccessException {
        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
    }
}
