package service;

import dataaccess.exceptions.*;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void loginTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);

        AuthData auth = service.login(new LoginRequest("liz", "password"));

        assertEquals("liz", auth.getUsername());
        assertNotNull(auth.getAuthToken());
    }

    @Test
    public void falseLoginTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);

        assertThrows(dataaccess.exceptions.UnauthorizedException.class, () -> {
            service.login(new model.LoginRequest("liz", "wrongpassword"));
        });
    }

    @Test
    public void logoutTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);

        AuthData auth = service.login(new LoginRequest("liz", "password"));
        assertDoesNotThrow(() -> service.logout(auth.getAuthToken()));
    }

    @Test
    public void falseLogoutTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        String badAuth = "THIS IS BADD";
        assertThrows(UnauthorizedException.class, () -> {service.logout(badAuth);});
    }

    @Test
    public void gameListTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);

        AuthData auth = service.login(new LoginRequest("liz", "password"));

        GameService gameService = new GameService(gameDao, userDao, authDao);
        int gameId = gameService.createGame(auth.getAuthToken(), "LizChessGame");

        GamesListResult result = assertDoesNotThrow(() -> gameService.getGamesList(auth.getAuthToken()));
        var games = result.getGames();
        assertTrue(Arrays.stream(games).anyMatch(g -> g.getGameName().equals("LizChessGame")));
    }

    @Test
    public void negativeGameListTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        GameService gameService = new GameService(gameDao, userDao, authDao);

        String badToken = "bad token";
        assertThrows(UnauthorizedException.class, () -> {gameService.getGamesList(badToken);});
    }

    @Test
    public void createGameTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);

        AuthData auth = service.login(new LoginRequest("liz", "password"));

        GameService gameService = new GameService(gameDao, userDao, authDao);
        assertDoesNotThrow(() -> gameService.createGame(auth.getAuthToken(), "LizChessGame"));
    }

    @Test
    public void falseCreateGameTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        String badToken = "badToken";


        GameService gameService = new GameService(gameDao, userDao, authDao);
        assertThrows(UnauthorizedException.class, () -> gameService.createGame(badToken, "LizChessGame"));
    }

    @Test
    public void joinGameTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);

        UserData userData2 = new UserData("mimi", "password", "email@email.com");
        service.register(userData2);

        AuthData auth = service.login(new LoginRequest("liz", "password"));
        AuthData auth2 = service.login(new LoginRequest("mimi", "password"));

        GameService gameService = new GameService(gameDao, userDao, authDao);
        int gameID = gameService.createGame(auth.getAuthToken(), "LizChessGame");
        assertDoesNotThrow(() -> gameService.joinGame(auth2.getAuthToken(), "BLACK", gameID));
    }

    @Test
    public void falseJoinGameTest() throws DataAccessException {
        UserDao userDao = new MemoryUserDAO();
        GameDao gameDao = new MemoryGameDAO();
        AuthDao authDao = new MemoryAuthDAO();

        UserService service = new UserService(gameDao, userDao, authDao);
        UserData userData = new UserData("liz", "password", "email@email.com");
        service.register(userData);

        String badToken = "bad";

        AuthData auth = service.login(new LoginRequest("liz", "password"));

        GameService gameService = new GameService(gameDao, userDao, authDao);
        int gameID = gameService.createGame(auth.getAuthToken(), "LizChessGame");
        assertThrows(UnauthorizedException.class, () -> gameService.joinGame(badToken, "BLACK", gameID));
    }

}
