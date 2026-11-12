package client;

import org.junit.jupiter.api.*;
import requestresultclasses.RegisterRequest;
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
        facade = new ServerFacade("localhost:" + port);
    }

    @BeforeEach
    public static void beforeEach() throws Exception {
        facade.clear();
    }

    @Test
    @DisplayName("Register Success")
    public void registerSucces() throws Exception {
        var result = facade.register(new RegisterRequest("Liz", "Pass", "test@test.com"));
        Assertions.assertNotNull(result.authToken());
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
