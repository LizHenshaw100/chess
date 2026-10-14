package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserDAO userDAO;

    public ClearService(AuthDAO authDao, GameDAO gameDao, UserDAO userDao) {
        this.authDAO = authDao;
        this.gameDAO = gameDao;
        this.userDAO = userDao;
    }

    public void clear(){
        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
    }
}
