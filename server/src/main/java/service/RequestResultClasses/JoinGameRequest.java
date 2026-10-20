package service.RequestResultClasses;

public record JoinGameRequest(String authToken, String playerColor, int gameID) { }
