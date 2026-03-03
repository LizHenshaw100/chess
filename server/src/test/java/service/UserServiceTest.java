package service;

import dataaccess.exceptions.DataAccessException;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {
    @Test
    public void registerTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);

        assertNotNull(userDao.getUser("liz"));
    }
    @Test
    public void userTakenRegisterTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);
        UserData dup = new UserData("liz", "password123", "email2@email.com");

        assertThrows(DataAccessException.class, () -> {
            service.register(dup);
        });
    }
}
