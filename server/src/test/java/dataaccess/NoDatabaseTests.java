package dataaccess;

import chess.ChessGame;
import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.AuthService;
import service.UserService;

public class NoDatabaseTests {
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

        gameDAO.clear();
        userDAO.clear();
    }

    @Test
    @DisplayName("Join invalid Game")
    public void joinInvalidGame() throws DataAccessException {
        int id1 = gameDAO.addGameData(null, null, "Test Game", new ChessGame());
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.join("a".repeat(300), "BLACK", id1));
    }

    @Test
    @DisplayName("Create invalid Game")
    public void createInvalidGame() {
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.addGameData(null, "a".repeat(300), "Test Game", new ChessGame()));
    }
}
