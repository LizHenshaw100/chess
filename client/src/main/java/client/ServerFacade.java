package client;


import com.google.gson.Gson;
import requestresultclasses.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public RegisterResult register(RegisterRequest request) throws Exception {
        var request1 = buildRequest("POST", "/user", request, null);
        var response = sendRequest(request1);
        return handleResponse(response, RegisterResult.class);
    };

    public LoginResult login(LoginRequest request) throws Exception {
        var request1 = buildRequest("POST", "/session", request, null);
        var response = sendRequest(request1);
        return handleResponse(response, LoginResult.class);
    };

    public void logout(LogoutRequest request) throws Exception {
        var request1 = buildRequest("DELETE", "/session", request, null);
        sendRequest(request1);
    };

    public ListGamesResult listGames(String authToken) throws Exception {
        var request1 = buildRequest("GET", "/game", null, authToken);
        var response = sendRequest(request1);
        return handleResponse(response, ListGamesResult.class);
    };

    public void clear() throws Exception {
        var request1 = buildRequest("DELETE", "/db", null, null);
        sendRequest(request1);
    };

    public CreateGameResult createGame(CreateGameRequest request, String authToken) throws Exception {
        var request1 = buildRequest("POST", "/game", request, authToken);
        var response = sendRequest(request1);
        return handleResponse(response, CreateGameResult.class);
    };

    public void joinGame(JoinGameRequest request, String authToken) throws Exception {
        var request1 = buildRequest("PUT", "/game", request, authToken);
        sendRequest(request1);
    };

    private HttpRequest buildRequest(String method, String path, Object body, String authToken) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        if (authToken != null) {
            request.setHeader("authorization", authToken);
        }
        return request.build();
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws Exception{
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws Exception {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw new Exception(body);
            }

            throw new Exception("other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}

