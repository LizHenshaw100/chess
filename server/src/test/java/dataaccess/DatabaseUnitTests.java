package dataaccess;

import dataaccess.sql.SQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class DatabaseUnitTests {
    UserDAO userDAO;

    @BeforeEach
    public void setup() {
        userDAO = new SQLUserDAO();

        try {
            DatabaseManager.createDatabase();
            DatabaseManager.createTables();
            userDAO.clear();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Add User")
    public void addUserTest() throws DataAccessException {
        UserData testUser = new UserData("user", "pass", "email");
        Assertions.assertDoesNotThrow(() -> userDAO.addUserData(testUser));

        UserData expected = userDAO.getUser("user");
        Assertions.assertEquals(expected, testUser);
    }
}
