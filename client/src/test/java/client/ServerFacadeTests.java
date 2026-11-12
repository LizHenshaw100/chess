package client;

import org.junit.jupiter.api.*;
import requestresultclasses.*;
import server.Server;

import java.net.http.HttpRequest;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        facade.clear();
    }

    @Test
    @DisplayName("Register Success")
    public void registerSuccess() throws Exception {
        var result = facade.register(new RegisterRequest("Liz", "Pass", "test@test.com"));
        Assertions.assertNotNull(result.authToken());
    }

    @Test
    @DisplayName("Register Failure")
    public void registerFail() throws Exception {
        facade.register(new RegisterRequest("Liz", "Pass", "test@test.com"));
        Assertions.assertThrows(Exception.class ,() -> facade.register(new RegisterRequest("Liz", "Pass", "test@test.com")));
    }

    @Test
    @DisplayName("Login Success")
    public void loginSuccess() throws Exception{
        facade.register(new RegisterRequest("Liz", "Pass", "test@test.com"));
        Assertions.assertDoesNotThrow(() -> facade.login(new LoginRequest("Liz", "Pass")));
    }

    @Test
    @DisplayName("Login Fail")
    public void loginFailure() {
        Assertions.assertThrows(Exception.class, () -> facade.login(new LoginRequest("Liz", "Pass")));
    }

    @Test
    @DisplayName("Logout Success")
    void logoutSuccess() throws Exception {
        String auth = facade.register(new RegisterRequest("Liz", "Pass", "test@test.com")).authToken();
        Assertions.assertDoesNotThrow(() -> facade.logout(new LogoutRequest(auth)));
        Assertions.assertThrows(Exception.class, () -> facade.listGames(auth));
    }

    @Test
    @DisplayName("Logout Fail")
    void logoutBadTokenFails() {
        Assertions.assertThrows(Exception.class, () -> facade.logout(new LogoutRequest("Bad auth")));
    }

    @Test
    @DisplayName("Join Game Success")
    void joinGameSuccess() throws Exception{
        String auth = facade.register(new RegisterRequest("Liz", "Pass", "test@test.com")).authToken();
        int gameID = facade.createGame(new CreateGameRequest(auth, "LizsGame"), auth).gameID();
        facade.joinGame(new JoinGameRequest(auth, "BLACK", gameID), auth);
        var game = facade.listGames(auth).games().iterator().next();
        Assertions.assertEquals("Liz", game.blackUsername());

    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

}
