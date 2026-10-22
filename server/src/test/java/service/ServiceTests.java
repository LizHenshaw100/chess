package service;

import chess.ChessGame;
import dataaccess.*;
import exception.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.ClearHandler;
import server.UserHandler;
import service.RequestResultClasses.*;

public class ServiceTests {
    MemoryAuthDAO authDB;
    MemoryGameDAO gameDB;
    MemoryUserDAO userDB;

    AuthService authService;
    ClearService clearService;
    UserService userService;
    GameService gameService;

    ClearHandler clearHandler;
    UserHandler userHandler;

    @BeforeEach
    public void setup() {
        authDB = new MemoryAuthDAO();
        gameDB = new MemoryGameDAO();
        userDB = new MemoryUserDAO();

        clearService = new ClearService(authDB, gameDB, userDB);
        authService = new AuthService(authDB);
        userService = new UserService(userDB, authService);
        gameService = new GameService(authService, gameDB);

        clearHandler = new ClearHandler(clearService);
        userHandler = new UserHandler(userService);
    }

    @Test
    @DisplayName("Success Register User")
    public void addUserTest() throws Exception {
        String username = "username";
        String password = "password";
        String email = "email";
        RegisterResult result = userService.register(new RegisterRequest(username, password, email));
        Assertions.assertEquals(username, result.username());
        Assertions.assertNotNull(String.class, result.authToken());
    }
    @Test
    @DisplayName("Already Taken Register User")
    public void addSameUserTest() throws Exception {
        String username = "username";
        String password = "password";
        String email = "email";
        RegisterResult result1 = userService.register(new RegisterRequest(username, password, email));
        Assertions.assertThrows(AlreadyTakenException.class, () -> userService.register(new RegisterRequest(username, password, email)));
    }

    @Test
    @DisplayName("Bad Request Register User")
    public void addBadUserTest() throws Exception {
        String username = null;
        String password = "password";
        String email = "email";
        Assertions.assertThrows(BadRequestException.class, () -> userService.register(new RegisterRequest(username, password, email)));
    }

    @Test
    @DisplayName("Test Clear Endpoint")
    public void testClear() throws Exception {
        String username = "username";
        String password = "password";
        String email = "email";
        RegisterResult result = userService.register(new RegisterRequest(username, password, email));
        clearService.clear();
        UserData userData = userService.getUser(username);
        Assertions.assertEquals(null, userData);
    }

    @Test
    @DisplayName("Success Logout User")
    public void logoutUserTest() throws Exception {
        String username = "username";
        String password = "password";
        String email = "email";
        RegisterResult result1 = userService.register(new RegisterRequest(username, password, email));
        userService.logout(new LogoutRequest(result1.authToken()));
        Assertions.assertNull(authService.getAuthData(result1.authToken()));
    }

    @Test
    @DisplayName("Success Login User")
    public void loginUserTest() throws Exception {
        String username = "username";
        String password = "password";
        String email = "email";
        RegisterResult result1 = userService.register(new RegisterRequest(username, password, email));
        userService.logout(new LogoutRequest(result1.authToken()));
        LoginResult result2 = userService.login(new LoginRequest(username, password));
        Assertions.assertEquals(username, result2.username());
        Assertions.assertNotNull(String.class, result2.authToken());
    }

    @Test
    @DisplayName("Unauthorized Login: Wrong password")
    public void wrongPasswordTest() throws Exception {
        String username = "username";
        String password = "password";
        String email = "email";
        RegisterResult result1 = userService.register(new RegisterRequest(username, password, email));
        userService.logout(new LogoutRequest(result1.authToken()));
        Assertions.assertThrows(UnauthorizedException.class, () -> userService.login(new LoginRequest(username, "Wrong")));
    }
    @Test
    @DisplayName("Unauthorized Login: No Username")
    public void noUsernameTest() throws Exception {
        String username = null;
        String password = "password";
        Assertions.assertThrows(BadRequestException.class, () -> userService.login(new LoginRequest(username, password)));
    }

    @Test
    @DisplayName("Create Game")
    public void createGameTest() throws Exception {
        String username = "username";
        String password = "password";
        String email = "email";
        RegisterResult result1 = userService.register(new RegisterRequest(username, password, email));
        String authToken = result1.authToken();
        String gameName = "Test name";
        gameService.createGame(new CreateGameRequest(authToken, gameName));
        GameData expected = new GameData(0, null, null, gameName, new ChessGame());
        Assertions.assertEquals(expected, gameDB.getGame(0));
    }

    @Test
    @DisplayName("Bad AuthCreate Game")
    public void createGameBadAuthTest() throws Exception {
        String authToken = "bad";
        String gameName = "Test name";
        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.createGame(new CreateGameRequest(authToken, gameName)));
    }

    @Test
    @DisplayName("Bad AuthCreate Game")
    public void createGameBadAuthTest() throws Exception {
        String authToken = "bad";
        String gameName = "Test name";
        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.createGame(new CreateGameRequest(authToken, gameName)));
    }
}
