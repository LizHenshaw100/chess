package service;

import dataaccess.UserDAO;
import model.UserData;

public class UserService {
    UserDAO userDAO;

    public UserService(UserDAO userDao) {
        this.userDAO = userDao;
    }

    public UserData getUser(String username) {
        UserData userData = userDAO.getUser(username);
        if (userData != null) {
            return throw new
        }
    }

    public void createUser(UserData userData) {
        userDAO.addUserData(userData);
    }
}
