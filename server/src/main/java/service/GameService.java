package service;

import chess.ChessGame;
import dataaccess.GameDAO;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.UnauthorizedException;
import model.GameData;
import service.RequestResultClasses.*;

public class GameService {
    AuthService authService;
    GameDAO gameDAO;
    public GameService(AuthService aService, GameDAO gameDAO1) {
        this.authService = aService;
        this.gameDAO = gameDAO1;
    }

    public CreateGameResult createGame(CreateGameRequest request) throws Exception{
        String gameName = request.gameName();
        if (gameName == null) {
            throw new BadRequestException("bad request");
        }
        if (authService.getAuthData(request.authToken()) == null ) {
            throw new UnauthorizedException("unauthorized");
        }
        return new CreateGameResult(gameDAO.addGameData(null, null, gameName, new ChessGame()));
    }

    public ListGamesResult listGames(ListGamesRequest request) throws Exception {
        if (authService.getAuthData(request.authToken()) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        return new ListGamesResult(gameDAO.listGames());
    }

    public void joinGame(JoinGameRequest request) throws Exception {
        if (authService.getAuthData(request.authToken()) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        if (request == null
                || request.playerColor() == null
                || !("BLACK".equals(request.playerColor()) ||"WHITE".equals(request.playerColor()))) {
            throw new BadRequestException("bad request");
        }
        GameData game = gameDAO.getGame(request.gameID());
        if (game == null) {
            throw new BadRequestException("bad request");
        }
        if ("BLACK".equals(request.playerColor()) && game.blackUsername() != null) {
            throw new AlreadyTakenException("already taken");
        }
        if ("WHITE".equals(request.playerColor()) && game.whiteUsername() != null) {
            throw new AlreadyTakenException("already taken");
        }
        gameDAO.join(authService.getAuthData(request.authToken()).username(), request.playerColor(), request.gameID());
    }

    public GameData getGame(int gameID) {
        return gameDAO.getGame(gameID);
    }
}
