package model;

public class AuthData {
    private final String authToken;
    private final String username;


    AuthData(String authToken, String username) {

        this.authToken = authToken;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
