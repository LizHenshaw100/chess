package service.requestresultclasses;

public record JoinGameRequest(String authToken, String playerColor, int gameID) { }
