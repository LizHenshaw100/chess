package dataaccess;

import chess.ChessGame;
import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.AuthService;
import service.UserService;

import java.util.ArrayList;


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

    @Test
    @DisplayName("Add Game Data")
    public void addGameData() throws Exception {
        int id = gameDAO.addGameData(null, "Player1", "Test Game", new ChessGame());
        Assertions.assertEquals(gameDAO.getGame(id), new GameData(id, null, "Player1", "Test Game", new ChessGame()));
    }

    @Test
    @DisplayName("List Games")
    public void listGames() throws Exception {
        int id1 = gameDAO.addGameData(null, "Player1", "Test Game", new ChessGame());
        int id2 = gameDAO.addGameData("Player4", null, "Test Game", new ChessGame());
        ArrayList<GameData> games = new ArrayList<>();
        games.add(gameDAO.getGame(id1));
        games.add(gameDAO.getGame(id2));
        Assertions.assertEquals(games, gameDAO.listGames());
    }

    @Test
    @DisplayName("Join Game White")
    public void joinGame() throws Exception {
        int id1 = gameDAO.addGameData(null, "Player1", "Test Game", new ChessGame());
        gameDAO.join("white", "WHITE", id1);
        Assertions.assertEquals("white", gameDAO.getGame(id1).whiteUsername());
    }

    @Test
    @DisplayName("Join Game Black")
    public void joinGameBlack() throws Exception {
        int id1 = gameDAO.addGameData(null, null, "Test Game", new ChessGame());
        gameDAO.join("white", "BLACK", id1);
        Assertions.assertEquals("white", gameDAO.getGame(id1).blackUsername());
    }
}
