package dataaccess;

import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.AuthService;
import service.UserService;


public class DatabaseUnitTests {
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    UserService userService;
    AuthService authService;

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO = new SQLUserDAO();
        authDAO = new SQLAuthDAO();
        gameDAO = new SQLGameDAO();
        authService = new AuthService(authDAO);
        userService = new UserService(userDAO, authService);

        try {
            DatabaseManager.createDatabase();
            DatabaseManager.createTables();
            userDAO.clear();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }

        gameDAO.clear();
        userDAO.clear();
    }

    @Test
    @DisplayName("Add User")
    public void addUserTest() throws DataAccessException {
        UserData testUser = new UserData("user", "pass", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUserData(testUser));

        UserData expected = userDAO.getUser("user");
        Assertions.assertEquals(expected, testUser);
    }

    @Test
    @DisplayName("Get User")
    public void getUserTest() throws DataAccessException {
        UserData testUser = new UserData("user", "pass", "email");
        userDAO.addUserData(testUser);

        UserData expected = userDAO.getUser("user");
        Assertions.assertEquals(expected, testUser);
    }

    @Test
    @DisplayName("Get User Not Found")
    public void getUserNotFoundTest() throws DataAccessException {
        UserData expected = userDAO.getUser("user");
        Assertions.assertNull(expected);
    }

    @Test
    @DisplayName("Add Auth")
    public void addAuthSuccess() throws Exception {
        userDAO.addUserData(new UserData("name", "pass", "email"));
        authDAO.addAuth(new AuthData("auth", "name"));
        AuthData authData = authDAO.getAuth("auth");
        Assertions.assertEquals("auth", authData.authToken());
        Assertions.assertEquals("name", authData.username());
    }

    @Test
    @DisplayName("Get Auth")
    public void getAuthSuccess() throws Exception {
        userDAO.addUserData(new UserData("name", "pass", "email"));
        authDAO.addAuth(new AuthData("auth", "name"));
        AuthData authData = authDAO.getAuth("auth");
        Assertions.assertEquals("auth", authData.authToken());
        Assertions.assertEquals("name", authData.username());
    }

    @Test
    @DisplayName("Clear Auth")
    public void clearAuthSuccess() throws Exception {
        userDAO.addUserData(new UserData("name", "pass", "email"));
        authDAO.addAuth(new AuthData("Auth", "name"));
        authDAO.clear();
        Assertions.assertNull(authDAO.getAuth("Auth"));
    }

    @Test
    @DisplayName("Delete Auth")
    public void deleteAuthSuccess() throws Exception {
        userDAO.addUserData(new UserData("name", "pass", "email"));
        authDAO.addAuth(new AuthData("Auth", "name"));
        authDAO.deleteAuth("Auth");
        Assertions.assertNull(authDAO.getAuth("Auth"));
    }
}
