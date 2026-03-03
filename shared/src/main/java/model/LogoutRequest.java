package model;

public class LogoutRequest {
    private AuthData authToken;


    public LogoutRequest(AuthData authToken) {
        this.authToken = authToken;
    }

    public LogoutRequest() {}

    public AuthData getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthData authToken) {
        this.authToken = authToken;
    }
}
