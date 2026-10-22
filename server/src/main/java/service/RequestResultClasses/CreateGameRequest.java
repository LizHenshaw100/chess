package service.RequestResultClasses;

import model.GameData;

public record CreateGameRequest(String authToken, String gameName) { }
