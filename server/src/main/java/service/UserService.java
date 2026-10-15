package service;

import dataaccess.UserDAO;
import model.UserData;

public class UserService {
    UserDAO userDAO;

    public UserService(UserDAO userDao) {
        this.userDAO = userDao;
    }

    public UserData getUser(String username) {
        return userDAO.getUser(username);
    }

    public void createUser(UserData userData) {
        userDAO.addUserData(userData);
    }
}
