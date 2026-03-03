package service;

import dataaccess.exceptions.DataAccessException;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNull;

public class ClearServiceTest {
    @Test
    public void clearTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        userDao.createUser(new UserData("liz", "pass", "email"));

        ClearService service = new ClearService(gameDao, userDao, authDao);
        service.clear();

        assertNull(userDao.getUser("liz"));
    }
}
