package service;

import dataaccess.*;
import dataaccess.exceptions.*;
import model.*;

public class GameService {
    private UserDao userDao;
    private GameDao gameDao;
    private AuthDao authDao;

    public GameService(GameDao gameDao, UserDao userDao, AuthDao authDao) {
        this.gameDao = gameDao;
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public GamesListResult getGamesList(String authToken) throws DataAccessException {
        if(authDao.getAuth(authToken) == null || authToken == null) {
            throw new UnauthorizedException();
        }
        else {
            return new GamesListResult(gameDao.listGames());
        }
    }

    public int createGame(String authToken, String gameName) throws DataAccessException{
        if (authToken == null || authDao.getAuth(authToken) == null) {
            throw new UnauthorizedException();
        }
        else if (gameName == null) {
            throw new BadRequestException();
        }
        return gameDao.createGame(gameName);
    }

    public void joinGame(String authToken, String playerColor, int gameID) throws DataAccessException{
        if (authToken == null || authDao.getAuth(authToken) == null) {
            throw new UnauthorizedException();
        }
        else if (gameDao.getGame(gameID) == null) {
            throw new BadRequestException();
        }
        else if (!gameDao.getGame(gameID).isAvailable(playerColor)) {
            throw new AlreadyTakenException();
        }
    }

}
