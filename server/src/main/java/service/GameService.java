package service;

import dataaccess.GameDAO;
import exception.BadRequestException;
import exception.UnauthorizedException;
import model.GameData;
import service.RequestResultClasses.CreateGameRequest;
import service.RequestResultClasses.CreateGameResult;
import service.RequestResultClasses.ListGamesRequest;
import service.RequestResultClasses.ListGamesResult;

public class GameService {
    AuthService authService;
    GameDAO gameDAO;
    public GameService(AuthService aService, GameDAO gameDAO1) {
        this.authService = aService;
        this.gameDAO = gameDAO1;
    }

    public CreateGameResult createGame(CreateGameRequest request) throws Exception{
        GameData gameData = request.gameData();
        if (gameData.gameName() == null) {
            throw new BadRequestException("bad request");
        }
        if (authService.getAuthData(request.authToken()) == null ) {
            throw new UnauthorizedException("unauthorized");
        }
        return new CreateGameResult(gameDAO.addGameData(gameData));
    }

    public ListGamesResult listGames(ListGamesRequest request) throws Exception {
        if (authService.getAuthData(request.authToken()) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        return new ListGamesResult(gameDAO.listGames());
    }
}
