package server;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.GameData;
import service.GameService;
import service.RequestResultClasses.*;

public class GameHandler {
    GameService gameService;
    Gson gson = new Gson();

    public GameHandler(GameService service) {
        this.gameService = service;

    }

    public void createGame(Context ctx) throws Exception {
        var body = gson.fromJson(ctx.body(), GameData.class);
        var auth = ctx.header("authorization");
        CreateGameRequest request = new CreateGameRequest(auth, body);
        CreateGameResult result = gameService.createGame(request);
        ctx.status(200);
        ctx.result(gson.toJson(result));
    }

    public void listGames(Context ctx) throws Exception {
        ListGamesRequest request = new ListGamesRequest(ctx.header("authorization"));
        ListGamesResult result = gameService.listGames(request);
        ctx.status(200);
        ctx.result(gson.toJson(result));
    }
}
