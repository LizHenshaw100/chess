package service;

import dataaccess.GameDAO;
import exception.BadRequestException;
import exception.UnauthorizedException;
import model.GameData;
import service.RequestResultClasses.CreateGameRequest;

public class GameService {
    AuthService authService;
    GameDAO gameDAO;
    public GameService(AuthService aService, GameDAO gameDAO1) {
        this.authService = aService;
        this.gameDAO = gameDAO1;
    }

    public int createGame(CreateGameRequest request) throws Exception{
        GameData gameData = request.gameData();
        if (gameData.gameName() == null) {
            throw new BadRequestException("bad request");
        }
        if (authService.getAuthData(request.authToken()) == null ) {
            throw new UnauthorizedException("bad request");
        }
        return gameDAO.addGameData(gameData);
    }
}
